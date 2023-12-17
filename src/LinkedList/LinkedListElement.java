package LinkedList;

public class LinkedListElement <T>{
    // Creating each element such that it stores a value and a pointer to the previous element and the next element
    private T value;
    private LinkedListElement<T> previous;
    private LinkedListElement<T> next;

    //Upon creation of the element, its value, previous pointer and next pointer must be initialised
    public LinkedListElement(T value, LinkedListElement<T> previous, LinkedListElement<T> next)
    {
        this.value = value;
        this.previous = previous;
        this.next = next;
    }

    //Overriding the toString function allows us to call System.out.println(element) to output the value onto the console
    @Override
    public String toString()
    {
        return String.valueOf(value);
    }

    //Getters and setters for the data stored by this class
    public T getValue()
    {
        return value;
    }

    public LinkedListElement<T> get()
    {
        return this;
    }
    //TODO maybe add in a setValue()

    public LinkedListElement<T> getPrevious()
    {
        return previous;
    }

    public void setPrevious(LinkedListElement<T> newElement)
    {
        previous = newElement;
    }
    public LinkedListElement<T> getNext()
    {
        return next;
    }
    public void setNext(LinkedListElement<T> newElement)
    {
        next = newElement;
    }
}
