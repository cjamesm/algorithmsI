import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle this is contained within.
        private Node leftorbottom;        // the left/bottom subtree
        private Node rightortop;        // the right/top subtree
        private boolean vertical;
        public Node(Point2D pt, RectHV re, boolean vert)
        {
            p = pt;
            rect = re;
            vertical = vert;
        }
    }
    
    private RectHV containingRect = new RectHV(0.0, 0.0, 1.0, 1.0);
    private int size = 0;
    private Node rootNode;
    
    public KdTree()        
    {
        
    }// construct an empty set of points 
    public boolean isEmpty() {        
        return size == 0;
    }// is the set empty? 
    public int size()  {
        return size;
    }// number of points in the set     
    
    public void insert(Point2D p)   
    {
        if(p == null)
            throw new java.lang.IllegalArgumentException();    
        if(contains(p))
            return;
        rootNode = insert(rootNode, new Node(p, containingRect, true));
        size++;
        
    }// add the point to the set (if it is not already in the set)
    
    private Node insert(Node currentNode, Node newNode)
    {
        if(currentNode == null)
            return newNode;
        if(currentNode.vertical)
        {
            if(newNode.p.x() < currentNode.p.x())
            {
                currentNode.leftorbottom = insert(currentNode.leftorbottom, new Node(newNode.p, leftOrBottomRect(currentNode, currentNode.p), false));
            }
            else
            {
                currentNode.rightortop = insert(currentNode.rightortop, new Node(newNode.p, rightOrTopRect(currentNode, currentNode.p), false));
            }
        }
        else
        {
            if(newNode.p.y() < currentNode.p.y())
            {
                currentNode.leftorbottom = insert(currentNode.leftorbottom, new Node(newNode.p, leftOrBottomRect(currentNode, currentNode.p), true));
            }
            else
            {
                currentNode.rightortop = insert(currentNode.rightortop, new Node(newNode.p, rightOrTopRect(currentNode, currentNode.p), true));
            }
        }
        return currentNode;
    }
        
    private RectHV rightOrTopRect(Node currentNode, Point2D boundaryPoint)
    {
        RectHV container = currentNode.rect;
        //StdOut.println("(" +node.p.x()+","+node.p.y()+") container:("+container.xmin()+","+container.ymin()+"):("+container.xmax()+","+container.ymax()+")");
        if(currentNode.vertical)
        {
            return new RectHV(boundaryPoint.x(), container.ymin(), container.xmax(), container.ymax());
        }
        else
        {
            return new RectHV(container.xmin(), boundaryPoint.y(), container.xmax(), container.ymax()); 
        }
    }
    
    private RectHV leftOrBottomRect(Node currentNode, Point2D boundaryPoint)
    {
        RectHV container = currentNode.rect;
        //StdOut.println("(" +node.p.x()+","+node.p.y()+") container:("+container.xmin()+","+container.ymin()+"):("+container.xmax()+","+container.ymax()+")");
        if(currentNode.vertical)
        {
            return new RectHV(container.xmin(), container.ymin(), boundaryPoint.x(), container.ymax());
        }
        else
        {
            return new RectHV(container.xmin(), container.ymin(), container.xmax(), boundaryPoint.y()); 
        }
    }
    
    public boolean contains(Point2D p) 
    {
        if(p == null)
            throw new java.lang.IllegalArgumentException();       
        
        if(rootNode == null)
            return false;     
        
        return contains(p, rootNode);
    }// does the set contain point p? 
    
    private boolean contains(Point2D p, Node node)
    {
        if(p.x() == node.p.x() && p.y() == node.p.y())
            return true;
        else if(node.vertical && p.x() < node.p.x() || !node.vertical && p.y() < node.p.y())
        {
            if(node.leftorbottom == null)
                return false;
            else
                return contains(p, node.leftorbottom);
        }
        else 
        {
            if(node.rightortop == null)
                return false;
            else
                return contains(p, node.rightortop);
        }
    }
    
    public void draw() {
        containingRect.draw(); 
        
        draw(rootNode);
        
        
    } // draw all points to standard draw 
        
    private void draw(Node node)
    {
        if(node == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();         
        
        StdDraw.setPenRadius();
        if(node.vertical)
        {
            RectHV line = new RectHV(node.p.x(),node.rect.ymin(),node.p.x(),node.rect.ymax());
            StdDraw.setPenColor(StdDraw.RED);         
            line.draw();      
        }
        else
        {
            RectHV line = new RectHV(node.rect.xmin(),node.p.y(),node.rect.xmax(),node.p.y());
            StdDraw.setPenColor(StdDraw.BLUE);        
            line.draw();
        }        
        draw(node.leftorbottom);
        draw(node.rightortop);
    }
    
    public Iterable<Point2D> range(RectHV rect)   
    {
        if(rect == null)
            throw new java.lang.IllegalArgumentException();
        
        Queue<Point2D> pointQueue = new Queue<Point2D>();
        
        range(rect, rootNode, pointQueue);
        
        return pointQueue;
    }// all points that are inside the rectangle (or on the boundary) 
    
    private void range(RectHV rect, Node node, Queue<Point2D> pointQueue)
    {
        if(node == null)
            return;
        if(rect.contains(node.p))
        {
            pointQueue.enqueue(node.p);
        }
        if(node.leftorbottom != null && rect.intersects(node.leftorbottom.rect))
        {
            range(rect, node.leftorbottom, pointQueue);
        }
        if(node.rightortop != null && rect.intersects(node.rightortop.rect))
        {
            range(rect, node.rightortop, pointQueue);
        }
    }
    
    public Point2D nearest(Point2D p) 
    {
        if(p == null)
            throw new java.lang.IllegalArgumentException();        
        if(rootNode == null)
            return null;
        else 
            return nearest(p, null, rootNode, true);
    }// a nearest neighbor in the set to point p; null if the set is empty 
    
    
    private Point2D nearest(Point2D p, Point2D closestPoint, Node n, boolean leftFirst)
    {
        if(n == null)
            return closestPoint;
        //start at root and recursively search in both subtrees using following pruning rule
        if(closestPoint == null || p.distanceTo(n.p) < p.distanceTo(closestPoint))
            closestPoint = n.p;
        //if closest point so far if closer than the distance btwn query pt and rectangle corresp to node, no need to explore
        if(leftFirst)
        {
            if(n.leftorbottom != null && n.leftorbottom.rect.distanceTo(p) <= n.p.distanceTo(p))
                closestPoint = nearest(p, closestPoint, n.leftorbottom, true);
            if(n.rightortop != null && n.rightortop.rect.distanceTo(p) <= n.p.distanceTo(p))
                closestPoint = nearest(p, closestPoint, n.rightortop, false);
        }
        else
        {
            if(n.rightortop != null && n.rightortop.rect.distanceTo(p) <= n.p.distanceTo(p))
                closestPoint = nearest(p, closestPoint, n.rightortop, false);
            if(n.leftorbottom != null && n.leftorbottom.rect.distanceTo(p) <= n.p.distanceTo(p))
                closestPoint = nearest(p, closestPoint, n.leftorbottom, true);            
        }
        
        return closestPoint;
    }
    
    public static void main(String[] args)     
    {        
        Point2D p1 = new Point2D(0.5,0.5);
        Point2D p2 = new Point2D(0.3,0.7);
        
        KdTree tree = new KdTree();
        tree.insert(p1);
        tree.draw();
        StdDraw.setPenColor(StdDraw.BLUE);  
        p2.draw();
        tree.nearest(p2).draw();
    } // unit testing of the methods (optional) 
}
//
////
////Writing KdTree. Start by writing isEmpty() and size(). These should be very easy. From there, write a simplified 
//version of insert() which does everything except set up the RectHV for 
//    each node. Write the contains() method, and use this to test that insert() was implemented properly. Note that 
//    insert() and contains() are best implemented by using private helper methods analogous to those found on page 
//    399 of the book or by looking at BST.java. We recommend using orientation as an argument to these helper methods.
////
////Now add the code to insert() which sets up the RectHV for each Node. Next, write draw(), and use this to test 
//    these rectangles. Finish up KdTree with the nearest and range methods. Finally, test your implementation
//    using our interactive client programs as well as any other tests you'd like to conduct. 



// In the tests below, we consider three classes of points and rectangles. * Non-degenerate points: no two points
//    (or rectangles) share either an x-coordinate or a y-coordinate * Distinct points: no two points (or rectangles) 
//    share both an x-coordinate and a y-coordinate * General points: no restrictions on the x-coordinates or 
//     y-coordinates of the points (or rectangles) A point in an m-by-m grid means that it is of the form (i/m, j/m), 
//     where i and j are integers between 0 and m (inclusive).
