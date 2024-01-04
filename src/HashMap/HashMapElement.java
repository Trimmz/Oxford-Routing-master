/*
 * HashMapElement class represents an element in a generic key-value pair for a HashMap
 */

package HashMap;

public class HashMapElement<K, V>{
    private K key;
    private V value;

    //Each Element Is A Key-Value Pair
    public HashMapElement(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return key;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String toString()
    {
        return key.toString() + ":" + value.toString();
    }
}
