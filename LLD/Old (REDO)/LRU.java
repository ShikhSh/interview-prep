import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


/**
 * Design Cache with LRU eviction policy.
 * * GPT suggestions:
 * Suggestions for Improvement
Naming Consistency:
CacheManager is the main cache, but LRUCacheImpl is empty. Consider merging or clarifying their roles.
Typically, the main cache class is called LRUCache.

Encapsulation:
Make fields like head, tail, and keyMappings private and provide only necessary access.

Thread Safety:

This design does not mention thread safety. In a multithreaded environment (e.g., when multiple threads 
are accessing or modifying the cache), you'd need to introduce some synchronization mechanism
or use concurrent data structures (like ConcurrentHashMap). This would ensure that the cache behaves correctly under concurrent access.
!!!! Use synchronized blocks or locks to ensure thread safety.
1. we can use synchronized blocks around critical sections of code that modify the cache.
2. Alternatively, we can use ReentrantLock read write locks for finer control over locking.

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

/**
 * 
 * FINAL DESIGN:
 * 1. Cache interface with put, get, remove methods.
 * 2. LRUCacheImpl class implementing Cache interface. - If we want LFU, we can implement class using Cache interface.
 * 3. REMOVE - CacheManager class managing the cache operations. - NOT NEEDED
 * 4. NodeAccessPattern class for managing the doubly linked list.
 * 5. Node class representing each entry in the cache.
 */


interface Cache<K,V> {
    public void put(K key, V value);
    V remove (K key);
    V get(K key);
}

/**
 * Just contains the implementation of the Cache interface.
 * It uses CacheManager to perform the operations.
 * Does not contain any data structures or how to manage them.
 */
class LRUCacheImpl<K,V> implements Cache<K,V> {
    private ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private ReadLock rdLock = reentrantLock.readLock();
    private WriteLock wrLock = reentrantLock.writeLock();

    @Override
    public void put(K key, V value) {
        // calls insertNode of CacheManager
    }

    @Override
    public V get(K key) {
        // calls get of CacheManager
        return null;
    }
    @Override 
    public V remove(K key) {
        // calls remove of CacheManager
        return null;
    }
}

//////////////////////////////////// REMOVE CacheManager ////////////////////////////////////
/// Incorporate its functionality into LRUCacheImpl directly, as it is not needed.
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