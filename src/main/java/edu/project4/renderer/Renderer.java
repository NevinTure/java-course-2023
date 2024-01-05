package edu.project4.renderer;

import edu.project4.FractalImage;
import edu.project4.Rect;
import edu.project4.affine.AffineTransformation;
import edu.project4.transformation.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {

    void render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        AffineTransformation affine,
        int samples,
        short iterPerSample,
        long seed);
}
