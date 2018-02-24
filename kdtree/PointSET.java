import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> pointSet;
    
    public PointSET()
    {
        pointSet = new SET<Point2D>();
        
    }// construct an empty set of points 
    
    public boolean isEmpty() {   
        return pointSet.isEmpty();
    }// is the set empty? 
    
    public int size()  {       
        return pointSet.size();
    }// number of points in the set 
    
    public void insert(Point2D p)   {
        if(p == null)
            throw new java.lang.IllegalArgumentException();
        if(!contains(p))
            pointSet.add(p);   
    }// add the point to the set (if it is not already in the set)
    
    public boolean contains(Point2D p) {
        if(p == null)
            throw new java.lang.IllegalArgumentException();       
        return pointSet.contains(p);
    }// does the set contain point p? 
    
    public void draw() {   
        for(Point2D point : pointSet)
        {
            point.draw();
        }
    }// draw all points to standard draw 
    
    public Iterable<Point2D> range(RectHV rect)   {
        if(rect == null)
            throw new java.lang.IllegalArgumentException();
        Stack<Point2D> pointStack = new Stack<Point2D>();
        
        for(Point2D point : pointSet)
        { 
            if(rect.contains(point))
                pointStack.push(point);           
        }     
        
        return pointStack;
    }// all points that are inside the rectangle (or on the boundary) 
    
    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new java.lang.IllegalArgumentException();
        if(isEmpty())
            return null;
        Point2D nearestPoint = null;
        for(Point2D point : pointSet)
        { 
            if(nearestPoint == null)
                nearestPoint = point;
            else if(p.distanceTo(point) < p.distanceTo(nearestPoint))
                nearestPoint = point;
        }     
        return nearestPoint;
    }// a nearest neighbor in the set to point p; null if the set is empty 
    
    public static void main(String[] args)     {}             // unit testing of the methods (optional) 
}


//Corner cases.  Throw a java.lang.IllegalArgumentException if any argument is null. Performance requirements.  
//    Your implementation should support insert() and contains() in time proportional to the logarithm of the number of
//    points in the set in the worst case; it should support nearest() and range() in time proportional to the number 
//        of points in the set.
//

//public class Point2D implements Comparable<Point2D> {
//   public Point2D(double x, double y)              // construct the point (x, y)
//   public  double x()                              // x-coordinate 
//   public  double y()                              // y-coordinate 
//   public  double distanceTo(Point2D that)         // Euclidean distance between two points 
//   public  double distanceSquaredTo(Point2D that)  // square of Euclidean distance between two points 
//   public     int compareTo(Point2D that)          // for use in an ordered symbol table 
//   public boolean equals(Object that)              // does this point equal that object? 
//   public    void draw()                           // draw to standard draw 
//   public  String toString()                       // string representation 
//}
//public class RectHV {
//   public    RectHV(double xmin, double ymin,      // construct the rectangle [xmin, xmax] x [ymin, ymax] 
//                    double xmax, double ymax)      // throw a java.lang.IllegalArgumentException if (xmin > xmax) or (ymin > ymax)
//   public  double xmin()                           // minimum x-coordinate of rectangle 
//   public  double ymin()                           // minimum y-coordinate of rectangle 
//   public  double xmax()                           // maximum x-coordinate of rectangle 
//   public  double ymax()                           // maximum y-coordinate of rectangle 
//   public boolean contains(Point2D p)              // does this rectangle contain the point p (either inside or on boundary)? 
//   public boolean intersects(RectHV that)          // does this rectangle intersect that rectangle (at one or more points)? 
//   public  double distanceTo(Point2D p)            // Euclidean distance from point p to closest point in rectangle 
//   public  double distanceSquaredTo(Point2D p)     // square of Euclidean distance from point p to closest point in rectangle 
//   public boolean equals(Object that)              // does this rectangle equal that object? 
//   public    void draw()                           // draw to standard draw 
//   public  String toString()                       // string representation 
//}

//void  add(Key key)
//Adds the key to this set (if it is not already present).

//Key  ceiling(Key key)
//Returns the smallest key in this set greater than or equal to key.

//boolean  contains(Key key)
//Returns true if this set contains the given key.

//void  delete(Key key)
//Removes the specified key from this set (if the set contains the specified key).

//boolean  equals(Object other)
//Compares this set to the specified set.

//Key  floor(Key key)
//Returns the largest key in this set less than or equal to key.

//int  hashCode()
//This operation is not supported because sets are mutable.

//SET<Key>  intersects(SET<Key> that)
//Returns the intersection of this set and that set.

//boolean  isEmpty()
//Returns true if this set is empty.

//Iterator<Key>  iterator()
//Returns all of the keys in this set, as an iterator.

//static void  main(String[] args)
//Unit tests the SET data type.

//Key  max()
//Returns the largest key in this set.

//Key  min()
//Returns the smallest key in this set.

//int  size()
//Returns the number of keys in this set.

//String  toString()
//Returns a string representation of this set.

//SET<Key>  union(SET<Key> that)
//Returns the union of this set and that set.
