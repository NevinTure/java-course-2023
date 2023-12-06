package edu.project4;

public record Color(int r, int g, int b) {

    public static final Color BLACK = new Color(0, 0, 0);

    @SuppressWarnings("MagicNumber")
    public int getRGB() {
        return (r << 16) | (g << 8) | b;
    }

    public Color divColor(int divider) {
        return new Color(
            this.r() / divider,
            this.g() / divider,
            this.b() / divider);
    }

    public Color setGammaLog(double normal, double gamma) {
        return new Color(
            (int) (this.r() * Math.pow(normal, 1.0 / gamma)),
            (int) (this.g() * Math.pow(normal, 1.0 / gamma)),
            (int) (this.b() * Math.pow(normal, 1.0 / gamma))
        );
    }
}
