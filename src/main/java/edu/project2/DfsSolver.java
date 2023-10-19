package edu.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DfsSolver implements Solver {
    private final static Random RANDOMIZER = new Random();
    private final static int[] neighRows = {0, -1, 0, 1};
    private final static int[] neighCols = {-1, 0, 1, 0};

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

    private boolean depthFirstSearch(Cell[][] grid, Cell current, Cell end, boolean[][] visited, List<Coordinate> answer) {
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
        List<Cell> neighs = new ArrayList<>(neighRows.length);
        int row = coord.row();
        int col = coord.col();
        for (int i = 0; i < neighRows.length; i++) {
            if (row + neighRows[i] >= grid.length
                || row + neighRows[i] < 0
                || col + neighCols[i] >= grid[0].length
                || col + neighCols[i] < 0
                || grid[row + neighRows[i]][col + neighCols[i]].getType().equals(Type.WALL)) {
                continue;
            }
            neighs.add(grid[row + neighRows[i]][col + neighCols[i]]);
        }
        return neighs;
    }
}
