package io.github.some_example_name.Pathfinding;

import java.util.ArrayList;

public class PriorityQueue {

    public class Node{
        int x, y, prio;

        Node(int x, int y, int prio){
            this.x = x;
            this.y = y;
            this.prio = prio;
        }
    }

    public ArrayList<Node> heap = new ArrayList<>();

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    public void add(int x, int y, int prio){
        heap.add(new Node(x, y, prio));
        heapifyUp(heap.size() - 1);
    }

    public int[] poll(){
        if (heap.isEmpty()){
            throw new IllegalStateException("Priority queue is empty");
        }

        Node Root = heap.get(0);
        int[] Result = new int[]{Root.x, Root.y};

        if (heap.size() == 1){
            heap.clear();
        }
        else {
            Node Last = heap.remove(heap.size() - 1);
            heap.set(0, Last);
            heapifyDown(0);
        }
        return Result;
    }

    private void heapifyUp(int index){
        while (index > 0){
            int parent = (index - 1) / 2;
            if (heap.get(index).prio >= heap.get(parent).prio) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index){
        while (true){
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;

            if (left < heap.size() && heap.get(left).prio < heap.get(smallest).prio){
                smallest = left;
            }

            if (right < heap.size() && heap.get(right).prio < heap.get(smallest).prio){
                smallest = right;
            }

            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int a, int b){
        Node temp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, temp);
    }
}
