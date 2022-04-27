package com.civitasv.lox;

import java.util.List;

abstract class Expr {

    // Visitor pattern, with this, we don't need
    // to write every method in parent class and
    // then override them in subclass. Instead, we
    // only supply a `accept` method in parent class
    // and override it in subclass. In this way, when
    // we want to add method for each subclass, we
    // declare a class which implement this `Visitor`
    // interface and then implements all the methods
    // in it, in `visitBinaryExpr` method, we can write
    // logic to handle `Binary` expression, in
    // `visitGroupingExpr` method, we can write
    // different logic to handle `Grouping` expression, etc.
    // Thus, we can only write the function once without
    // overriding by all subclasses.
    interface Visitor<R> {
        R visitBinaryExpr(Binary expr);

        R visitGroupingExpr(Grouping expr);

        R visitLiteralExpr(Literal expr);

        R visitUnaryExpr(Unary expr);
    }

    static class Binary extends Expr {
        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        final Expr left;
        final Token operator;
        final Expr right;
    }

    static class Grouping extends Expr {
        Grouping(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        final Expr expression;
    }

    static class Literal extends Expr {
        Literal(Object value) {
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        final Object value;
    }

    static class Unary extends Expr {
        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }

        final Token operator;
        final Expr right;
    }

    abstract <R> R accept(Visitor<R> visitor);
}
