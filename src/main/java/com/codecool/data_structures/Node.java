package com.codecool.data_structures;

public class Node<T> {

    private T data;
    private Node pointer;

    public Node(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return this.pointer;
    }

    public void setNext(Node<T> pointer) {
        this.pointer = pointer;
    }

    public T getData() {
        return this.data;
    }

}
