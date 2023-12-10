package edu.hw9.task3;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class DfsSolverTask extends RecursiveTask<Boolean> {

    private final Random randomizer;
    private final Cell[][] grid;
    private final Coordinate current;
    private final Coordinate end;
    private final AtomicBoolean[][] visited;
    private final List<Coordinate> answer;
    private final static int[] NEIGH_ROWS = {0, -1, 0, 1};
    private final static int[] NEIGH_COLS = {-1, 0, 1, 0};

    public DfsSolverTask(
        Cell[][] grid,
        Coordinate current,
        Coordinate end,
        AtomicBoolean[][] visited,
        List<Coordinate> answer
    ) {
        this.randomizer = ThreadLocalRandom.current();
        this.grid = grid;
        this.current = current;
        this.end = end;
        this.visited = visited;
        this.answer = answer;
    }

    @Override
    protected Boolean compute() {
        if (current.equals(end)) {
            answer.add(current);
            return true;
        }
        visited[current.row()][current.col()].set(true);
        List<Coordinate> neighbours = getCellNeighbors(current, grid);
        List<DfsSolverTask> remainNeigs = new ArrayList<>(neighbours.size());
        while (!neighbours.isEmpty()) {
            int choice = randomizer.nextInt(neighbours.size());
            Coordinate neig = neighbours.get(choice);
            if (!visited[neig.row()][neig.col()].get()) {
                DfsSolverTask task = new DfsSolverTask(grid, neig, end, visited, answer);
                task.fork();
                remainNeigs.add(task);
            }
            neighbours.remove(neig);
        }
        boolean isEnd;
        for (var neig : remainNeigs) {
            isEnd = neig.join();
            if (isEnd) {
                answer.add(current);
                return true;
            }
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
}
