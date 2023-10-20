package edu.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DfsGenerator implements Generator {
    private final static Random RANDOMIZER = new Random();
    private final static int[] neighRows = {0, -2, 0, 2};
    private final static int[] neighCols = {-2, 0, 2, 0};

    @Override
    public Maze generate(int height, int width) {
        if (height % 2 == 0) {
            height++;
        }
        //Или при четном размере выбрасывать ошибку
        if (width % 2 == 0) {
            width++;
        }
        Cell[][] grid = generateStartGrid(height, width);
        generateMazeGridRecursive(grid);
        return new Maze(grid, height, width);
    }

    private Cell[][] generateStartGrid(int height, int width) {
        Cell[][] grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i % 2 != 0) && (j % 2 != 0) && (i < height - 1 && j < width - 1)) {
                    grid[i][j] = new Cell(Type.PASSAGE, new Coordinate(i,j));
                } else {
                    grid[i][j] = new Cell(Type.WALL, new Coordinate(i,j));
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
                Coordinate wallCoord = removeWall(grid, coord, neig.getCoord());
                visited[wallCoord.row()][wallCoord.col()] = true;
                depthFirstSearch(grid, neig, visited);
            }
            neighbours.remove(neig);
        }
    }

    private List<Cell> getCellNeighbors(Coordinate coord, Cell[][] grid) {
        List<Cell> neighs = new ArrayList<>(neighRows.length);
        int row = coord.row();
        int col = coord.col();
        for (int i = 0; i < neighRows.length; i++) {
            int rowCoord = row + neighRows[i];
            int colCoord = col + neighCols[i];
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

    private Coordinate removeWall(Cell[][] grid, Coordinate currentCoord, Coordinate neighCoord) {
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
