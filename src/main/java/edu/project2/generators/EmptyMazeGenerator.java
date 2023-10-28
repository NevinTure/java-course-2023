package edu.project2.generators;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Type;

//Класс для тестирования
public class EmptyMazeGenerator implements Generator {
    @Override
    public Maze generate(int height, int width) {
        Cell[][] grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(Type.PASSAGE, new Coordinate(i, j));
            }
        }
        for (int i = 0; i < width; i++) {
            grid[0][i].setType(Type.WALL);
            grid[height - 1][i].setType(Type.WALL);
        }
        for (int i = 0; i < height; i++) {
            grid[i][0].setType(Type.WALL);
            grid[i][width - 1].setType(Type.WALL);
        }
        return new Maze(grid, height, width);
    }
}
