package edu.hw2.lsp_problem;

public class Square extends Rectangle {
    @Override
    void setWidth(int width) {
        super.setHeight(width);
        super.setWidth(width);
    }

    @Override
    void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}
