package edu.project4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public final class ImageUtils {
    private ImageUtils() {}

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage img = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                img.setRGB(i, j, image.pixel(i, j).getColor().ARGB());
            }
        }
        try {
            ImageIO.write(img, "png", filename.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
