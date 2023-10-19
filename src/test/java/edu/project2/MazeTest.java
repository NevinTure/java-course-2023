package edu.project2;

import org.junit.jupiter.api.Test;
import java.util.List;

public class MazeTest {

    @Test
    void testRenderer() {
        Generator generator = new PrimGenerator();
        Maze maze = generator.generate(21, 21);
        Renderer renderer = new RendererImpl();
        System.out.println(renderer.render(maze));
    }

    @Test
    void testPathRenderer() {
        Generator generator = new DfsGenerator();
        Maze maze = generator.generate(35, 35);
        Renderer renderer = new RendererImpl();
        Solver solver = new DfsSolver();
        List<Coordinate> path = solver.solve(maze, new Coordinate(1,1), new Coordinate(33, 33));
        System.out.println(path);
        System.out.println(renderer.render(maze, path));
    }
}
