package com.dke.game.Models.AI.Luc;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T data = null;


    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node(T data, Node<T> parent) {
        this.data = data;
    }

    public Node getRoot() {
        // Finish this method
        Node<T> pointer = this;
        while(pointer.parent != null){
            pointer = pointer.parent;
        }
        return pointer;
    }

    public Node<T> addChild(Node<T> child) {
        // Finish this method
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        // Finish this method
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        // Finish this method
        return this.children;
    }

    public T getData() {
        // Finish this method
        return this.data;
    }

    public void setData(T data) {
        // Finish this method
        this.data = data;
    }

    private void setParent(Node<T> parent) {
        // Finish this method
        this.parent = parent;
    }

    public Node<T> getParent() {
        // Finish this method
        return this.parent;
    }
    public int getDepth(Node<T> root) {
        if (root == null) {
            return 0;
        }
        int h = 0;

        for (Node<T> n : root.getChildren()) {
            h = Math.max(h, getDepth(n));
        }
        return h + 1;
    }

}