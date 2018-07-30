/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;
    }

    Node head;

    /*
    if list contains more than one node
    inserts newNode before addBefore
    */
    private void insertBetweenNodes(Node addBefore, Node newNode) {
        addBefore.prev.next = newNode;
        newNode.prev = addBefore.prev;
        newNode.next = addBefore;
        addBefore.prev = newNode;
    }

    public void insertBeginning(Point p) {
        Node newNode = new Node();
        newNode.point = p;
        if(head == null) {
            newNode.next = newNode;
            newNode.prev = newNode;
        }
        else {
            insertBetweenNodes(head, newNode);
        }
        head = newNode;
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        if(head == head.next.next) {
            return distanceBetween(head.point, head.next.point);
        }
        float total = 0;
        Node p = head;
        do {
            total += distanceBetween(p.point, p.next.point);
            p = p.next;
        }while(p != head);
        return total;
    }

    public void insertNearest(Point p) {
        if(head == null) {
            insertBeginning(p);
        }
        else {
            float dis = distanceBetween(head.point, p);
            Node small = head;
            Node temp = head.next;
            while(true) {
                if(dis > distanceBetween(p, temp.point)) {
                    small = temp;
                    dis = distanceBetween(p, temp.point);
                }
                if(temp.next == head) {
                    break;
                }
                temp = temp.next;
            }
            boolean flag = false;
            if(small == head.prev) {
                flag = true;
            }
            Node newNode = new Node();
            newNode.point = p;
            Node h = small.next;
            insertBetweenNodes(h, newNode);
            if(flag) {
                head = newNode;
            }
        }
    }

    public void insertSmallest(Point p) {
        if(head == null) {
            insertBeginning(p);
        }
        else {
            float dis = distanceBetween(head.point, p) + distanceBetween(head.next.point, p) - distanceBetween(head.point, head.next.point);
            Node small = head;
            Node temp = head.next;
            while(true) {
                if(dis > distanceBetween(p, temp.point) + distanceBetween(p, temp.next.point) - distanceBetween(temp.point, temp.next.point)) {
                    small = temp;
                    dis = distanceBetween(p, temp.point) + distanceBetween(p, temp.next.point) - distanceBetween(temp.point, temp.next.point);
                }
                if(temp.next == head) {
                    break;
                }
                temp = temp.next;
            }
            boolean flag = false;
            if(small == head.prev) {
                flag = true;
            }
            Node newNode = new Node();
            newNode.point = p;
            Node h = small.next;
            insertBetweenNodes(h, newNode);
            if(flag) {
                head = newNode;
            }
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
