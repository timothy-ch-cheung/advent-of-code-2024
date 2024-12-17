package com.cheung.timothy.day12;

public class Node {
    private final String group;
    private Node left;
    private Node right;
    private Node top;
    private Node down;
    private boolean visited;

    public Node(String group) {
        this.group = group;
    }

    public Node getLeft() {
        return this.left;
    }

    public void setLeft(Node node) {
        this.left = node;
    }

    public Node getRight() {
        return this.right;
    }

    public void setRight(Node node) {
        this.right = node;
    }

    public Node getTop() {
        return this.top;
    }

    public void setTop(Node node) {
        this.top = node;
    }

    public Node getDown() {
        return this.down;
    }

    public void setDown(Node node) {
        this.down = node;
    }

    public String getGroup() {
        return group;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
    }

    public int getPerimeter() {
        int perimeter = 0;
        if (this.left == null) {
            perimeter++;
        }
        if (this.right == null) {
            perimeter++;
        }
        if (this.top == null) {
            perimeter++;
        }
        if (this.down == null) {
            perimeter++;
        }
        return perimeter;
    }
}
