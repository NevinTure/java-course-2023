package edu.project2;

import org.junit.jupiter.api.Test;
import java.util.List;

public class MazeTest {

    @Test
    void testRenderer() {
        Generator generator = new DfsGenerator();
        Maze maze = generator.generate(17, 17);
        Renderer renderer = new RendererImpl();
        System.out.println(renderer.render(maze));
    }

    @Test
    void testPathRenderer() {
        Generator generator = new PrimGenerator();
        Maze maze = generator.generate(45, 45);
        Renderer renderer = new RendererImpl();
        Solver solver = new DfsSolver();
//        System.out.println(renderer.render(maze));
        System.out.println();
        List<Coordinate> path = solver.solve(maze, new Coordinate(1,1), new Coordinate(45, 45));
        System.out.println(renderer.render(maze, path));
    }
}
