package edu.project2;

import edu.project2.generators.DfsGenerator;
import edu.project2.generators.EmptyMazeGenerator;
import edu.project2.generators.Generator;
import edu.project2.generators.PrimGenerator;
import edu.project2.renderers.Renderer;
import edu.project2.renderers.SimpleRenderer;
import edu.project2.solvers.DeadEndFillSolver;
import edu.project2.solvers.DfsSolver;
import edu.project2.solvers.Solver;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class MazeTest {

    private static Arguments[] generators() {
        return new Arguments[] {
            Arguments.of(new PrimGenerator()),
            Arguments.of(new DfsGenerator())
        };
    }

    private static Arguments[] solvers() {
        return new Arguments[] {
            Arguments.of(new DfsSolver()),
            Arguments.of(new DeadEndFillSolver())
        };
    }


    @Test
    void testRendererWithPredefinedMaze() {
        //given
        Generator generator = new EmptyMazeGenerator();
        Maze maze = generator.generate(5, 5);
        Cell[][] grid = maze.getGrid();
        grid[1][2].setType(Type.WALL);
        grid[3][2].setType(Type.WALL);

        //when
        Renderer renderer = new SimpleRenderer();
        String result = renderer.render(maze);

        //then
        String expectedResult = """
            █████
            █ █ █
            █   █
            █ █ █
            █████
            """;
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void testRendererPathWithPredefinedMaze(Solver solver) {
        //given
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);
        Generator generator = new EmptyMazeGenerator();
        Maze maze = generator.generate(5, 5);
        Cell[][] grid = maze.getGrid();
        grid[1][2].setType(Type.WALL);
        grid[3][2].setType(Type.WALL);

        //when
        List<Coordinate> path = solver.solve(maze, start, end);
        Renderer renderer = new SimpleRenderer();
        String result = renderer.render(maze, path);

        //then
        String expectedResult = """
            █████
            █◊█ █
            █◊◊◊█
            █ █◊█
            █████
            """;
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("generators")
    void testGeneratorWithWrongParams(Generator generator) {
        //given
        int height = -1;
        int width = 12;

        //then
        assertThatIllegalArgumentException().isThrownBy(() -> generator.generate(height, width));
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void testSolverWhenMazeCanBeSolved(Solver solver) {
        //given
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);
        Generator generator = new EmptyMazeGenerator();
        Maze maze = generator.generate(5, 5);
        Cell[][] grid = maze.getGrid();
        grid[1][2].setType(Type.WALL);
        grid[3][2].setType(Type.WALL);
        //            █████
        //            █s█ █
        //            █   █
        //            █ █e█
        //            █████

        //then
        assertThatCode(() -> solver.solve(maze, start, end)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void testSolverWhenMazeCannotBeSolved(Solver solver) {
        //given
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 5);
        Generator generator = new EmptyMazeGenerator();
        Maze maze = generator.generate(5, 7);
        Cell[][] grid = maze.getGrid();
        grid[1][3].setType(Type.WALL);
        grid[2][3].setType(Type.WALL);
        grid[3][3].setType(Type.WALL);
        //            ███████
        //            █s █  █
        //            █  █  █
        //            █  █ e█
        //            ███████

        //then
        assertThatIllegalStateException().isThrownBy(() -> solver.solve(maze, start, end));
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void testSolverWithWrongParams(Solver solver) {
        //given
        Coordinate start = new Coordinate(10, 123);
        Coordinate end = new Coordinate(1, 1);
        int height = 15;
        int width = 15;
        Generator generator = new EmptyMazeGenerator();

        //when
        Maze maze = generator.generate(height, width);

        //then
        assertThatIllegalArgumentException().isThrownBy(() -> solver.solve(maze, start, end));
    }
}
