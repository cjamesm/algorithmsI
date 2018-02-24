import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)   
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
        
        startItUp(points);
        
    }
    
    private void startItUp(Point[] points)
    {
        int count = 0;
        Arrays.sort(points, 0, points.length);
        for(int i = 0; i< points.length; i++)
        {
            //Check if null
            if(points[i] == null)
                throw new java.lang.IllegalArgumentException();
            
            for(int j = i+1; j< points.length; j++)
            {
                if(i != j && points[i] == points[j])
                    throw new java.lang.IllegalArgumentException();
                
                for(int k = j+1; k < points.length; k++)
                {
                    double slopeOne = points[j].slopeTo(points[i]);
                    if( slopeOne != points[k].slopeTo(points[i]))
                        continue;
                    for (int l = k+1; l< points.length; l++)
                    {
                        if( slopeOne == points[k].slopeTo(points[i]) && slopeOne ==
                           points[l].slopeTo(points[i]))
                        {
                            Point start = getStartPoint(points[i],points[j],points[k],points[l]);
                            Point end = getEndPoint(points[i],points[j],points[k],points[l]);
                            lineSegments[count] = new LineSegment(start, end);
                            count++;
                        }
                    }
                }
            }
            
        }
    }
    private Point getStartPoint(Point a, Point b, Point c, Point d)
    {
        return a;
//      if(a.compareTo(b) && a.compareTo(c) && a.compareTo( d))
//        return a;
//      if(b.compareTo(a) && b.compareTo(c) && b.compareTo(d))
//        return b;
//      if(c.compareTo(b) && c.compareTo(a) && c < d)
//        return c;
//      if(d < b && d < c && d < a)
//        return d;      
    }
    private Point getEndPoint(Point a, Point b, Point c, Point d)
    {
        return d;
//      if(a > b && a > c && a > d)
//        return a;
//      if(b > a && b > c && b > d)
//        return b;
//      if(c > b && c > a && c > d)
//        return c;
//      if(d > b && d > c && d > a)
//        return d;
        
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