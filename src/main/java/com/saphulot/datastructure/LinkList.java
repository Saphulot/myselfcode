package com.saphulot.datastructure;

import java.util.Iterator;

public class LinkList<T> implements Iterable{
    private Node head;
    private int N;

    @Override
    public Iterator iterator() {
        return new LIterable();
    }

    private class LIterable implements Iterator{
        private Node n;
        public LIterable(){
            this.n = head;
        }
        @Override
        public boolean hasNext() {
            return n.next!=null;
        }

        @Override
        public Object next() {
            return n.next;
        }
    }

    private class Node {
        T item;
        Node next;

        public Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public LinkList() {
        this.head = new Node(null, null);
        this.N = 0;
    }

    public void clear() {
        head.next = null;
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int length() {
        return N;
    }

    public T get(int i) {
        Node node = head.next;
        for (int index = 0; index < i; index++) {
            node = node.next;
        }

        return node.item;
    }

    public void insert(T t) {
        Node node = head;

//        for (int i = 0; i < N; i++) {
//            node = node.next;
//        }
        while (node.next != null){
            node = node.next;
        }

        Node newNode = new Node(t, null);

        node.next = newNode;

        N++;
    }

    public void insert(int i, T t) {
        Node pre = head;
        for(int index = 0; index <= i-1; index++){
            pre = pre.next;
        }

        Node nextNode = pre.next;

        Node curr = new Node(t,null);

        curr.next = nextNode;

        pre.next = curr;

        N++;
    }

    public T remove(int i) {
        Node pre = head;
        for(int index = 0; index <= i-1; index++){
            pre = pre.next;
        }

        Node currNode = pre.next;

        pre.next = currNode.next;

        N--;

        return currNode.item;
    }

    public int indexOf(T t) {
        Node pre = head;
        for(int i = 0; pre.next != null; i++){
            if (pre.next.item.equals(t)){
                return i;
            }
        }
        return -1;
    }

}
