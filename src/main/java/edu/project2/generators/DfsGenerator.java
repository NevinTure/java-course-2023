package edu.project2.generators;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DfsGenerator implements Generator {
    private final static Random RANDOMIZER = new Random();
    private final static int[] NEIGH_ROWS = {0, -2, 0, 2};
    private final static int[] NEIGH_COLS = {-2, 0, 2, 0};

    @SuppressWarnings({"ParameterAssignment", "MagicNumber"})
    @Override
    public Maze generate(int height, int width) {
        /*
        При нечетном размере прибавляю 2, чтобы внутренняя сетка (то есть не считая внешний слой
        стенок) была размером (height * width).
        Алгоритм может работать с четными размерами, но тогда правую и нижнюю стенку необходимо
        сделать шириной в 2 клетки. Но чтобы такого не было и для большей красоты, к четным я
        прибавляю 3. Так же можно решить эту проблему выбросив ошибку при вводе четной длины.
         */
        height += height % 2 == 0 ? 3 : 2;
        width += width % 2 == 0 ? 3 : 2;
        Cell[][] grid = generateStartGrid(height, width);
        generateMazeGridRecursive(grid);
        return new Maze(grid, height, width);
    }

    private Cell[][] generateStartGrid(int height, int width) {
        Cell[][] grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i % 2 != 0) && (j % 2 != 0) && (i < height - 1 && j < width - 1)) {
                    grid[i][j] = new Cell(Type.PASSAGE, new Coordinate(i, j));
                } else {
                    grid[i][j] = new Cell(Type.WALL, new Coordinate(i, j));
                }
            }
        }
        return grid;
    }

    private void generateMazeGridRecursive(Cell[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Cell startCell = grid[1][1];
        depthFirstSearch(grid, startCell, visited);
    }

    private void depthFirstSearch(Cell[][] grid, Cell current, boolean[][] visited) {
        Coordinate coord = current.getCoord();
        visited[coord.row()][coord.col()] = true;
        List<Cell> neighbours = getCellNeighbors(coord, grid);
        while (!neighbours.isEmpty()) {
            int choice = RANDOMIZER.nextInt(neighbours.size());
            Cell neig = neighbours.get(choice);
            Coordinate neighCoord = neig.getCoord();
            if (!visited[neighCoord.row()][neighCoord.col()]) {
                Coordinate wallCoord = removeWall(coord, neig.getCoord(), grid);
                visited[wallCoord.row()][wallCoord.col()] = true;
                depthFirstSearch(grid, neig, visited);
            }
            neighbours.remove(neig);
        }
    }

    private List<Cell> getCellNeighbors(Coordinate coord, Cell[][] grid) {
        List<Cell> neighs = new ArrayList<>(NEIGH_ROWS.length);
        int row = coord.row();
        int col = coord.col();
        for (int i = 0; i < NEIGH_ROWS.length; i++) {
            int rowCoord = row + NEIGH_ROWS[i];
            int colCoord = col + NEIGH_COLS[i];
            if (rowCoord >= grid.length
                || rowCoord < 0
                || colCoord >= grid[0].length
                || colCoord < 0) {
                continue;
            }
            neighs.add(grid[rowCoord][colCoord]);
        }
        return neighs;
    }

    private Coordinate removeWall(Coordinate currentCoord, Coordinate neighCoord, Cell[][] grid) {
        Cell cell;
        if (currentCoord.row() == neighCoord.row()) {
            cell = grid[currentCoord.row()][(currentCoord.col() + neighCoord.col()) / 2];
        } else {
            cell = grid[(currentCoord.row() + neighCoord.row()) / 2][currentCoord.col()];
        }
        cell.setType(Type.PASSAGE);
        return cell.getCoord();
    }
}
