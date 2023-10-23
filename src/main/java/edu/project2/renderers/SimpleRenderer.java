package edu.project2.renderers;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Type;
import java.util.List;

public class SimpleRenderer implements Renderer {

    private final static char WALL_SYMBOL = '█';
    private final static char PASSAGE_SYMBOL = ' ';
    private final static char PATH_SYMBOL = '◊';

    @Override
    public String render(Maze maze) {
        StringBuilder gridStrBuilder = new StringBuilder();
        Cell[][] grid = maze.getGrid();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if (grid[i][j].getType().equals(Type.WALL)) {
                    gridStrBuilder.append(WALL_SYMBOL);
                } else {
                    gridStrBuilder.append(PASSAGE_SYMBOL);
                }
            }
            gridStrBuilder.append("\n");
        }
        return gridStrBuilder.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder gridStrBuilder = new StringBuilder();
        Cell[][] grid = maze.getGrid();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if (path.contains(new Coordinate(i, j))) {
                    gridStrBuilder.append(PATH_SYMBOL);
                } else if (grid[i][j].getType().equals(Type.WALL)) {
                    gridStrBuilder.append(WALL_SYMBOL);
                } else {
                    gridStrBuilder.append(PASSAGE_SYMBOL);
                }
            }
            gridStrBuilder.append("\n");
        }
        return gridStrBuilder.toString();
    }
}
