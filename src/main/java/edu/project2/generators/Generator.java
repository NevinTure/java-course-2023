package edu.project2.generators;

import edu.project2.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
