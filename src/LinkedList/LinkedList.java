package LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {
    private LinkedListElement<T> front;
    private int length = 0;

    // Returns a string representation of the linked list
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        LinkedListElement<T> current = front;

        // Iterate through the linked list
        while (current != null) {
            sb.append(current.getValue());
            current = current.getNext();

            // Append comma if not the last element
            if (current != null) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    // Checks if the linked list is empty
    public boolean isEmpty() {
        return length == 0;
    }

    // Appends a value to the end of the linked list
    public void append(T value) {
        LinkedListElement<T> current;
        LinkedListElement<T> tail;

        // If the linked list is not empty, iterate to the end
        if (front != null) {
            current = front;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            tail = new LinkedListElement<T>(value, current, null);
            current.setNext(tail);
        } else {
            // If the linked list is empty, create a new front element
            front = new LinkedListElement<T>(value, null, null);
        }
        length++;
    }

    // Removes and returns the first element in the linked list
    public T pop() {
        T returnValue;

        if (!isEmpty()) {
            returnValue = front.getValue();
            front = front.getNext();

            // Update previous pointer if the new front is not null
            if (front != null) {
                front.setPrevious(null);
            }
            length--;
        } else {
            throw new UnsupportedOperationException("The linked list you are attempting to pop from is empty");
        }
        return returnValue;
    }

    // Removes and returns the element at the specified index
    public T remove(int index) {
        LinkedListElement<T> current = front;
        T returnValue;

        if (0 < index && index < length) {
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            returnValue = current.getValue();
            (current.getPrevious()).setNext(current.getNext());

            // Update the next element's previous pointer if it's not null
            if (current.getNext() != null) {
                (current.getNext()).setPrevious(current.getPrevious());
            }
            length--;
        } else if (index == 0) {
            returnValue = pop();
        } else {
            throw new IndexOutOfBoundsException("You tried to remove from an index of the linked list that is out of bounds");
        }
        return returnValue;
    }

    // Inserts a value at the specified index in the linked list
    public void insert(T value, int index) {
        if (index > length) {
            throw new IndexOutOfBoundsException("The index you are attempting to insert a value into is larger than the length of the linked list");
        }

        LinkedListElement<T> current = front;
        LinkedListElement<T> inserted;

        if (0 < index && index < length) {
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }

            inserted = new LinkedListElement<T>(value, current.getPrevious(), current);
            (current.getPrevious()).setNext(inserted);
            current.setPrevious(inserted);

            length++;
        } else if (index == 0) {
            inserted = new LinkedListElement<T>(value, null, front);
            front.setPrevious(inserted);
            front = inserted;
            length++;
        } else if (index == length) {
            append(value);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Returns the number of elements in the linked list
    public int length() {
        return length;
    }

    // Checks if the linked list contains a specific value
    public boolean contains(T value) {
        LinkedListElement<T> current;
        current = front;
        boolean isInList = false;

        // Iterate through the linked list to check for the value
        for (int i = 0; i < length; i++) {
            if (current.getValue().equals(value)) {
                isInList = true;
                break;
            }
            current = current.getNext();
        }
        return isInList;
    }

    // Returns the value at the specified index in the linked list
    public T getValue(int index) {
        int counter = 0;
        LinkedListElement<T> current = front;

        // Check for a valid index
        if (index >= length()) throw new IllegalArgumentException("Index : " + index);

        // Iterate to the specified index
        while (counter < index) {
            current = current.getNext();
            counter++;
        }
        return current.getValue();
    }

    // Returns the element at the specified index in the linked list
    public LinkedListElement<T> get(int index) {
        int counter = 0;
        LinkedListElement<T> current = front;

        // Check for a valid index
        if (index >= length()) throw new IllegalArgumentException("Index : " + index);

        // Iterate to the specified index
        while (counter < index) {
            current = current.getNext();
            counter++;
        }
        return current.get();
    }

    // Returns an iterator for the linked list
    public Iterator iterator() {
        return new LinkedListIterator();
    }

    // Private class representing an iterator for the linked list
    private class LinkedListIterator implements Iterator<T> {
        private LinkedListElement<T> current = front;

        // Checks if there is a next element in the linked list
        public boolean hasNext() {
            return current != null;
        }

        // Returns the next element in the linked list
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T value = current.getValue();
            current = current.getNext();
            return value;
        }
    }
}
