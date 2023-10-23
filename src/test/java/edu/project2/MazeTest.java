package edu.project2;

import edu.project2.generators.DfsGenerator;
import edu.project2.generators.Generator;
import edu.project2.generators.PrimGenerator;
import edu.project2.renderers.Renderer;
import edu.project2.renderers.SimpleRenderer;
import edu.project2.solvers.DfsSolver;
import edu.project2.solvers.Solver;
import org.junit.jupiter.api.Test;
import java.util.List;

public class MazeTest {

    @Test
    void testRenderer() {
        Generator generator = new DfsGenerator();
        Maze maze = generator.generate(17, 17);
        Renderer renderer = new SimpleRenderer();
        System.out.println(renderer.render(maze));
    }

    @Test
    void testPathRenderer() {
        Generator generator = new PrimGenerator();
        Maze maze = generator.generate(45, 45);
        Renderer renderer = new SimpleRenderer();
        Solver solver = new DfsSolver();
//        System.out.println(renderer.render(maze));
        System.out.println();
        List<Coordinate> path = solver.solve(maze, new Coordinate(1,1), new Coordinate(45, 45));
        System.out.println(renderer.render(maze, path));
    }
}
