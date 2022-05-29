package com.example;

public class RBT<T extends Comparable<T>> implements ITree<T> {

    private Node<T> rbtRoot;
    private boolean isEmpty = true;

    /**
     * Defining booleans to help with rotations within the red-black tree class
     */

    boolean ll = false;
    boolean lr = false;
    boolean rr = false;
    boolean rl = false;
    /**
     * Method to return the root of the tree if it is not empty.
     */
    @Override
    public Node<T> getRoot() {
        if (this.isEmpty) {
            return null;
        }
        return this.rbtRoot;
    }
    /**
     * Method to true if a tree is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.isEmpty;
    }
    /**
     * Method that clears the tree. Returns true if it wasn't empty and cleared
     * successfully, false otherwise.
     */
    @Override
    public boolean clear() {
        if (rbtRoot == null) {
            isEmpty = true;
            return false;
        }
        this.rbtRoot = null;
        isEmpty = true;
        return true;
    }

    /**
     * Method that returns the node of a given key, if found.
     */
    @Override
    public Node<T> search(T item) {
        Node<T> current = rbtRoot;
        // LOOP UNTIL NULL POINTER
        while (current != null) {
            // IF FOUND
            if (current.getItem().equals(item)) {
                return current;
            } else if (current.getItem().compareTo(item) < 0) {
                current = current.right; // IF THE GIVEN KEY IS GREATER THAN THE CURRENT KEY.
            } else {
                current = current.left; // IF THE GIVEN KEY IS LOWER THAN THE CURRENT KEY.
            }
        }
        // IF NOT FOUND
        return null;
    }

    /**
     * Method that returns true if the tree contains a key, false otherwise;
     */
    @Override
    public boolean contains(T item) {
        Node<T> current = rbtRoot;
        // LOOP UNTIL NULL POINTER
        while (current != null) {
            if (current.getItem().equals(item)) {
                return true;
            } else if (current.getItem().compareTo(item) < 0) {
                current = current.right; // IF THE GIVEN KEY IS GREATER THAN THE CURRENT KEY.
            } else {
                current = current.left; // IF THE GIVEN KEY IS LOWER THAN THE CURRENT KEY.
            }
        }
        // IF NOT FOUND.
        return false;
    }

