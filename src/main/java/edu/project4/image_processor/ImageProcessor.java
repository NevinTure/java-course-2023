package edu.project4.image_processor;

import edu.project4.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
