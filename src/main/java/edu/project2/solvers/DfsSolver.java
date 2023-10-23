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
        Cell startCell = grid[start.row()][start.col()];
        Cell endCell = grid[end.row()][end.col()];
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        depthFirstSearch(grid, startCell, endCell, visited, answer);
        return answer;
    }

    private boolean depthFirstSearch(
        Cell[][] grid,
        Cell current,
        Cell end,
        boolean[][] visited,
        List<Coordinate> answer) {
        if (current.equals(end)) {
            answer.add(current.getCoord());
            return true;
        }
        Coordinate coord = current.getCoord();
        visited[coord.row()][coord.col()] = true;
        List<Cell> neighbours = getCellNeighbors(coord, grid);
        while (!neighbours.isEmpty()) {
            int choice = RANDOMIZER.nextInt(neighbours.size());
            Cell neig = neighbours.get(choice);
            Coordinate neighCoord = neig.getCoord();
            if (!visited[neighCoord.row()][neighCoord.col()]) {
                boolean isEnd = depthFirstSearch(grid, neig, end, visited, answer);
                if (isEnd) {
                    answer.add(coord);
                    return true;
                }
            }
            neighbours.remove(neig);
        }
        return false;
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
                || colCoord < 0
                || grid[rowCoord][colCoord].getType().equals(Type.WALL)) {
                continue;
            }
            neighs.add(grid[rowCoord][colCoord]);
        }
        return neighs;
    }
}