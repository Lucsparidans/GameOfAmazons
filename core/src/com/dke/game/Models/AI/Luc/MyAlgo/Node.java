package com.dke.game.Models.AI.Luc.MyAlgo;


import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T data = null;


    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;
    private final int DEPTH;


    public Node(T data, Node<T> parent) {
        this.parent = parent;
        this.data = data;
        this.DEPTH = this.getDepth();
    }
    public Node(Node<T> parent){
        this(null,parent);
    }

    public Node getRoot() {
        // Finish this method
        Node<T> pointer = this;
        while (pointer.parent != null) {
            pointer = pointer.parent;
        }
        return pointer;
    }

    public Node<T> addChild(Node<T> child) {
        // Finish this method
        this.children.add(child);
        child.setParent(this);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        // Finish this method
        this.children.addAll(children);
        for (Node child : children) {
            child.setParent(this);
        }
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

    private int getDepth() {
        int count = 0;
        Node<T> current;

        if (this.getParent() != null) {
            current = this.getParent();
            count++;
            while (current.getParent() != null) {
                current = current.parent;
                count++;
            }
        }
        return count;
    }

    public int getDEPTH() {
        return DEPTH;
    }
}