package edu.project2.renderers;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
