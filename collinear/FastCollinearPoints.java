import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collections;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int count;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)    
    {
        if(points == null)
            throw new java.lang.IllegalArgumentException();
        
        for(int i = 0; i< points.length; i++)
        {
            if(points[i] == null)
                throw new java.lang.IllegalArgumentException();
            for(int j = 0; j< points.length; j++)
            {
                if(points[j] == null)
                    throw new java.lang.IllegalArgumentException();
                if(i != j && points[i].compareTo(points[j])==0)
                    throw new java.lang.IllegalArgumentException();
            }
        } 
        lineSegments = new LineSegment[points.length*points.length];
        count = 0;
        
        //Arrays.sort(points, 0, points.length);    
        //outputPoints(points);       
        
        for (Point origin : points) {
            //Point[] copy = copy(points);
            Point[] copy = new Point[points.length];
            for(int i =0; i< points.length; i++)
            {
                copy[i] = points[i]; 
            }            
            Arrays.sort(copy, origin.slopeOrder());
            
            //outputPoints(points);
            //outputPoints(copy);
            
            int j = 0;
            double previous = -20.0;
            LinkedList<Point> collinear = new LinkedList<Point>();
            for(Point p : copy) {
                if (j == 0 || p.slopeTo(origin) != previous) {
                    if (collinear.size() >= 3) {
                        collinear.add(origin);
                        Collections.sort(collinear);
                        if (origin == collinear.getFirst()) makeSegments(collinear);
                    }
                    collinear.clear();
                }
                collinear.add(p);
                
                previous = p.slopeTo(origin);
                //outputSlopes(p.slopeTo(origin), previous);
                j++;
            }
            if (collinear.size() >= 3) {
                collinear.add(origin);
                Collections.sort(collinear);
                if (origin == collinear.getFirst()) makeSegments(collinear);
            }
        }        
    }
    private void makeSegments(LinkedList<Point> points)
    {
        Collections.sort(points);
        lineSegments[count] = new LineSegment(points.getFirst(), points.getLast());
        count++;
    }
    
    private void outputPoints(Point[] points)
    {
        StdOut.println("Outputting points...");
        for(Point p : points)
        {
            StdOut.println(p.toString()); 
        }
    }
    private void outputSlopes(double a, double b)
    {
        StdOut.println("Outputting slopes..." + a + "    " + b);
//    for(Point p : points)
//    {
//      StdOut.println(p.toString()); 
//    }
    }
    
    // the number of line segments
    public int numberOfSegments()
    {      
        int notNullCount = 0;
        for(int i = 0; i< lineSegments.length; i++)
        {
            if(lineSegments[i] != null)
                notNullCount++;
        }
        return notNullCount;
    }
    
    // the line segments
    public LineSegment[] segments()       
    {     
        int notNullCount = 0;
        for(int i = 0; i< lineSegments.length; i++)
        {
            if(lineSegments[i] != null)
                notNullCount++;
        }
        
        LineSegment[] returnList = new LineSegment[notNullCount];
        notNullCount = 0;
        for(int i = 0; i< lineSegments.length; i++)
        {
            if(lineSegments[i] != null)
                returnList[notNullCount] = lineSegments[i];
            notNullCount++;
        }
        
        return returnList;
    }
}