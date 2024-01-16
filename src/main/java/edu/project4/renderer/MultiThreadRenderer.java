package edu.project4.renderer;

import edu.project4.FractalImage;
import edu.project4.Rect;
import edu.project4.affine.AffineTransformation;
import edu.project4.transformation.Transformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadRenderer implements Renderer {

    private final int threadsAmount;
    private final int symmetry;

    public MultiThreadRenderer(int threadsAmount, int symmetry) {
        this.threadsAmount = threadsAmount;
        this.symmetry = symmetry;
    }

    public MultiThreadRenderer(int threadsAmount) {
        this.threadsAmount = threadsAmount;
        this.symmetry = 1;
    }

    @Override
    public void render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        AffineTransformation affine,
        int samples,
        short iterPerSample,
        long seed
    ) {
        int samplesPerThread = samples / threadsAmount;
        try (ExecutorService service = Executors.newFixedThreadPool(threadsAmount)) {
            for (int i = 0; i < threadsAmount; i++) {
                service.submit(() -> {
                    Renderer renderer = new RenderWorker(symmetry);
                    renderer.render(
                        canvas, world, variations, affine, samplesPerThread, iterPerSample, seed
                    );
                });
            }
//            service.shutdown();
        }
    }
}
