package edu.project4;

import edu.project4.affine.AffineTransformation;
import edu.project4.image_processor.GammaLogProcessor;
import edu.project4.image_processor.ImageProcessor;
import edu.project4.renderer.MultiThreadRenderer;
import edu.project4.renderer.Renderer;
import edu.project4.transformation.DiskTransformation;
import edu.project4.transformation.HeartTransformation;
import edu.project4.transformation.HorseshoeTransformation;
import edu.project4.transformation.HyperbolicTransformation;
import edu.project4.transformation.PolarTransformation;
import edu.project4.transformation.SinusoidalTransformation;
import edu.project4.transformation.Transformation;
import edu.project4.util.ImageUtils;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FractalFlameTest {

    @Test
    public void test() {
        FractalImage image = FractalImage.create(3840 , 2160);
        Renderer renderer = new MultiThreadRenderer(5, 4);
        Rect rect = new Rect(-1, -1, 1, 1);
        List<Transformation> variations = List.of(
            new HeartTransformation(),
            new HyperbolicTransformation(),
            new PolarTransformation(),
            new DiskTransformation(),
            new HorseshoeTransformation(),
            new SinusoidalTransformation()
        );
        AffineTransformation affine = new AffineTransformation(10);
        renderer.render(image, rect, variations, affine,10000000, (short) 5, ThreadLocalRandom.current().nextLong(Long.MAX_VALUE));
        ImageProcessor processor = new GammaLogProcessor(2.2);
        processor.process(image);
        Path path = Path.of("F:\\Tink projects\\java-course-2023\\src\\test\\java\\edu\\project4\\test.png");
        ImageUtils.save(image, path, ImageFormat.PNG);
    }

    @Test
    public void test2() {
        double xrMin = -1080;
        double xrMax = 1920;
        double yr = 1080;
        int xc = 1920;
        int yc = 1920;
        double x = 231e-6;
        double y = 541e-6;
        double x1 = xc - Math.abs(((xrMax - x) / (xrMax - xrMin)) * xc);
        System.out.println(x1);
    }

    @Test
    public void test3() {
        ImageFormat format = ImageFormat.BMP;
        System.out.println(format);
    }
}
