package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DfsSolver implements Solver {
    private final static Random RANDOMIZER = new Random();
    private final static int[] NEIGH_ROWS = {0, -1, 0, 1};
    private final static int[] NEIGH_COLS = {-1, 0, 1, 0};

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> answer = new ArrayList<>();
        Cell[][] grid = maze.getGrid();
        checkParam(start, grid);
        checkParam(end, grid);
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        depthFirstSearch(grid, start, end, visited, answer);
        if (!answer.contains(end)) {
            throw new IllegalStateException("Maze cannot be solved!");
        }
        return answer;
    }

    private boolean depthFirstSearch(
        Cell[][] grid,
        Coordinate current,
        Coordinate end,
        boolean[][] visited,
        List<Coordinate> answer) {
        if (current.equals(end)) {
            answer.add(current);
            return true;
        }
        visited[current.row()][current.col()] = true;
        List<Coordinate> neighbours = getCellNeighbors(current, grid);
        while (!neighbours.isEmpty()) {
            int choice = RANDOMIZER.nextInt(neighbours.size());
            Coordinate neig = neighbours.get(choice);
            if (!visited[neig.row()][neig.col()]) {
                boolean isEnd = depthFirstSearch(grid, neig, end, visited, answer);
                if (isEnd) {
                    answer.add(current);
                    return true;
                }
            }
            neighbours.remove(neig);
        }
        return false;
    }

    private List<Coordinate> getCellNeighbors(Coordinate coord, Cell[][] grid) {
        List<Coordinate> neighs = new ArrayList<>(NEIGH_ROWS.length);
        int row = coord.row();
        int col = coord.col();
        for (int i = 0; i < NEIGH_ROWS.length; i++) {
            int rowCoord = row + NEIGH_ROWS[i];
            int colCoord = col + NEIGH_COLS[i];
            if (rowCoord >= grid.length
                || rowCoord < 0
                || colCoord >= grid[0].length
                || colCoord < 0
                || grid[rowCoord][colCoord].getType().equals(Type.WALL)) {
                continue;
            }
            neighs.add(grid[rowCoord][colCoord].getCoord());
        }
        return neighs;
    }

    private void checkParam(Coordinate coord, Cell[][] grid) {
        if (coord.row() < 0
            || coord.row() >= grid.length
            || coord.col() < 0
            || coord.col() >= grid[0].length
            || grid[coord.row()][coord.col()].getType().equals(Type.WALL)) {
            throw new IllegalArgumentException("Illegal parameter (out of bound or wall)");
        }
    }
}
