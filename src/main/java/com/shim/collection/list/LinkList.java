package com.shim.collection.list;

/**
 * @类描述：
 * @创建人：xn064961
 * @创建时间：2017/11/8 14:19
 * @版权：Copyright (c) 深圳市牛鼎丰科技有限公司-版权所有.
 */
public class LinkList {
    class Node {
        int data;
        Node next;

        public Node (int data) {
            this.data = data;
        }
    }

    private Node head;
    private Node current;

    /**
     * 方法：添加元素
     * @param data
     */
    public void add (int data) {
        if (head == null) {
            head = new Node(data);
            current = head;
        } else {
            current.next = new Node(data);
            current = current.next;
        }
    }

    /**
     * 方法：从指定节点开始打印链表元素
     * @param node
     */
    public void print (Node node) {
        if (node == null) {
            return;
        }

        current = node;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    /**
     * 方法：获取链表长度
     * @param head
     * @return
     */
    public int getLength(Node head) {
        if (head == null) {
             return 0;
         }

        int length = 0;
        Node current = head;
        while (current != null) {
             length++;
             current = current.next;
         }

        return length;
    }

    /**
     * 方法：找出倒数第k个节点
     * @param node
     * @param index
     * @return
     */
    public Node getLastK (Node node, int index) {
        Node first = head;
        Node second = head;

        while(second.next != null) {
            while (index-- > 1) {
                second = second.next;
            }
            first = first.next;
            second = second.next;
        }
        return first;
    }

    public static void main(String[] args) {
        LinkList list = new LinkList();
        //向LinkList中添加数据
        for (int i = 0; i < 10; i++) {
           list.add(i);
        }

        list.print(list.head);// 从head节点开始遍历输出
        }

}
