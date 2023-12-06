package edu.project4.util;

import edu.project4.Color;
import edu.project4.FractalImage;
import edu.project4.Pixel;
import edu.project4.ImageFormat;
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
                Pixel pixel = image.pixel(j, i);
                Color color = pixel.getColor();
                img.setRGB(j, i, color.ARGB());
            }
        }
        try {
            ImageIO.write(img, format.toString(), filename.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
