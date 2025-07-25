import java.util.*;


/**
 * Design Cache with LRU eviction policy.
 * * GPT suggestions:
 * Suggestions for Improvement
Naming Consistency:
CacheManager is the main cache, but LRUCacheImpl is empty. Consider merging or clarifying their roles.
Typically, the main cache class is called LRUCache.

Encapsulation:
Make fields like head, tail, and keyMappings private and provide only necessary access.

Responsibility:
CacheManager and LRUCacheImpl seem to overlap. Prefer a single class for the cache, or clearly separate responsibilities (e.g., CacheManager for orchestration, LRUCacheImpl for implementation).

Testing:
Add a simple main/test method to demonstrate usage.

Example Structure
A typical LRU cache design for interviews would look like:

LRUCache<K, V> (main class, implements Cache<K, V>)
Uses a HashMap<K, Node<K, V>> for O(1) access
Uses a doubly linked list for O(1) LRU ordering
Node class for DLL nodes
 */

interface Cache<K,V> {
    public void put(K key, V value);
    V remove (K key);
    V get(K key);
}

class LRUCacheImpl<K,V> implements Cache<K,V> {
    @Override
    public void put(K key, V value) {

    }

    @Override
    public V get(K key) {
        return null;
    }
    @Override 
    public V remove(K key) {
        return null;
    }
}

class CacheManager<K,V> {
    private int capacity;
    private HashMap<K,Node<K,V>> keyMappings;
    private NodeAccessPattern<K,V> nodeAccessPattern;

    public void insertNode(K key, V value) {
        // if key is present, update the value in the HM
        // call the usedNode of NodeAccessPattern to put it to
        // the end of Doubly Linked List

        // if absent, create a new Node, with Key and Value
        // check current size, if == capacity,
        // call evict from DLL, remove the returned key from the HM

        // Insert the new node in the HM,
        // and LL by calling insert
    }

    public V get(K key) {
        // if key is present
        // call the usedNode of NodeAccessPattern to put it to
        // the end of Doubly Linked List
        // return node.value

        // else return null
        return null;
    }

    public void remove(K key) {
        // if key absent from HM
        // return;

        // else
        // remove from HM
        // call removeNode of DLL
    }

}

class NodeAccessPattern<K,V> {
    private Node<K,V> head;
    private Node<K,V> tail;

    public void insertNode(Node<K,V> node) {
        // insert at the tail
    }

    public void usedNode(Node<K,V> node) {
        // call remove and then insert 
    }

    public Node<K,V> removeNode(Node<K,V> node) {
        // update pointers and head/tail if needed
        return null;
    }

    public K evict() {
        // remove the head and return the key
        // call remove function above to remove the head
        return null;
    }
}

class Node<K,V> {
    K key;
    V value;
    Node<K,V> next;
    Node<K,V> prev;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
        next = null;
        prev = null;
    }
}