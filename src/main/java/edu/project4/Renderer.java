package edu.project4;

import java.util.List;

@FunctionalInterface
public interface Renderer {

    FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        long seed);
}
