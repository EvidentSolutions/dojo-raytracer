package fi.evident.dojo.raytracer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;

public final class SceneParser {
    
    private final char[] input;
    private int pos = 0;

    private SceneParser(String input) {
        this.input = input.toCharArray();
    }

    public static Scene parse(String file) throws IOException {
        return parse(new File(file));
    }
    
    public static Scene parse(File file) throws IOException {
        String contents = readContents(file);
        return new SceneParser(contents).parseScene();
    }
    
    private static String readContents(File file) throws IOException {
        FileReader reader = new FileReader(file);
        try {
            char[] buffer = new char[1024];
            int n;
            StringBuilder sb = new StringBuilder();
            while ((n = reader.read(buffer)) != -1)
                sb.append(buffer, 0, n);
            
            return sb.toString();
        } finally {
            reader.close();
        }
    }

    private Scene parseScene() {
        Scene scene = new Scene(parseCamera());
        
        while (hasMore()) {
            String symbol = readSymbol();
            if (symbol.equals("plane"))
                scene.addObject(parsePlane());
            else if (symbol.equals("sphere"))
                scene.addObject(parseSphere());
            else if (symbol.equals("light"))
                scene.addLight(parseLight());
            else
                throw fail("unexpected symbol: " + symbol);
        }
        
        return scene;
    }

    private SceneObject parsePlane() {
        Vector3 normal = parseVector();
        float offset = parseNumber();
        Surface surface = parseSurface();
        return new Plane(normal, offset, surface);
    }

    private SceneObject parseSphere() {
        Vector3 normal = parseVector();
        float offset = parseNumber();
        Surface surface = parseSurface();
        return new Sphere(normal, offset, surface);
    }

    private Surface parseSurface() {
        String name = readSymbol();
        
        Surface surface = Surfaces.findSurfaceByName(name);
        if (surface != null)
            return surface;
        else
            throw fail("invalid surface: "+ name);
    }
    
    private Light parseLight() {
        Vector3 position = parseVector();
        Color color = parseColor();
        return new Light(position, color);
    }

    private Camera parseCamera() {
        expectSymbol("camera");
        Vector3 position = parseVector();
        Vector3 lookAt = parseVector();
        
        return new Camera(position, lookAt);
    }

    private Vector3 parseVector() {
        expectChar('[');
        float x = parseNumber();
        float y = parseNumber();
        float z = parseNumber();
        expectChar(']');
        
        return new Vector3(x, y, z);
    }

    private Color parseColor() {
        expectChar('(');
        float r = parseNumber();
        float g = parseNumber();
        float b = parseNumber();
        expectChar(')');
        
        return new Color(r, g, b);
    }

    private float parseNumber() {
        skipWhitespace();
        
        if (!hasMore())
            throw fail("expected number, but got EOF");
        
        String token = readTokenFromAlphabet("-.0123456789");
        try {
            return Float.parseFloat(token);
        } catch (NumberFormatException e) {
            throw fail("expected number, but got " + token);
        }
    }

    private void expectChar(char expected) {
        skipWhitespace();
        
        if (pos < input.length) {
            char ch = input[pos++];
            if (ch != expected)
                throw fail("expected char " + expected + ", but got " + ch);
        } else
            throw fail("expected char " + expected + ", but got EOF");
    }

    private void expectSymbol(String expected) {
        String symbol = readSymbol();
        if (!expected.equals(symbol))
            throw fail("expected symbol " + expected + ", but got: " + symbol);
    }
    
    private String readSymbol() {
        skipWhitespace();
        StringBuilder sb = new StringBuilder();
        while (pos < input.length && isLetter(input[pos]))
            sb.append(input[pos++]);
        return sb.toString();
    }

    private String readTokenFromAlphabet(String alphabet) {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length && alphabet.indexOf(input[pos]) != -1)
            sb.append(input[pos++]);
        return sb.toString();
    }
    
    private boolean hasMore() {
        skipWhitespace();
        
        return pos < input.length;
    }

    private void skipWhitespace() {
        for (; pos < input.length; pos++) {
            char ch = input[pos];
            if (ch == ';') {
                while (pos < input.length && input[pos] != '\n')
                    pos++;
            } else if (!isWhitespace(input[pos]))
                break;
        }
    }
    
    private ParseException fail(String message) {
        return new ParseException(pos, message);
    }
}

class ParseException extends RuntimeException {
    public ParseException(int pos, String message) {
        super(pos + ": " + message);
    }
}
