package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class SceneParser {
    
    @NotNull
    private final char[] input;
    private int pos = 0;

    private SceneParser(@NotNull String input) {
        this.input = input.toCharArray();
    }

    @NotNull
    public static Scene parse(@NotNull String file) throws IOException {
        return parse(new File(file));
    }
    
    @NotNull
    public static Scene parse(@NotNull File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new SceneParser(new String(bytes, UTF_8)).parseScene();
    }

    @NotNull
    private Scene parseScene() {
        Scene scene = new Scene(parseCamera());
        
        while (hasMore()) {
            String symbol = readSymbol();
            switch (symbol) {
                case "plane":
                    scene.addObject(parsePlane());
                    break;
                case "sphere":
                    scene.addObject(parseSphere());
                    break;
                case "light":
                    scene.addLight(parseLight());
                    break;
                default:
                    throw fail("unexpected symbol: " + symbol);
            }
        }
        
        return scene;
    }

    @NotNull
    private SceneObject parsePlane() {
        Vector3 normal = parseVector();
        float offset = parseNumber();
        Surface surface = parseSurface();
        return new Plane(normal, offset, surface);
    }

    @NotNull
    private SceneObject parseSphere() {
        Vector3 normal = parseVector();
        float offset = parseNumber();
        Surface surface = parseSurface();
        return new Sphere(normal, offset, surface);
    }

    @NotNull
    private Surface parseSurface() {
        String name = readSymbol();

        Optional<Surface> surface = Surfaces.findSurfaceByName(name);
        return surface.orElseThrow(() -> fail("invalid surface: "+ name));
    }
    
    @NotNull
    private Light parseLight() {
        Vector3 position = parseVector();
        Color color = parseColor();
        return new Light(position, color);
    }

    @NotNull
    private Camera parseCamera() {
        expectSymbol("camera");
        Vector3 position = parseVector();
        Vector3 lookAt = parseVector();
        
        return new Camera(position, lookAt);
    }

    @NotNull
    private Vector3 parseVector() {
        expectChar('[');
        float x = parseNumber();
        float y = parseNumber();
        float z = parseNumber();
        expectChar(']');
        
        return new Vector3(x, y, z);
    }

    @NotNull
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

    private void expectSymbol(@NotNull String expected) {
        String symbol = readSymbol();
        if (!expected.equals(symbol))
            throw fail("expected symbol " + expected + ", but got: " + symbol);
    }

    @NotNull
    private String readSymbol() {
        skipWhitespace();
        StringBuilder sb = new StringBuilder();
        while (pos < input.length && isLetter(input[pos]))
            sb.append(input[pos++]);
        return sb.toString();
    }

    @NotNull
    private String readTokenFromAlphabet(@NotNull String alphabet) {
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
    
    @NotNull
    private ParseException fail(@NotNull String message) {
        return new ParseException(pos, message);
    }
}

