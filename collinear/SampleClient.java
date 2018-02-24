import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class SampleClient
{
  public static void main(String[] args) {
    
    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }
    
    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();
    
    boolean useBruteOrNot = false;
    
    if(useBruteOrNot)
    {
      // print and draw the line segments
      BruteCollinearPoints bfCollinear = new BruteCollinearPoints(points);
      StdOut.println(bfCollinear.numberOfSegments());
      for (LineSegment segment : bfCollinear.segments()) {
        StdOut.println(segment);
        segment.draw();
      }
      StdDraw.show();
    }
    else
    {
      // print and draw the line segments
      FastCollinearPoints collinear = new FastCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
      }
      StdDraw.show();
      
    }
  }
}
