package com.sujit.collections;

/**
 * Map demostration class.
 * 
 * @author Sujit Roy
 */
public class DemoMap<K,V> {
    
    // first define a static inner class which will hold the key value mapping entries in a linkedlist node
    static class Entry<K,V> {
        K key;
        V value;
        Entry<K,V> nextEntry;
        
        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.nextEntry = next;
        }
    }
    
    private Entry<K,V>[] mapArray;
    private int capacity = 10;
    
    public DemoMap(){
        mapArray = new Entry[capacity];
    }
    
    /**
     * stores the provided key value mapping into the map.
     * if a key value mapping already exists then it replaces old value with the new value.
     * 
     * @param key
     *          the key of the mapping to be added
     * @param value
     *          the value of the mapping to be added
     * 
     */
    public void put(K key, V value) {
        int keyHash = getHash(key);
        if(mapArray[keyHash]!=null){
            Entry<K,V> node = mapArray[keyHash];
            do{
                if(node.key.equals(key)){
                    node.value = value; 
                    return;
                }
                node = node.nextEntry;
            }while(node.nextEntry!=null);
            Entry<K,V> newEntry = new Entry(key, value, null);
            node.nextEntry = newEntry; 
        }else{
            Entry<K,V> newEntry = new Entry(key, value, null);
            mapArray[keyHash] = newEntry;
        }
    }
    
   /**
    * retrieves the value against a given key, if entry is available in the map.
    * if the key value mapping is not available then it returns null.
    * @param key
    *           the key of the mapping to be retrieved
    * @return value
    *           the value stored against the key or null if not available
    */
    public V get(K key){
        int hashBucket = getHash(key);
        Entry<K,V> bucketNode = mapArray[hashBucket];
        do {
            if(bucketNode.key.equals(key)){
                return bucketNode.value;
            }
            bucketNode = bucketNode.nextEntry;
        } while (bucketNode!=null);
        return null;
    }
    
    /**
     * removes a key value mapping from the map if entry exists
     * @param key
     *            the key of the mapping to be removed
     * @return value
     *            the value of the removed mapping or null if not available
     */
    public V remove(K key) {
        int hashBucket = getHash(key);
        Entry<K,V> current, prev;
        current = prev = mapArray[hashBucket]; 
        do {
            if(current.key.equals(key)){
                V val = current.value;
                if(current.nextEntry == null && prev == current){
                    current = prev = null;
                    mapArray[hashBucket] = null;
                }else{
                    prev.nextEntry = current.nextEntry;
                    current = null;
                }
                return val;
            }
            prev = current;
            current = current.nextEntry;
        } while (current!=null);
        return null;    
    }
    
    /**
     * displays the contents of the map. each key value entry in a new line
     */
    public void display(){
        System.out.println("DemoMap contains below entries...");
        for(int i = 0; i < capacity; i++){
            Entry<K, V> node = mapArray[i];
            if(node!=null){
               do {
                   System.out.println("[ Key: "+node.key+", Value: "+node.value+" ]");
                   node = node.nextEntry;
               } while (node!=null); 
            }
        }
    }
    
    /**
     * Returns the number of key value entries present in the map
     */
    public int size(){ 
        int size = 0;
        for(int i = 0; i < capacity; i++){
            Entry<K, V> node = mapArray[i];
            if(node!=null){
               do {
                   size++;
                   node = node.nextEntry;
               } while (node!=null); 
            }
        }
        return size; 
    }
    
    private final Entry<K,V> getEntry(K key){
        // TO DO
        return null;
    }
    
    public boolean containsKey(K key){
        // To DO
        return false;
    }
    
    public boolean containsValue(K key){
        // TO DO
        return false;
    } 
    
    /**
     * Computes the hash value for a given key from the context of the local bucket capacity. 
     * The actual hash of the key is then pit against total capacity of the hashmap (number of buckets) 
     * in an attempt to get even distribution of the entries.
     * 
     * For better understanding of Java default hashMap implementation please check - 
     * https://en.wikipedia.org/wiki/Java_hashCode()
     */
    private int getHash(K key){
        int hash = key!=null?key.hashCode():0;
        return Math.abs(hash) % capacity;
    }
}