    /**
     * Method that inserts a key. Returns true upon a successful insertion, false
     * otherwise.
     */
    @Override
    public boolean insert(T item) {
        // IF THE ITEM IS ALREADY INSERTED
        if (contains(item)) {
            System.out.println(item.toString() + " FOUND");
            return false;
        } else {
            isEmpty = false;
            // CHECK IF THE TREE HAS A ROOT NODE
            if (rbtRoot == null) {
                rbtRoot = new Node<T>(item);
                rbtRoot.setBlack(true);
                return true;
            }
            // IF SO, DO THE INSERTION
            rbtRoot = insertItem(item, rbtRoot);
            if (rbtRoot != null) {
                rbtRoot.parent = null;
            }
            return true;
        }

    }
    /**
     * Method to help with insertion of a key.
     */
    private Node<T> insertItem(T item, Node<T> root) {
        // DEFINING A DOUBLE RED CONFLICT PARAMETER
        boolean conflict = false;
        // IF THE GIVEN NODE IS NULL, WHICH IS ROOT IN THIS CASE, RETURN A CREATION OF A
        // NEW NODE WITH THE GIVEN KEY.
        if (root == null) {
            return new Node<T>(item);
        } else if (item.compareTo(root.getItem()) < 0) {
            // IF THE GIVEN ITEM IS LOWER THAN THE CURRENT ITEM, INSERT TO THE LEFT.
            root.left = insertItem(item, root.left);
            // SET THE PARENT OF THE INSERTED NODE TO CURRENT NODE.
            root.left.parent = root;

            // IF A DOUBLE RED IS ENCOUNTERED AFTER INSERTION, SET THE CONFLICT PARAMETER.
            if (root != this.rbtRoot) {
                if (!root.isBlack() && !root.left.isBlack()) {
                    conflict = true;
                }
            }
        } else if (item.compareTo(root.getItem()) > 0) {
            // IF THE GIVEN ITEM IS GREATER THAN THE CURRENT ITEM, INSERT TO THE RIGHT.
            root.right = insertItem(item, root.right);
            // SET THE PARENT OF THE INSERTED NODE TO THE CURRENT NODE.
            root.right.parent = root;

            // IF A DOUBLE RED IS ENCOUNTERED AFTER INSERTION, SET THE CONFLICT PARAMETER.
            if (root != this.rbtRoot) {
                if (!root.isBlack() && !root.right.isBlack()) {
                    conflict = true;
                }
            }
        }
        // DO ROTATIONS ACCORDING TO THE CONFLICT HAPPENED. THIS IS DONE RECURSEVILY.
        // THINK OF WHAT WOULD HAPPEN AFTER INSERTING THE NODE.
        if (this.ll) {
            System.out.println("Performing LL Rotation on " + root.getItem());
            root = rotateLeft(root);
            root.setBlack(true);
            root.left.setBlack(false);
            this.ll = false;
        } else if (this.rr) {
            System.out.println("Performing RR Rotation on " + root.getItem());
            root = rotateRight(root);
            root.setBlack(true);
            root.right.setBlack(false);
            this.rr = false;
        } else if (this.rl) {
            System.out.println("Performing RL Rotation on " + root.getItem());
            root.right = rotateRight(root.right);
            root.right.parent = root;
            root = rotateLeft(root);
            root.setBlack(true);
            root.left.setBlack(false);
            this.rl = false;
        } else if (this.lr) {
            System.out.println("Performing LR Rotation on " + root.getItem());
            root.left = rotateLeft(root.left);
            root.left.parent = root;
            root = rotateRight(root);
            root.setBlack(true);
            root.right.setBlack(false);
            this.lr = false;
        }
        // SETTING THE ROTATION PARAMETERS ACCORDING TO CONFLICT TYPE, IF THERE IS A
        // CONFLICT.
        if (conflict) {
            // IF THE SIBLING (UNCLE) IS THE LEFT CHILD OF THE GRANDPARENT
            if (root.parent.right == root) {
                // AND THE COLOR OF SIBLING IS BLACK. (PLEASE NOT WE DIDN'T USE SENTINEL NODES
                // SO WE ASSUMED THEM TO BE NULL.)
                if (root.parent.left == null || root.parent.left.isBlack()) {
                    // AND IF THE INSERTED RED NODE IS A LEFT CHILD OF THE PARENT
                    if (root.left != null && !root.left.isBlack()) {
                        // THEN IT IS A RIGHT LEFT ROTATION
                        this.rl = true;
                    } else if (root.right != null && !root.right.isBlack()) {
                        // LEFT LEFT ROTATION OTHERWISE.
                        this.ll = true;
                    }
                } else {
                    // IF THERE IS NO ROTATION, SIMPLY RECOLOR THE NODES.
                    root.parent.left.setBlack(true);
                    root.setBlack(true);
                    if (root.parent != this.rbtRoot) {
                        root.parent.setBlack(false);
                    }
                }
            } else {
                // BUT IF THE SIBLING IS A RIGHT CHILD OF THE GRANDPARENT AND IS BLACK
                if (root.parent.right == null || root.parent.right.isBlack()) {
                    if (root.left != null && !root.left.isBlack()) {
                        // AND THE NEWLY INSERTED RED NODE IS A LEFT CHILD OF THE PARENT
                        // THEN IT IS AN RR ROTATION
                        this.rr = true;
                    } else if (root.right != null && !root.right.isBlack()) {
                        // LEFT RIGHT ROTATION OTHERWISE.
                        this.lr = true;
                    }
                } else {
                    // IF THERE IS NO ROTATION, SIMPLY RECOLOR.
                    root.parent.right.setBlack(true);
                    root.setBlack(true);
                    if (root.parent != this.rbtRoot) {
                        root.parent.setBlack(false);
                    }
                }
            }
            // UNSET THE CONFLICT AFTER BALANCING AND RECOLORING
            conflict = false;
        }
        return root;
    }

    /**
     * Methods to help with the rotation within insertion
     */
    private Node<T> rotateLeft(Node<T> node) {
        Node<T> tempNode = node.right;
        Node<T> tempNode2 = tempNode.left;
        tempNode.left = node;
        node.right = tempNode2;
        node.parent = tempNode;
        if (tempNode2 != null) {
            tempNode2.parent = node;
        }
        return tempNode;
    }
    private Node<T> rotateRight(Node<T> node) {
        Node<T> tempNode = node.left;
        Node<T> tempNode2 = tempNode.right;
        tempNode.right = node;
        node.left = tempNode2;
        node.parent = tempNode;
        if (tempNode2 != null) {
            tempNode2.parent = node;
        }
        return tempNode;
    }

