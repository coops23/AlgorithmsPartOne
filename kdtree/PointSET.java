import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> set;

    public PointSET()                               // construct an empty set of points
    {
        set = new SET<Point2D>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return set.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return set.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (!contains(p)) set.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return set.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D p : set)
        {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        ArrayList<Point2D> inside = new ArrayList<Point2D>();

        if(rect == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        for (Point2D p : set)
        {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax()) inside.add(p);
        }

        return inside;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if(p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        double shortestDistance = 100;
        Point2D shortestDistancePoint = null;
        for (Point2D point : set)
        {
            double distance = point.distanceSquaredTo(p);
            if (distance < shortestDistance)
            {
                shortestDistance = distance;
                shortestDistancePoint = point;
            }
        }

        return shortestDistancePoint;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        PointSET set = new PointSET();

        int n = 16;
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            set.insert(p);
        }

        Point2D compare = new Point2D(0.0, 0.5);
        StdOut.println(set.nearest(compare).toString());
    }

}