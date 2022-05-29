package com.example;

public class Node<T extends Comparable<T>> {

    private T item;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    private boolean isBlack = false;

    public Node() {
        item = null;
        parent = left = right = null;
    }

    public Node(T item) {
        this.item = item;
        parent = left = right = null;
    }

    public Node(T item, Node<T> parent, Node<T> left, Node<T> right) {
        this.item = item;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
    public Node<T> getSibling() {
        if (parent.left==this||parent.left==null)
            return parent.right;
        return parent.left;
    }
    public T getItem() {
        return this.item;
    }
    public void setItem(T item) {
        this.item = item;
    }
    public boolean isBlack() {
        try {
            return this.isBlack;
        }catch (Exception e){
            return true;
        }
    }
    public boolean isLeftChild(){

        if (parent.left==null||parent.left==this){
            return true;
        }
        else
            return false;
    }
    public void setBlack(boolean isBlack) {
        this.isBlack = isBlack;
    }
    public boolean hasLeft() {
        return left != null;
    }
    public boolean hasRight() {
        return right != null;
    }
}
