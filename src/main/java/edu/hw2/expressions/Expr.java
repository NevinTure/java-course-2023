package edu.hw2.expressions;

public sealed interface Expr {
    double evaluate();

    public record Constant(double value) implements Expr {

        @Override
        public double evaluate() {
            return value;
        }
    }
    public record Negate(Constant value) implements Expr {
        @Override
        public double evaluate() {
            return -value.evaluate();
        }
    }
    public record Exponent(Constant base, double exp) implements Expr {
        @Override
        public double evaluate() {
            return Math.pow(base.evaluate(), exp);
        }
    }
    public record Addition(Constant a, Constant b) implements Expr {
        @Override
        public double evaluate() {
            return a.evaluate() + b.evaluate();
        }
    }
    public record Multiplication(Constant a, Constant b) implements Expr {
        @Override
        public double evaluate() {
            return a.evaluate() * b.evaluate();
        }
    }
}
