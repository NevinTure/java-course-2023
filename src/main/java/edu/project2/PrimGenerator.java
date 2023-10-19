package edu.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator implements Generator {

    private static final Random RANDOMIZER = new Random();
    private final static int[] neighRows = {0, -2, 0, 2};
    private final static int[] neighCols = {-2, 0, 2, 0};
    @Override
    public Maze generate(int height, int width) {
        Cell[][] grid = generateStartGrid(height, width);
        generateMazeGrid(grid);
        return new Maze(grid, height, width);
    }

    private Cell[][] generateStartGrid(int height, int width) {
        Cell[][] grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(Type.WALL, new Coordinate(i, j));
            }
        }
        return grid;
    }

    private void generateMazeGrid(Cell[][] grid) {
        Cell current = grid[1][1];
        current.setType(Type.PASSAGE);
        Coordinate coord = current.getCoord();
        List<Cell> walls = getNeighWalls(coord, grid);
        Renderer renderer = new RendererImpl();
        while (!walls.isEmpty()) {
            System.out.println(renderer.render(new Maze(grid, grid.length, grid[0].length)));
            int choice = RANDOMIZER.nextInt(walls.size());
            Cell neig = walls.get(choice);
            walls.remove(neig);
            neig.setType(Type.PASSAGE);
            coord = neig.getCoord();
            Cell pass = findPassageToConnectWith(coord, grid);
            Cell wall = removeWall(grid, coord, pass.getCoord());
            wall.setType(Type.PASSAGE);
            walls.addAll(getNeighWalls(coord, grid));
        }
    }

    private List<Cell> getNeighWalls(Coordinate coord, Cell[][] grid) {
        List<Cell> neighs = new ArrayList<>(neighRows.length);
        int row = coord.row();
        int col = coord.col();
        for (int i = 0; i < neighRows.length; i++) {
            if (row + neighRows[i] >= grid.length - 1
                || row + neighRows[i] <= 0
                || col + neighCols[i] >= grid[0].length - 1
                || col + neighCols[i] <= 0
                || grid[row + neighRows[i]][col + neighCols[i]].getType().equals(Type.PASSAGE)) {
                continue;
            }
            neighs.add(grid[row + neighRows[i]][col + neighCols[i]]);
        }
        return neighs;
    }

    private Cell findPassageToConnectWith(Coordinate coord, Cell[][] grid) {
        int row = coord.row();
        int col = coord.col();
        List<Direction> directions = new ArrayList<>(
            List.of(Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST)
        );
        Cell foundPassage = null;
        while (!directions.isEmpty()) {
            Coordinate direction = getRandomDirection(directions);
            if (row + direction.row() >= grid.length
                || row + direction.row() < 0
                || col + direction.col() >= grid[0].length
                || col + direction.col() < 0
                || grid[row + direction.row()][col + direction.col()].getType().equals(Type.WALL)) {
                continue;
            }
            foundPassage = grid[row + direction.row()][col + direction.col()];
            break;
        }
        return foundPassage;
    }

    private Coordinate getRandomDirection(List<Direction> directions) {
        int choice = RANDOMIZER.nextInt(directions.size());
        Direction direction = directions.get(choice);
        directions.remove(choice);
        return switch (direction) {
            case WEST -> new Coordinate(0, -2);
            case NORTH -> new Coordinate(-2, 0);
            case EAST -> new Coordinate(0, 2);
            case SOUTH -> new Coordinate(2, 0);
        };
    }

    private Cell removeWall(Cell[][] grid, Coordinate currentCoord, Coordinate neighCoord) {
        Cell cell;
        if (currentCoord.row() == neighCoord.row()) {
            cell = grid[currentCoord.row()][(currentCoord.col() + neighCoord.col()) / 2];
        } else {
            cell = grid[(currentCoord.row() + neighCoord.row()) / 2][currentCoord.col()];
        }
        cell.setType(Type.PASSAGE);
        return cell;
    }
}
