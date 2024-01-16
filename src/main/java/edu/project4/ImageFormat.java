package edu.project4;

public enum ImageFormat {
    JPEG,
    BMP,
    PNG;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
