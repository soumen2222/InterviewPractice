package com.hackerRank.StackQueue;

public class MaxStack {
        private int N;
        private Node first;
        private Node max;

        private class Node {
            private double item =0;
            private Node next;
        }

        public MaxStack() {
            N = 0;
            first = null;
            max = null;
        }

        public double getMax() {
            return max.item;
        }

        public void push(double item) {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            N++;
            if (item >= getMax()) {
                Node oldmax = max;
                max = new Node();
                max.next = oldmax;
            }
        }

        public double pop() {
            double tmp = first.item;
            first = first.next;
            N--;
            if (tmp == getMax()) {
                max = max.next;
            }
            return tmp;
        }
        
        public static void main(String[] args)
        {
        	MaxStack m = new MaxStack();
        	m.push(1);
        	m.push(4);
        	m.push(2);
        	m.push(7);
        	m.push(8);
        	
        }
    }
