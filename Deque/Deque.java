import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {  
    private DoublyLinkedList myLinkedList;
    
    public Deque() {
        myLinkedList = new DoublyLinkedList();
    }             // construct an empty deque
    
    public boolean isEmpty() {   
        return myLinkedList.count() == 0;
    }          // is the deque empty?
    
    public int size() {
        return myLinkedList.count();
    }      // return the number of items on the deque
    
    public void addFirst(Item item) { 
        if(item == null)
            throw new java.lang.IllegalArgumentException();
        myLinkedList.addFirst(item);
    }     // add the item to the front
    
    public void addLast(Item item) {
        if(item == null)
            throw new java.lang.IllegalArgumentException();
        myLinkedList.addLast(item);
    }    // add the item to the end
    
    public Item removeFirst() {
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        return myLinkedList.removeFirst();
    }           // remove and return the item from the front
    
    public Item removeLast() {
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        return myLinkedList.removeLast();
        
    }         // remove and return the item from the end
    
    public Iterator<Item> iterator() { return new ListIterator(); }
    
    private class ListIterator implements Iterator<Item> {
        private Node current;
        
        public ListIterator() {
            current = myLinkedList.first;
        }
        
        public boolean hasNext() {  return current != null;  }
        
        public void remove() {  
            throw new java.lang.UnsupportedOperationException();
        }      
        
        public Item next() {
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current   = current.next; 
            return item;
        }
    }
    
    private class Node {
        public Item item;
        public Node next;
        public Node previous;
        public Node(Item i, Node n, Node p) {
            item = i;
            next = n;
            previous = p;
        }
    }
    
    private class DoublyLinkedList {
        int n;
        Node first;
        Node last;
        
        public DoublyLinkedList() {
            n = 0;
        }
        
        public int count() {
            return n;
        }
        
        public void addFirst(Item item) {
            if(first == null) {
                first = new Node(item, null, null);
                last = first;
            }
            else {
                Node toAdd = new Node(item, first, null);
                
                toAdd.next = first;
                first.previous = toAdd;
                first = toAdd; 
            }
            n++;
        }
        
        public void addLast(Item item) {
            if(last == null) {
                first = new Node(item, null, null);
                last = first;
            }
            else {
                Node toAdd = new Node(item, null, last);
                last.next = toAdd;
                toAdd.previous = last;
                last = toAdd; 
            }
            n++;
        }
        
        public Item removeFirst() {      
            Node temp = first;
            if(n == 1) {
                first = null;
                last = null;
            }
            else {
                
                first = first.next;
                first.previous = null;
            }
            n--;
            return temp.item;
        }           // remove and return the item from the front
        
        public Item removeLast() {
            Node temp = last;
            if(n == 1) {
                first = null;
                last = null;
            }
            else {
                last = last.previous;
                last.next = null;
            }
            n--;
            return temp.item;      
        }          
    }
    
    public static void main(String[] args)  {
    } // unit testing (optional)
}