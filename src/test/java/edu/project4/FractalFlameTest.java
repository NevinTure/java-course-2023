package edu.project4;

import edu.project4.affine.AffineTransformation;
import edu.project4.image_processor.GammaLogProcessor;
import edu.project4.image_processor.ImageProcessor;
import edu.project4.renderer.MultiThreadRenderer;
import edu.project4.renderer.Renderer;
import edu.project4.renderer.SingleThreadRenderer;
import edu.project4.transformation.DiskTransformation;
import edu.project4.transformation.HeartTransformation;
import edu.project4.transformation.HorseshoeTransformation;
import edu.project4.transformation.HyperbolicTransformation;
import edu.project4.transformation.PolarTransformation;
import edu.project4.transformation.SinusoidalTransformation;
import edu.project4.transformation.SphereTransformation;
import edu.project4.transformation.SwirlTransformation;
import edu.project4.transformation.Transformation;
import edu.project4.util.ImageUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FractalFlameTest {
    @Test
    public void testSingleThreadRenderer() {
        //given
        int canvasWidth = 1920;
        int canvasHeight = 1080;
        int xMin = -1;
        int xMax = 1;
        int yMin = -1;
        int yMax = 1;
        int symmetry = 2;
        int affineTranAmount = 10;
        int samples = 1000000;
        short iterPerSample = 3;
        double gamma = 2.2;
        long seed = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        Path filename =
            Paths.get(Path.of("").toAbsolutePath().toString(), "src", "test", "java", "edu", "project4", "single.png");

        //when
        FractalImage image = FractalImage.create(canvasWidth, canvasHeight);
        Rect rect = new Rect(xMin, yMin, xMax, yMax);
        Renderer renderer = new SingleThreadRenderer(symmetry);
        List<Transformation> variations = List.of(
            new HeartTransformation(),
            new HyperbolicTransformation(),
            new DiskTransformation(),
            new HorseshoeTransformation()
        );
        AffineTransformation affine = new AffineTransformation(affineTranAmount);
        renderer.render(image, rect, variations, affine, samples, iterPerSample, seed);
        ImageProcessor processor = new GammaLogProcessor(gamma);
        processor.process(image);
        ImageUtils.save(image, filename, ImageFormat.PNG);

        //then
        assertThat(Files.exists(filename)).isTrue();
    }

    /*
      кол-во сэмплов/итераций на сэмпл | время один поток | кол-во потоков/время
      1 млн/5                                1978 мсек            2/1547 мсек
      1 млн/5                                1978 мсек            4/915 мсек
      1 млн/5                                1978 мсек            8/588 мсек
      10 млн/5                               17.17 сек            2/14.7 сек
      10 млн/5                               17.17 сек            4/8.2 сек
      10 млн/5                               17.17 сек            8/4.4 сек
      50 млн/5                               76.4 сек             2/1.22 мин
      50 млн/5                               76.4 сек             4/36.6 сек
      50 млн/5                               76.4 сек             8/22.2 сек
    */

    @Test
    public void testMultiThreadRenderer() {
        //given
        int canvasWidth = 1920;
        int canvasHeight = 1080;
        int xMin = -1;
        int xMax = 1;
        int yMin = -1;
        int yMax = 1;
        int symmetry = 2;
        int threadsAmount = 2;
        int affineTranAmount = 10;
        int samples = 1000000;
        short iterPerSample = 3;
        double gamma = 2.2;
        long seed = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        Path filename =
            Paths.get(Path.of("").toAbsolutePath().toString(), "src", "test", "java", "edu", "project4", "multi.jpeg");

        //when
        FractalImage image = FractalImage.create(canvasWidth, canvasHeight);
        Rect rect = new Rect(xMin, yMin, xMax, yMax);
        Renderer renderer = new MultiThreadRenderer(threadsAmount, symmetry);
        List<Transformation> variations = List.of(
            new PolarTransformation(),
            new SinusoidalTransformation(),
            new SphereTransformation(),
            new SwirlTransformation()
        );
        AffineTransformation affine = new AffineTransformation(affineTranAmount);
        renderer.render(image, rect, variations, affine, samples, iterPerSample, seed);
        ImageProcessor processor = new GammaLogProcessor(gamma);
        processor.process(image);
        ImageUtils.save(image, filename, ImageFormat.JPEG);

        //then
        assertThat(Files.exists(filename)).isTrue();
    }
}

