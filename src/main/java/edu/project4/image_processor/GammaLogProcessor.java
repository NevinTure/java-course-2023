package edu.project4.image_processor;

import edu.project4.FractalImage;
import edu.project4.Pixel;

public class GammaLogProcessor implements ImageProcessor {
    private final double gamma;

    public GammaLogProcessor(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        double max = 0.0;
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                Pixel pixel = image.pixel(col, row);
                if (pixel.getHitCount() != 0) {
                    pixel.setNormal(StrictMath.log10(pixel.getHitCount()));
                    if (pixel.getNormal() > max) {
                        max = pixel.getNormal();
                    }
                }
            }
        }
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                Pixel pixel = image.pixel(col, row);
                pixel.setNormal(pixel.getNormal() / max);
                pixel.setColor(pixel
                    .getColor()
                    .setGammaLog(pixel.getNormal(), gamma)
                );
            }
        }
    }
}
