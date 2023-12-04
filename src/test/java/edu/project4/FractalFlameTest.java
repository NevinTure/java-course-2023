package edu.project4;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FractalFlameTest {

    @Test
    public void test() {
        FractalImage image = FractalImage.create(1080, 1080);
        Renderer renderer = new SingleThreadRenderer();
        Rect rect = new Rect(0, 0, 1080, 1080);
        List<Transformation> variations = List.of(new SphereTransformation());
        image = renderer.render(image, rect, variations, 100000, (short) 10, 1234);
        Path path = Path.of("F:\\Tink projects\\java-course-2023\\src\\test\\java\\edu\\project4\\test.png");
        ImageUtils.save(image, path, ImageFormat.PNG);
    }
}
