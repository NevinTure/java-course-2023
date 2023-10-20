package edu.project2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        Set<Cell> walls = getNeighWalls(coord, grid);
        while (!walls.isEmpty()) {
            Cell neig = getRandomCellFromSet(walls);
            walls.remove(neig);
            neig.setType(Type.PASSAGE);
            coord = neig.getCoord();
            Cell pass = findPassageToConnectWith(coord, grid);
            Cell wall = removeWall(grid, coord, pass.getCoord());
            wall.setType(Type.PASSAGE);
            walls.addAll(getNeighWalls(coord, grid));
        }
    }

    private Set<Cell> getNeighWalls(Coordinate coord, Cell[][] grid) {
        Set<Cell> neighs = new HashSet<>(neighRows.length);
        int row = coord.row();
        int col = coord.col();
        for (int i = 0; i < neighRows.length; i++) {
            int rowCoord = row + neighRows[i];
            int colCoord = col + neighCols[i];
            if (rowCoord >= grid.length - 1
                || rowCoord <= 0
                || colCoord >= grid[0].length - 1
                || colCoord <= 0
                || grid[rowCoord][colCoord].getType().equals(Type.PASSAGE)) {
                continue;
            }
            neighs.add(grid[rowCoord][colCoord]);
        }
        return neighs;
    }

    private Cell getRandomCellFromSet(Set<Cell> cells) {
        return cells
            .stream()
            .skip(RANDOMIZER.nextInt(cells.size()))
            .findFirst()
            .orElse(null);
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
            int rowCoord = row + direction.row();
            int colCoord = col + direction.col();
            if (rowCoord >= grid.length
                || rowCoord < 0
                || colCoord >= grid[0].length
                || colCoord < 0
                || grid[rowCoord][colCoord].getType().equals(Type.WALL)) {
                continue;
            }
            foundPassage = grid[rowCoord][colCoord];
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
