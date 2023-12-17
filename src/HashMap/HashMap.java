package HashMap;

import LinkedList.LinkedList;

public class HashMap<K, V>{

    private int maxSize;
    private int size = 0;

    private boolean wasModified;
    HashMapElement<K, V>[] items;

    // Initialises an array of key value pairs
    public HashMap(int size) {
        maxSize = size;
        items = new HashMapElement[maxSize];
    }

    public HashMap(int size, boolean wasModified) {
        this(size);
        this.wasModified = wasModified;
    }

    // Adds a key-value pair to the hash map
    public void add(K key, V value) {
        boolean itemAdded = false;
        int index = key.hashCode() % maxSize;

        // Check if the list is full, and if it is show an error, else add an item
        if (isFull()) {
            throw new UnsupportedOperationException("There are no empty spaces in the hashtable");
        }

        if (contains(key)) {
            throw new IllegalArgumentException("This key already exists in the hashtable");
        }

        // Checks if that index in the array is unoccupied
        if (items[index] == null) {
            items[index] = new HashMapElement<K, V>(key, value);
            size++;
        } else {
            // Iterates through the array as long as the current index is not occupied or is less than the max size
            while (index < maxSize && !itemAdded) {
                if (items[index] != null) {
                    index++;
                } else {
                    // If there is a free spot the item will be added that the current index
                    items[index] = new HashMapElement<K, V>(key, value);
                    itemAdded = true;
                    size++;
                }
                index = index % maxSize;
            }
        }

    }

    // Deletes a key-value pair from the hash map based on the key
    public void delete(K key) {
        int index = key.hashCode() % maxSize;
        boolean loopedOnce = false;
        int originalIndex = index;
        boolean wasDeleted = false;
        // If the key of the value is at its meant-to-be index, it deletes the value of the item at the current index
        if (items[index] != null && items[index].getKey() == key) {
            items[index] = null;
            size--;
        } else {
            while (index < maxSize && !wasDeleted) {
                if (items[index] == null) {
                    index++;
                } else {
                    // If there is a free spot the item will be added that the current index
                    if (items[index].getKey() == key) {
                        items[index] = null;
                        wasDeleted = true;
                        size++;
                    } else {
                        index++;
                    }
                    wasDeleted = true;
                }
                index = index % maxSize;
                if (index == 0) {
                    loopedOnce = true;
                }
                if (loopedOnce && index == originalIndex) {
                    throw new IllegalArgumentException("This key was not found and so could not be deleted");
                }
            }
        }
    }

    // Retrieves the value associated with any given key
    public V getValue(K key) {
        int index = key.hashCode() % maxSize;
        // If the key of the value is at its meant-to-be index, it returns true

        if (!contains(key)) {
            throw new IllegalArgumentException("This key was not found");
        }

        if (items[index] != null && items[index].getKey() == key) {
            return items[index].getValue();
        } else {
            while (index < maxSize) {
                if (items[index] == null) {
                    index++;
                } else {
                    if (items[index].getKey() == key) {
                        return items[index].getValue();
                    } else {
                        index++;
                    }
                }
                index = index % maxSize;
            }
        }
        return null;
    }

    // Checks if the hashmap contains a given key
    public boolean contains(K key) {
        int index = key.hashCode() % maxSize;
        int originalIndex = index;
        boolean loopedOnce = false;
        boolean itemFound = false;
        // If the key of the value is at its meant-to-be index, it returns true
        if (items[index] != null && items[index].getKey() == key) {
            itemFound = true;
        } else {
            while (index < maxSize && !itemFound) {
                if (items[index] == null) {
                    index++;
                } else {
                    if (items[index].getKey() == key) {
                        itemFound = true;
                    } else {
                        index++;
                    }
                }
                index = index % maxSize;
                if (index == 0) {
                    loopedOnce = true;
                }
                if (loopedOnce && index == originalIndex) {
                    break;
                }
            }
        }
        return itemFound;
    }

    public int length() {
        int count = 0;
        for (int i = 0; i < maxSize; i++) {
            // Adds to the counter every time there is an instance of an item
            if (items[i] != null) {
                count++;
            }
        }
        return count;
    }



    public boolean isEmpty() {
        boolean isEmpty = true;
        for (int i = 0; i < maxSize; i++) {
            // If there is an item in the array, it is not empty
            if (items[i] != null) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    public boolean isFull() {
        boolean isFull = true;
        for (int i = 0; i < maxSize; i++) {
            // If there are any free spots in the array, it is not full
            if (items[i] == null) {
                isFull = false;
                break;
            }
        }
        return isFull;
    }

    public String toString() {
        StringBuilder out = new StringBuilder("");

        for (HashMapElement<K, V> item : items) {
            if (item != null) {
                out.append(item);
                out.append(", ");
            }
            if (wasModified) {
                out.append("\n\t");
            }
        }
        if (!isEmpty()) {
            out.delete((wasModified) ? out.length() - 3 : out.length() - 2, out.length());
        }
        return out.toString();
    }

    public HashMapElement getItem(K key) {
        // TODO MAKE IT O(1)
        HashMapElement<K, V> item = null;
        for (int i = 0; i < maxSize; i++) {
            HashMapElement<K, V> tempItem = items[i];
            if (tempItem != null) {
                if (tempItem.getKey() == key) {
                    item = tempItem;
                }
            }
        }
        return item;
    }

    public void changeValue(K key, V value) {
        getItem(key).setValue(value);
    }

    public int getMaxSize() {
        return maxSize;
    }

    // Retrieves the keys from the hash map and returns them in a linked list of characters
    public LinkedList<Character> getNodeArray() {
        LinkedList<Character> charArray = new LinkedList<Character>();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                charArray.append((Character) items[i].getKey());
            }
        }
        return charArray;
    }

    // Retrieves the keys from the hash map and returns them in a linked list
    public LinkedList<K> getKeys()
    {
        LinkedList<K> keyList = new LinkedList<K>();
        for (HashMapElement<K, V> item : items) {
            if (item != null) {
                keyList.append(item.getKey());
            }
        }
        return keyList;
    }

    public void update(K key, V value)
    {
        this.getItem(key).setValue(value);
    }


}