    /**
     * Method to delete the node of a given key. Returns true if the node is found
     * and deleted, false otherwise.
     */
    @Override
    public boolean delete(T item) {
        // IF THE ITEM IS NOT FOUND, RETURN.
        if (!contains(item)) {
            return false;
        }
        // OTHERWISE, CARRY ON DELETION.
        deleteNfix(item, rbtRoot);
        return true;
    }
    private Node<T> deleteItem(T item, Node<T> root) {
        Node<T> pointer = null;
        Node<T> curr = root;

        // Set the pointer to the desired item
        while (curr != null && curr.getItem().compareTo(item) != 0) {
            pointer = curr;
            if (item.compareTo(curr.getItem()) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        if (curr == null) {
            return root;
        }
        // DELETED NODE HAS NO CHILDREN
        if (curr.left == null && curr.right == null) {
            if (curr == rbtRoot) {
                clear();
            }
            if (curr != root) {
                if (pointer.left == curr) {
                    pointer.left = null;
                } else {
                    pointer.right = null;
                }
            } else {
                root = null;
            }
            return curr;
        }
        // DELETED NODE HAS 2 CHILDREN
        else if (curr.left != null && curr.right != null) {
            Node<T> suc = getInSuc(curr.left);
            if (!suc.isBlack()) {
                T temp = suc.getItem();
                suc=deleteItem(suc.getItem(), root);
                curr.setItem(temp);
                return suc;
            }
            Node<T> successor = getPredecessor(curr.right);
            T temp = successor.getItem();
            successor=deleteItem(successor.getItem(), root);
            curr.setItem(temp);
            return successor;
        }

        // DELETED NODE HAS 1 CHILD
        else {
            Node<T> child;
            if (curr.left != null) {
                if (!curr.isBlack()) {
                    child = curr.left;
                    if (curr == pointer.left) {
                        pointer.left = child;
                        child.parent=pointer;
                    } else {
                        pointer.right = child;
                        child.parent=pointer;
                    }
                    return null;
                }
                Node<T> suc = getInSuc(curr.left);
                item = suc.getItem();
                deleteItem(suc.getItem(), root);
                curr.setItem(item);

                return suc;
            } else {
                if (!curr.isBlack()) {
                    child = curr.right;
                    if (curr == pointer.left) {
                        pointer.left = child;
                        child.parent=pointer;
                    } else {
                        pointer.right = child;
                        child.parent=pointer;
                    }
                    return null;
                }
                Node<T> successor = getPredecessor(curr.right);
                T temp = successor.getItem();
                successor=deleteItem(successor.getItem(), root);
                curr.setItem(temp);
                return successor;
            }
        }
    }
    private Node<T> getPredecessor(Node<T> current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node<T> getInSuc(Node<T> current) {
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    private void deleteNfix(T item, Node<T> root) {
        Node<T> node = deleteItem(item, root);
        Node<T> parent = root.parent;
        rbtRoot.parent=null;
        if (rbtRoot == null) {

            clear();
            return;
        }
        if (node == null || !node.isBlack()) {//if null then removed is root or red
            return;
        }
        if (node.isBlack()) {//check if double black case
            if (parent == root) {
                root = null;
            } else {
                if ((node == null || node.isBlack()) && root.isBlack()) {
                    resolveDoubleBlack(node, node.parent);
                } else {
                    if (parent.left != root && parent.left != null) {
                        parent.left.setBlack(false);
                    } else if (parent.right != root && parent.right != null) {
                        parent.right.setBlack(false);
                    }
                }
            }
            return;
        }
    }

    private Node<T> resolveDoubleBlack(Node<T> root, Node<T> parent) {
        // CASE 00
        if (root == rbtRoot) {
            return root;
        } else {
            // check sibling
            Node<T> sibling = root.getSibling();
            if (case3(sibling)) {//sibling black and his children is black
                System.out.println("case3 ");
                if (!parent.isBlack()) {
                    parent.setBlack(true);
                    sibling.setBlack(false);
                    return parent;
                } else {
                    sibling.setBlack(false);
                    resolveDoubleBlack(parent, parent.parent);
                    return null;
                }
            }
            // CASE 04 sibling is red
            else if (!sibling.isBlack()) {
                swapColor(parent, sibling);
                if (root.isLeftChild()) {
                    if (parent == rbtRoot) {
                        rbtRoot = rotateLeft(parent);
                        rbtRoot.parent = null;
                        return null;
                    }
                    if (parent.isLeftChild()) {//rotate that if only to re arrange tree after rotation and all casses
                        Node<T> grand = parent.parent;
                        parent.parent.left = rotateLeft(parent);
                        grand.left.parent = grand;
                        return null;
                    } else {
                        Node<T> grand = parent.parent;
                        parent.parent.right = rotateLeft(parent);
                        grand.right.parent = grand;
                    }
                } else {
                    if (parent == rbtRoot) {
                        rbtRoot = rotateRight(parent);
                        rbtRoot.parent = null;
                        return null;
                    }
                    if (parent.isLeftChild()) {
                        Node<T> grand = parent.parent;
                        parent.parent.left = rotateRight(parent);
                        grand.left.parent = grand;
                    } else {
                        Node<T> grand = parent.parent;
                        parent.parent.right = rotateRight(parent);
                        grand.right.parent = grand;
                    }
                }
                resolveDoubleBlack(root, parent);
                return null;
            } else {
                // check first near child
                boolean nearSiblingChild = (root.isLeftChild() && (sibling.left != null && !sibling.left.isBlack()))
                        || (!root.isLeftChild() && (sibling.right != null && !sibling.right.isBlack()));
                boolean farSiblingChild = (root.isLeftChild() && (sibling.right != null && !sibling.right.isBlack()))
                        || (!root.isLeftChild() && (sibling.left != null && !sibling.left.isBlack()));
                // case 6 check first near child is red or far if yes then case 6 else go to case 5
                if (farSiblingChild) {
                    System.out.println("case 6 ");
                    if (root.isLeftChild()) {//rotate that if only to re arrange tree after rotation and all casses
                        sibling.right.setBlack(true);
                        swapColor(parent, sibling);
                        if (parent.parent == null) {
                            rbtRoot = rotateLeft(parent);
                            rbtRoot.parent = null;
                            return null;
                        }
                        if (parent.isLeftChild()) {
                            Node<T> grand = parent.parent;
                            parent.parent.left = rotateLeft(parent);
                            grand.left.parent = grand;
                        } else {
                            Node<T> grand = parent.parent;
                            parent.parent.right = rotateLeft(parent);
                            grand.right.parent = grand;
                        }
                    } else {
                        sibling.left.setBlack(true);
                        swapColor(parent, sibling);
                        if (parent == rbtRoot) {//rotate that if only to re arrange tree after rotation and all casses
                            rbtRoot = rotateRight(parent);
                            rbtRoot.parent = null;
                            return null;
                        }
                        if (parent.isLeftChild()) {
                            Node<T> grand = parent.parent;
                            parent.parent.left = rotateRight(parent);
                            grand.left.parent = grand;
                        } else {
                            Node<T> grand = parent.parent;
                            parent.parent.right = rotateRight(parent);
                            grand.right.parent = grand;
                        }
                    }
                    return rbtRoot;
                }
                // CASE 05 check first near child
                if (nearSiblingChild) {
                    if (root.isLeftChild()) {
                        swapColor(sibling, sibling.left);
                        sibling.parent.right = rotateRight(sibling);
                        parent.right.parent = parent;
                        resolveDoubleBlack(root, parent);
                        return null;
                    } else {
                        swapColor(sibling, sibling.right);
                        sibling.parent.left = rotateLeft(sibling);
                        parent.left.parent = parent;
                        resolveDoubleBlack(root, parent);
                        return null;
                    }
                }
            }
        }
        return root;
    }

    private void swapColor(Node<T> sibling, Node<T> parent) {
        boolean temp = sibling.isBlack();
        sibling.setBlack(parent.isBlack());
        parent.setBlack(temp);
    }

    private boolean case3(Node<T> sibling) {
        return (sibling.isBlack() &&
                (sibling.left == null || sibling.left.isBlack())
                && (sibling.right == null || sibling.right.isBlack()));
    }

    public void preOrderTraversal() {
        preOrderTraverse(this.rbtRoot);
    }

    public void preOrderTraverse(Node<T> root) {
        if (root == null) {
            return;
        }
        System.out.print(root.getItem() + " - Black ? " + root.isBlack() + " ");
        preOrderTraverse(root.left);
        preOrderTraverse(root.right);
    }

}
