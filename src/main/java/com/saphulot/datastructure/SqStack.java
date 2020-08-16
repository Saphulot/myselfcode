package com.saphulot.datastructure;

import java.util.*;

public class SqStack {
    private int[] stackElem;
    private int maxSize;
    private int top = -1; //栈顶位置


    public SqStack(int maxSize){
        this.maxSize = maxSize;
        stackElem = new int[this.maxSize];
    }

    public boolean isFull(){
        return top == this.maxSize - 1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public void push(int value){
        if (isFull()){
            System.out.println("栈满");
            return;
        }
        top++;
        stackElem[top] = value;
    }

    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈空");
        }
        int value = stackElem[top];
        top--;
        return value;

    }

    public void list(){
        if (isEmpty()){
            throw new RuntimeException("栈空，没有数据");
        }

        for(int i = top; i>=0; i --){
            System.out.printf("stackElem[%d]=%d\n",i,stackElem[i]);
        }
    }
}

class Test{
    // 栈实现后缀表达式计算器
    public static int calculate(List<String> ls){
        Stack<String> stack = new Stack<String>();

        for (String element: ls) {
            if (element.matches("\\d+")){
                stack.push(element);
            }else {
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int result = 0;
                if (element.equals("+")){
                    result = num2 + num1;
                }else if(element.equals("-")){
                    result = num2 - num1;
                }else if(element.equals("*")){
                    result = num2 * num1;
                }else if(element.equals("/")){
                    result = num2 / num1;
                }
                stack.push(""+result);
            }
        }
        return Integer.parseInt(stack.pop());
    }
    public static void main(String[] args) {
        SqStack sqStack = new SqStack(10);
        sqStack.push(1);
        sqStack.push(2);
        sqStack.push(3);
        sqStack.push(4);
        sqStack.push(5);
        sqStack.push(6);
        sqStack.list();
        System.out.println(sqStack.pop());
        String cal = "22+33-43";
        List list = Arrays.asList("22","33","+","44","-");
        System.out.println(calculate(list));
    }
}