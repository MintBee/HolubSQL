package com.designpattern.model;

public interface ModelVisitor {
    void visit(Product product);
    void visit(Stock stock);
}
