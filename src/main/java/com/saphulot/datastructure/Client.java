package com.saphulot.datastructure;

public class Client {
    public static void main(String[] args) {
        BinaryTree a = new BinaryTree(1);

        a.insertLeft(2);
        a.insertRight(3);

        BinaryTree b = a.getLeftChild();
        b.insertLeft(4);

        BinaryTree c = a.getRightChild();
        c.insertLeft(5);
        c.insertRight(6);

        BinaryTree d = b.getLeftChild();
        BinaryTree e = c.getLeftChild();
        BinaryTree f = c.getRightChild();

        System.out.println(a.getValue());
        System.out.println(b.getValue());
        System.out.println(c.getValue());
        System.out.println(d.getValue());
        System.out.println(e.getValue());
        System.out.println(f.getValue());

        System.out.println("*********深度优先遍历************");
        System.out.println("*********先序遍历**************");
        a.preOrder();
        System.out.println("*********中序遍历**************");
        a.inOrder();
        System.out.println("*********后序遍历**************");
        a.outOrder();

        System.out.println("*********广度优先遍历*************");
        a.bfs();


    }
}
