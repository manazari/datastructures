/**
 * This class tests the YoHashMap class by creating an instance of it
 * then trying to add 3 values
 */
public class YoHashMapTester_Nazari {
    public static void main(String[] args) {
        System.out.println("\n##################");
        System.out.println("Creating a YoHashMap...");
        YoHashMap<String> map = new YoHashMap<>();
        String key = "any key";
        String value = "any value";
        System.out.println("size of map: " + map.size());
        System.out.println("map contain \"" + key + "\": " + map.containsKey(key));
        System.out.println("value at key \"" + key + "\": \"" + map.get(key) + "\"");
        
        System.out.println("\n##################");
        key = "a";
        value = "matthew";
        System.out.println("putting (\"" + key + "\", \"" + value + "\")");
        map.put(key, value);
        System.out.println("size of map: " + map.size());
        System.out.println("map contain \"" + key + "\": " + map.containsKey(key));
        System.out.println("value at key \"" + key + "\": \"" + map.get(key) + "\"");
        
        System.out.println("\n##################");
        key = "aa";
        value = "tanay";
        System.out.println("putting (\"" + key + "\", \"" + value + "\")");
        map.put(key, value);
        System.out.println("size of map: " + map.size());
        System.out.println("map contain \"" + key + "\": " + map.containsKey(key));
        System.out.println("value at key \"" + key + "\": \"" + map.get(key) + "\"");

        System.out.println("\n##################");
        key = "b";
        value = "lebron james";
        System.out.println("putting (\"" + key + "\", \"" + value + "\")");
        map.put(key, value);
        System.out.println("size of map: " + map.size());
        System.out.println("map contain \"" + key + "\": " + map.containsKey(key));
        System.out.println("value at key \"" + key + "\": \"" + map.get(key) + "\"");
    }
}

/**
 * This interface is a blueprint for everything YoHashMap must
 * accomplish for the assignment
 */
interface HashMap<K, V> {
    public boolean containsKey(K key);
    public V get(K key);
    public V put(K key, V value);
    public V remove(K key);
    public int size();
}

/**
 * This class is an implementation of a hash map where the key is
 * a string and the hash of the key is calculated by simply taking
 * the length of the string. Basically, the value associated with a key is
 * found at the index of the key's hash code which is its length
 */
class YoHashMap<V> implements HashMap<String, V> {
    private Object[] values;
    private int capacity;
    private int size;
    private final int INITIAL_CAP = 16;

    public YoHashMap() {
        capacity = INITIAL_CAP;
        size = 0;
        values = new Object[capacity];
    }

    /**
     * Basic doubling of values array in case the key string is longer
     * than length of array
     */
    private void grow() {
        capacity *= 2;
        Object[] temp = new Object[capacity];
        for (int i = 0; i < values.length; i++) temp[i] = values[i];
        values = temp;
    }

    /**
     * Hash of key is calculated by taking the length of the string
     * 
     * @param key key to find hash of
     * @return hashcode of key
     */
    private int hashOf(String key) {
        return key.length();
    }

    /**
     * Will grow the values array until it can hold the index for
     * which the key's hash corresponds to. Then it will return
     * whatever was at that spot and return it. If there was a null
     * value, it means nothing was there so only then is the size
     * of the map increased by 1
     * 
     * @param key key to put value at
     * @return old value on map at key
     */
    public V put(String key, V value) {
        int index = hashOf(key);
        while (index > capacity) grow();
        Object oldValue = values[index];
        values[index] = value;
        if (oldValue == null) size++;
        return (V) oldValue;
    }

    /**
     * If a null value corresponds to the key, then it is not contained
     * 
     * @param key key to inquire about
     * @return whether key is contained in map
     */
    public boolean containsKey(String key) {
        int index = hashOf(key);
        return index < capacity && values[index] != null;
    }

    /**
     * Returns value corresponding to key
     * 
     * @param key key to get
     * @return value at key
     */
    public V get(String key) {
        int index = hashOf(key);
        if (index >= capacity) return null;
        return (V) values[index];
    }

    /**
     * Removes value at key by setting it to null then returning the
     * value. If the value is null, then only then the size won't
     * decrease as it means nothing was removed
     * 
     * @param key key to remove
     * @return value at key removed
     */
    public V remove(String key) {
        if (!containsKey(key)) return null;
        int index = hashOf(key);
        Object value = values[index];
        values[index] = null;
        if (value != null) size--;
        return (V) value;
    }

    /**
     * Returns number of entries in map
     * 
     * @return number of entries
     */
    public int size() {
        return size;
    }
}