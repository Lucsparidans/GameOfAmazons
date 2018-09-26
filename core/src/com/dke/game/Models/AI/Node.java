package com.dke.game.Models.AI;
import java.util.ArrayList;
public class Node {

    ArrayList<Node> allMyChildren = new ArrayList<Node>();
    private Node parent;
    private Node child;
    private int value;

    public Node(Node root, Node child, int value) {
        this.parent = root;
        this.child = child;
        this.value = value;
    }



    public ArrayList<Node> getAllMyChildren() {
        return allMyChildren;
    }



    // exists
    public void addToAllMyChildren(Node child) {
        allMyChildren.add(child);


    }







    public void setRoot(Node root) {
        this.parent = root;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getRoot() {
        return parent;
    }

    public Node getChild() {
        return child;
    }

}
