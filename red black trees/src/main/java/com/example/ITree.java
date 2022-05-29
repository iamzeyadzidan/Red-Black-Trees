package com.example;

/**
 * Red Black Tree Interface
 */
public interface ITree<T extends Comparable<T>> {
    Node<T> getRoot();

    boolean isEmpty();

    boolean clear();

    Node<T> search(T item);

    boolean contains(T item);

    boolean insert(T item);

    boolean delete(T item);
}