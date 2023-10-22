package edu.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeadEndFillSolver implements Solver {
    private final static Random RANDOMIZER = new Random();
    private final static int[] neighRows = {0, -1, 0, 1};
    private final static int[] neighCols = {-1, 0, 1, 0};
    private final int FILLER = 1;
    private final int PROTECTED_CELL = 2;
    private final int DEAD_END_WALLS_NUM = 3;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        Cell[][] grid = maze.getGrid();
        int[][] fillerGrid = new int[maze.getHeight()][maze.getWidth()];
        protectCells(fillerGrid, start, end);
        parseGrid(fillerGrid, grid);
        List<Coordinate> deadEns = findDeadEnds(fillerGrid);
        fillDeadEnds(deadEns, fillerGrid);
        return findPath(start, end, fillerGrid);
    }

    private void protectCells(int[][] fillerGrid, Coordinate... coords) {
        for (Coordinate coord : coords) {
            fillerGrid[coord.row()][coord.col()] = PROTECTED_CELL;
        }
    }

    private void parseGrid(int[][] fillerGrid, Cell[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getType().equals(Type.WALL)) {
                    fillerGrid[i][j] = FILLER;
                }
            }
        }
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
        for (int i = 0; i < neighRows.length; i++) {
            int rowCoord = row + neighRows[i];
            int colCoord = col + neighCols[i];
            if (fillerGrid[rowCoord][colCoord] != FILLER) {
                counter++;
            }
        }
        return counter > 1;
    }

    private boolean isDeadEnd(int x, int y, int[][] fillerGrid) {
        int wallCounter = 0;
        for (int i = 0; i < neighRows.length; i++) {
            if (fillerGrid[x + neighRows[i]][y + neighCols[i]] == FILLER) {
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
        for (int i = 0; i < neighRows.length; i++) {
            int rowCoord = row + neighRows[i];
            int colCoord = col + neighCols[i];
            if (fillerGrid[rowCoord][colCoord] != FILLER) {
                coord = new Coordinate(rowCoord, colCoord);
            }
        }
        return coord;
    }
}
