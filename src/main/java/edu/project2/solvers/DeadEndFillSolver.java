package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Type;
import java.util.ArrayList;
import java.util.List;

public class DeadEndFillSolver implements Solver {
    private final static int[] NEIGH_ROWS = {0, -1, 0, 1};
    private final static int[] NEIGH_COLS = {-1, 0, 1, 0};
    private final static int FILLER = 1;
    private final static int PROTECTED_CELL = 2;
    private final static int DEAD_END_WALLS_NUM = 3;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        Cell[][] grid = maze.getGrid();
        checkParam(start, grid);
        checkParam(end, grid);
        int[][] fillerGrid = parseGrid(grid);
        protectCells(fillerGrid, start, end);
        List<Coordinate> deadEns = findDeadEnds(fillerGrid);
        fillDeadEnds(deadEns, fillerGrid);
        return findPath(start, end, fillerGrid);
    }

    private void protectCells(int[][] fillerGrid, Coordinate... coords) {
        for (Coordinate coord : coords) {
            fillerGrid[coord.row()][coord.col()] = PROTECTED_CELL;
        }
    }

    private int[][] parseGrid(Cell[][] grid) {
        int[][] fillerGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getType().equals(Type.WALL)) {
                    fillerGrid[i][j] = FILLER;
                }
            }
        }
        return fillerGrid;
    }

    private List<Coordinate> findDeadEnds(int[][] fillerGrid) {
        List<Coordinate> deadEnds = new ArrayList<>();
        for (int i = 1; i < fillerGrid.length - 1; i++) {
            for (int j = 1; j < fillerGrid[0].length - 1; j++) {
                if (isDeadEnd(i, j, fillerGrid) && fillerGrid[i][j] != PROTECTED_CELL) {
                    deadEnds.add(new Coordinate(i, j));
                }
            }
        }
        return deadEnds;
    }

    private void fillDeadEnds(List<Coordinate> deadEnds, int[][] fillerGrid) {
        for (int i = 0; i < deadEnds.size(); i++) {
            Coordinate coord = deadEnds.get(i);
            while (coord != null && !isJunction(coord, fillerGrid)) {
                if (fillerGrid[coord.row()][coord.col()] == PROTECTED_CELL) {
                    break;
                }
                fillerGrid[coord.row()][coord.col()] = FILLER;
                coord = findNextPassage(coord, fillerGrid);
            }
        }
    }

    private boolean isJunction(Coordinate coord, int[][] fillerGrid) {
        int counter = 0;
        int row = coord.row();
        int col = coord.col();
        for (int i = 0; i < NEIGH_ROWS.length; i++) {
            int rowCoord = row + NEIGH_ROWS[i];
            int colCoord = col + NEIGH_COLS[i];
            if (fillerGrid[rowCoord][colCoord] != FILLER) {
                counter++;
            }
        }
        return counter > 1;
    }

    private boolean isDeadEnd(int x, int y, int[][] fillerGrid) {
        int wallCounter = 0;
        for (int i = 0; i < NEIGH_ROWS.length; i++) {
            if (fillerGrid[x + NEIGH_ROWS[i]][y + NEIGH_COLS[i]] == FILLER) {
                wallCounter++;
            }
        }
        return wallCounter == DEAD_END_WALLS_NUM;
    }

    private List<Coordinate> findPath(Coordinate start, Coordinate end, int[][] fillerGrid) {
        List<Coordinate> path = new ArrayList<>();
        path.add(start);
        Coordinate current = start;
        do {
            fillerGrid[current.row()][current.col()] = FILLER;
            current = findNextPassage(current, fillerGrid);
            path.add(current);
        } while (!current.equals(end));
        return path;
    }

    private Coordinate findNextPassage(Coordinate prev, int[][] fillerGrid) {
        int row = prev.row();
        int col = prev.col();
        Coordinate coord = null;
        for (int i = 0; i < NEIGH_ROWS.length; i++) {
            int rowCoord = row + NEIGH_ROWS[i];
            int colCoord = col + NEIGH_COLS[i];
            if (fillerGrid[rowCoord][colCoord] != FILLER) {
                coord = new Coordinate(rowCoord, colCoord);
            }
        }
        if (coord == null) {
            throw new IllegalStateException("Maze cannot be solved!");
        }
        return coord;
    }

    private void checkParam(Coordinate coord, Cell[][] grid) {
        if (coord.row() >= grid.length
            || coord.col() >= grid[0].length
            || grid[coord.row()][coord.col()].getType().equals(Type.WALL)) {
            throw new IllegalArgumentException("Illegal parameter (out of bound or wall)");
        }
    }
}
