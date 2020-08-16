package com.saphulot.datastructure;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    private int value;
    private BinaryTree leftChild;
    private BinaryTree rightChild;


    public BinaryTree(int value){
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }

    public void insertLeft(int value){
        if (leftChild == null){
            this.leftChild = new BinaryTree(value);
        }else {
            BinaryTree newNode = new BinaryTree(value);
            newNode.setLeftChild(this.leftChild);
            this.setLeftChild(newNode);
        }
    }

    public void insertRight(int value){
        if (rightChild == null){
            this.rightChild = new BinaryTree(value);
        }else {
            BinaryTree newNode = new BinaryTree(value);
            newNode.setRightChild(this.rightChild);
            this.setRightChild(newNode);
        }
    }

    //先序遍历
    public void preOrder(){
        System.out.println(this.value);

        if (this.leftChild != null){
            this.leftChild.preOrder();
        }

        if (this.rightChild != null){
            this.rightChild.preOrder();
        }
    }

    //中序遍历
    public void inOrder(){
        if (this.leftChild != null){
            this.leftChild.inOrder();
        }
        System.out.println(this.value);

        if (this.rightChild != null){
            this.rightChild.inOrder();
        }
    }

    //后序遍历
    public void outOrder(){
        if (this.leftChild != null){
            this.leftChild.outOrder();
        }


        if (this.rightChild != null){
            this.rightChild.outOrder();
        }

        System.out.println(this.value);
    }

    //广度优先搜索
    public void bfs(){
        Queue<BinaryTree> queue = new LinkedList<BinaryTree>();
        queue.offer(this);

        while (!queue.isEmpty()){
            BinaryTree b = queue.poll();
            System.out.println(b.getValue());

            if (b.getLeftChild() != null){
                queue.offer(b.getLeftChild());
            }

            if (b.getRightChild() != null){
                queue.offer(b.getRightChild());
            }
        }
    }

    public void setLeftChild(BinaryTree leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(BinaryTree rightChild) {
        this.rightChild = rightChild;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinaryTree getLeftChild() {
        return leftChild;
    }

    public BinaryTree getRightChild() {
        return rightChild;
    }

    public int getValue() {
        return value;
    }
}
