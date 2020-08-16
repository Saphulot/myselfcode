package com.saphulot.datastructure;

public class BinarySearchTree {
    private int value;
    private BinarySearchTree leftChild;
    private BinarySearchTree rightChild;


    public BinarySearchTree(int value){
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }

    public void insertNode(int value){
        if (value < this.value && this.leftChild != null){
            this.leftChild.insertNode(value);
        } else if(value < this.value){
            this.leftChild = new BinarySearchTree(value);
        } else if(value > this.value && this.rightChild != null){
            this.rightChild.insertNode(value);
        } else {
            this.rightChild = new BinarySearchTree(value);
        }
    }


    public int getValue() {
        return value;
    }

    public BinarySearchTree getLeftChild() {
        return leftChild;
    }

    public BinarySearchTree getRightChild() {
        return rightChild;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setRightChild(BinarySearchTree rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeftChild(BinarySearchTree leftChild) {
        this.leftChild = leftChild;
    }
}
