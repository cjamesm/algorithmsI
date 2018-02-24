import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;
    
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }         // construct an empty randomized queue
    
    public boolean isEmpty() {
        return N == 0;
    }         // is the randomized queue empty?
    
    public int size() {
        return N;
    }          // return the number of items on the randomized queue
    
    public void enqueue(Item item) {
        if(item == null)
            throw new java.lang.IllegalArgumentException();
        if (N == s.length) resize(2 * s.length);
        s[N++] = item; 
    }      // add the item
    
    public Item dequeue() {
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        
        int randIndex = pickRandomElementIndex(N);
        Item temp = s[randIndex];
        if(randIndex != N-1) {
            s[randIndex] = s[N-1];
        }
        s[--N] = null;    
        
        if (N > 0 && N == s.length/4) resize(s.length/2);
        return temp;
    }          // remove and return a random item
    
    public Item sample() {
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        
        return s[pickRandomElementIndex(N)];
    }           // return a random item (but do not remove it)
    
    public static void main(String[] args) {
        RandomizedQueue<String> test2 = new RandomizedQueue<String>();
        test2.enqueue("to");
        test2.enqueue("be");
        test2.enqueue("or");
        test2.enqueue("not");
        test2.enqueue("aman");
        test2.enqueue("agra");
        test2.enqueue("cool");
        for(String s: test2) {
            for(String s2: test2) {
                System.out.print(s2 += " ");
            }
            System.out.print(s += " ");
        }        
    } // unit testing (optional)
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }    
    private int pickRandomElementIndex(int num) {
        return StdRandom.uniform(num);
    }
    
    public Iterator<Item> iterator() { return new ArrayIterator(); }
    
    private class ArrayIterator implements Iterator<Item> {
        Item[] copy = (Item[]) new Object[N];

        private int index = N;
        
        public boolean hasNext() {  return index > 0; }
        
        public ArrayIterator() {
            for (int i = 0; i < N; i++) {
                copy[i] = s[i];            
            }
        } 
        
        public void remove() { 
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {  
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            
            int randIndex = pickRandomElementIndex(index);
            Item currentItem = copy[randIndex];
            if(randIndex != index-1)
            {
                copy[randIndex] = copy[index-1];
            }
            copy[index-1] = null;
            index--;
            return currentItem;
        }
    }
}