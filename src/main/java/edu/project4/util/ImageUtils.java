package edu.project4.util;

import edu.project4.Color;
import edu.project4.FractalImage;
import edu.project4.ImageFormat;
import edu.project4.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {

    private ImageUtils() {}

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage img = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                Pixel pixel = image.pixel(j, i);
                Color color = pixel.getColor();
                img.setRGB(j, i, color.getRGB());
            }
        }
        try {
            ImageIO.write(img, format.toString(), filename.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
