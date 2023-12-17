package PriorityQueue;

public class PriorityQueueElement<T>{
    private T value;
    private int priority;

    //Each element is a value paired with its priority
    public PriorityQueueElement(T value, int priority)
    {
        this.value = value;
        this.priority = priority;
    }

    public T getValue(){
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public String toString()
    {
        return (value + " : " + priority);
    }
}
