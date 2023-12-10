package edu.hw9.task3;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Type;
import edu.project2.solvers.Solver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

public class DfsMultiThreadSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> answer = new ArrayList<>();
        Cell[][] grid = maze.getGrid();
        checkParam(start, grid);
        checkParam(end, grid);
        AtomicBoolean[][] visited = new AtomicBoolean[grid.length][grid[0].length];
        fillVisited(visited);
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            DfsSolverTask task = new DfsSolverTask(grid, start, end, visited, answer);
            forkJoinPool.invoke(task);
        }
        if (!answer.contains(end)) {
            throw new IllegalStateException("Maze cannot be solved!");
        }
        return answer;
    }

    private void fillVisited(AtomicBoolean[][] visited) {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                visited[i][j] = new AtomicBoolean(false);
            }
        }
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
