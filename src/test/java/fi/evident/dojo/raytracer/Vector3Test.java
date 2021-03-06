package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fi.evident.dojo.raytracer.Vector3.ZERO;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class Vector3Test {

    private static final float TOLERANCE = 0.001f;

    @NotNull
    private static final Random random = new Random();

    @Test
    public void magnitude() {
        assertMagnitude(0, new Vector3(0, 0, 0));
    }

    @Test(expected = ArithmeticException.class)
    public void unitVectorOfZeroIsUndefined() {
        ZERO.normalize();
    }

    @Test
    public void scalingZeroProducesZero() {
        for (float s : arbitraryScales())
            assertEqualVector(ZERO, ZERO.scale(s));
    }

    @Test
    public void scalingByZeroProducesZero() {
        for (Vector3 v : arbitraryVectors())
            assertEqualVector(ZERO, v.scale(0));
    }

    @Test
    public void scalingScalesMagnitude() {
        for (Vector3 v : arbitraryVectors())
            for (float s : arbitraryScales())
                assertMagnitude(Math.abs(s * v.magnitude()), v.scale(s));
    }

    @Test
    public void magnitudeOfUnitVectorIsOne() {
        for (Vector3 v : arbitraryVectors())
            if (v.magnitude() != 0)
                assertMagnitude(1.0f, v.normalize());
    }

    @NotNull
    private static List<Float> arbitraryScales() {
        List<Float> magnitudes = new ArrayList<>();
        magnitudes.addAll(asList(0f, 1f, 5.4f, 100f, -42.4f));
        for (int i = 0; i < 10; i++)
            magnitudes.add(random.nextFloat());
        return magnitudes;
    }

    @NotNull
    private static List<Vector3> arbitraryVectors() {
        List<Vector3> vectors = new ArrayList<>();
        vectors.add(ZERO);
        vectors.add(new Vector3(3, 4, 0));
        vectors.add(new Vector3(1.4f, -4, 40.2f));
        vectors.add(new Vector3(4.0f, -50, 4));
        for (int i = 0; i < 10; i++)
            vectors.add(new Vector3(random.nextFloat(),
                    random.nextFloat(),
                    random.nextFloat()));
        return vectors;
    }

    private static void assertMagnitude(float magnitude, @NotNull Vector3 v) {
        assertEquals("magnitude", magnitude, v.magnitude(), TOLERANCE);
    }

    private static void assertEqualVector(@NotNull Vector3 expected, @NotNull Vector3 v) {
        assertEquals("x", expected.x, v.x, TOLERANCE);
        assertEquals("y", expected.y, v.y, TOLERANCE);
        assertEquals("z", expected.z, v.z, TOLERANCE);
    }
}
