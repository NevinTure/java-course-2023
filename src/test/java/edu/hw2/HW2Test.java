package edu.hw2;

import edu.hw2.connections_problem.PopularCommandExecutor;
import edu.hw2.connections_problem.connection_managers.ConnectionManager;
import edu.hw2.connections_problem.connection_managers.DefaultConnectionManager;
import edu.hw2.connections_problem.connection_managers.FaultyConnectionManager;
import edu.hw2.connections_problem.exceptions.ConnectionException;
import edu.hw2.expressions.Expr;
import edu.hw2.lsp_problem.Rectangle;
import edu.hw2.lsp_problem.Square;
import edu.hw2.who_called.CallingInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.hw2.expressions.Expr.Addition;
import static edu.hw2.expressions.Expr.Constant;
import static edu.hw2.expressions.Expr.Exponent;
import static edu.hw2.expressions.Expr.Multiplication;
import static edu.hw2.expressions.Expr.Negate;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw2.who_called.WhoCalledUtils.callingInfo;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HW2Test {

    static Rectangle[] rectangles() {
        return new Rectangle[] {new Rectangle(), new Square()};
    }

    @Test
    void testExpr() {
        //given
        Expr two = new Constant(2);
        Expr four = new Constant(4);
        Expr negOne = new Negate(new Constant(1));
        Expr sumTwoFour = new Addition(two, four);
        Expr mult = new Multiplication(sumTwoFour, negOne);
        Expr exp = new Exponent(mult, 2);
        Expr res = new Addition(exp, new Constant(1));

        //when
        double result = res.evaluate();

        //then
        assertThat(result).isEqualTo(37.0);
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void testRectangleArea(Rectangle rect) {
        //when
        rect = rect.setHeight(20);
        rect = rect.setWidth(10);
        double area = rect.area();

        //then
        assertThat(area).isEqualTo(200.0);
    }

    @Test
    void testSquareArea() {
        //given
        int side = 10;
        Square square = new Square(side);

        //when
        double area = square.area();

        //then
        assertThat(area).isEqualTo(100.0);
    }

    @Test
    void testCommandExecutorWithDefaultManagerWhenSuccessful() {
        //given
        int percentOfFailure = 0;
        int connectionPof = 0;
        ConnectionManager manager = new DefaultConnectionManager(
            percentOfFailure,
            connectionPof
        );
        int maxAttempts = 1;
        PopularCommandExecutor executor = new PopularCommandExecutor(
            manager,
            maxAttempts
        );
        String command = "something";

        //then
        assertThatCode(() ->
            executor.tryExecute(command))
            .doesNotThrowAnyException();
    }

    @Test
    void testCommandExecutorWithFaultyManagerWhenSuccessful() {
        //given
        int connectionPof = 0;
        ConnectionManager manager = new FaultyConnectionManager(
            connectionPof
        );
        int maxAttempts = 1;
        PopularCommandExecutor executor = new PopularCommandExecutor(
            manager,
            maxAttempts
        );
        String command = "something";

        //then
        assertThatCode(() ->
            executor.tryExecute(command))
            .doesNotThrowAnyException();
    }

    @Test
    void testCommandExecutorWithDefaultManagerWhenFailed() {
        //given
        int percentOfFailure = 100;
        int connectionPof = 100;
        ConnectionManager manager = new DefaultConnectionManager(
            percentOfFailure,
            connectionPof
        );
        int maxAttempts = 1;
        PopularCommandExecutor executor = new PopularCommandExecutor(
            manager,
            maxAttempts
        );
        String command = "something";

        //then
        assertThatThrownBy(() ->
            executor.tryExecute(command))
            .hasCause(new ConnectionException());
    }

    @Test
    void testCommandExecutorWithFaultyManagerWhenFailed() {
        //given
        int connectionPof = 100;
        ConnectionManager manager = new FaultyConnectionManager(
            connectionPof
        );
        int maxAttempts = 1;
        PopularCommandExecutor executor = new PopularCommandExecutor(
            manager,
            maxAttempts
        );
        String command = "something";

        //then
        assertThatThrownBy(() ->
            executor.tryExecute(command))
            .hasCause(new ConnectionException());
    }

    @Test
    void testCallingInfo() {
        //when
        CallingInfo info = callingInfo();
        String result = info.className() + " " + info.methodName();

        //then
        String expectedResult = "edu.hw2.HW2Test" + " " + "testCallingInfo";
        assertThat(result).isEqualTo(expectedResult);
    }
}
