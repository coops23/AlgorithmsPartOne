/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private final SET<Point2D> set;

    private class KdTreeSet
    {
        public KdTreeSet()
        {

        }
    }

    public KdTree()                               // construct an empty set of points
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

        if (isEmpty()) return null;

        for (Point2D p : set)
        {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax()) inside.add(p);
        }

        return inside;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (isEmpty()) return null;

        double shortestDistance = 100;
        Point2D shortestDistancePoint = null;
        for (Point2D point : set)
        {
            double distance = point.distanceSquaredTo(p);
            if (distance > shortestDistance)
            {
                shortestDistance = distance;
                shortestDistancePoint = point;
            }
        }

        return shortestDistancePoint;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        //empty
    }
}
