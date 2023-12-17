package PriorityQueue;

import LinkedList.LinkedList;

public class PriorityQueue<T> {
    // LinkedList to store elements with priorities
    private final LinkedList<PriorityQueueElement<T>> queue = new LinkedList<PriorityQueueElement<T>>();
    private int length = 0; // Track the number of elements in the queue

    // Adds an element to the queue with a specified value and priority
    public void enQueue(T value, int priority) {
        PriorityQueueElement<T> toAdd = new PriorityQueueElement<T>(value, priority);

        if (queue.isEmpty()) {
            // Adds Element to the front of the empty queue
            queue.append(toAdd);
        } else {
            // Iterate through the queue until the element's priority is less than the element being inspected,
            // then insert the element before that
            int i;
            for (i = 0; i < queue.length(); i++) {
                if (priority < queue.getValue(i).getPriority()) {
                    queue.insert(toAdd, i);
                    break;
                }
            }
            // If the item has the lowest priority in the queue, just append it to the queue
            if (i == queue.length()) {
                queue.append(toAdd);
            }
        }
        length++;
    }

    // Removes and returns the first item in the queue
    public PriorityQueueElement<T> deQueue() {
        PriorityQueueElement<T> item = queue.remove(0);
        length--;
        return item;
    }

    // Removes and returns the first item in the queue with the given value
    public PriorityQueueElement<T> deQueue(T value) {
        for (int i = 0; i < queue.length(); i++) {
            if (queue.getValue(i).getValue().equals(value)) {
                length--; // Decrement the length when an item is removed
                return queue.remove(i);
            }
        }
        throw new IllegalArgumentException("Value was not found");
    }

    // Displays the list as a string output
    public void display() {
        System.out.print("[");
        for (int i = 0; i < queue.length(); i++) {
            if (i == queue.length() - 1 || queue.length() == 1) {
                System.out.print(queue.getValue(i).getValue());
            } else {
                System.out.print(queue.getValue(i).getValue() + ", ");
            }
        }
        System.out.println("]");
    }

    // To amend the priority of a specific value in the queue
    public void setPriority(T value, int priority) {
        deQueue(value);
        enQueue(value, priority);
    }

    // Gets the priority of a value in the queue
    public int getPriority(T value) {
        boolean isFound = false;
        int i = 0;
        while (!isFound && i != queue.length()) {
            if (queue.getValue(i).getValue().equals(value)) {
                isFound = true;
            }
            i++;
        }
        return queue.getValue(i - 1).getPriority();
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Returns a string representation of the queue
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < queue.length(); i++) {
            s.append(queue.getValue(i).getValue()).append(" : ");
            if (i == queue.length() - 1 || queue.length() == 1) {
                s.append(queue.getValue(i).getPriority());
            } else {
                s.append(queue.getValue(i).getPriority()).append(", ");
            }
        }
        s.append("]");
        return s.toString();
    }

    // Returns the number of elements in the queue
    public int getLength() {
        return length;
    }
}
