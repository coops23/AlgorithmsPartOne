/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private Node left;
        private Node right;
        private Point2D point;
        private boolean isVertical;
        private RectHV rect;

        public Node(Node l, Node r, Point2D p, boolean isV, RectHV nodeRect) {
            left = l;
            right = r;
            point = p;
            isVertical = isV;
            rect = nodeRect;
        }
    }

    public KdTree()                               // construct an empty set of points
    {
        root = null;
        size = 0;
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return root == null;
    }

    public int size()                         // number of points in the set
    {
        return size;
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (!contains(p)) {
            if (isEmpty()) {
                root = new Node(null, null, p, true, new RectHV(0, 0, 1, 1));
                size = 1;
            }
            else {
                insertHelper(root, p);
            }
        }
    }

    private void insertHelper(Node parent, Point2D p) {
        if (parent.isVertical) {
            if (parent.point.x() > p.x()) {
                if (parent.left == null) {
                    double xmin, xmax, ymin, ymax;

                    xmin = parent.rect.xmin();
                    xmax = parent.point.x();
                    ymin = parent.rect.ymin();
                    ymax = parent.rect.ymax();
                    RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
                    parent.left = new Node(null, null, p, false, rect);
                    size++;
                }
                else {
                    insertHelper(parent.left, p);
                }
            }
            else {
                if (parent.right == null) {
                    double xmin, xmax, ymin, ymax;

                    xmin = parent.point.x();
                    xmax = parent.rect.xmax();
                    ymin = parent.rect.ymin();
                    ymax = parent.rect.ymax();
                    RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
                    parent.right = new Node(null, null, p, false, rect);
                    size++;
                }
                else {
                    insertHelper(parent.right, p);
                }
            }
        }
        else {
            if (parent.point.y() > p.y()) {
                if (parent.left == null) {
                    double xmin, xmax, ymin, ymax;

                    xmin = parent.rect.xmin();
                    xmax = parent.rect.xmax();
                    ymin = parent.rect.ymin();
                    ymax = parent.point.y();
                    RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
                    parent.left = new Node(null, null, p, true, rect);
                    size++;
                }
                else {
                    insertHelper(parent.left, p);
                }
            }
            else {
                if (parent.right == null) {
                    double xmin, xmax, ymin, ymax;

                    xmin = parent.rect.xmin();
                    xmax = parent.rect.xmax();
                    ymin = parent.point.y();
                    ymax = parent.rect.ymax();
                    RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
                    parent.right = new Node(null, null, p, true, rect);
                    size++;
                }
                else {
                    insertHelper(parent.right, p);
                }
            }
        }

    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return containsHelper(root, p);
    }

    private boolean containsHelper(Node root, Point2D p) {
        boolean value = true;
        if (root == null) return false;

        int compare = comparePoint(root, p);
        if (compare < 0) {
            value = containsHelper(root.right, p);
        }
        else if (compare > 0) {
            value = containsHelper(root.left, p);
        }

        return value;
    }

    private int comparePoint(Node node, Point2D p) {
        int value = 0;
        if (node.isVertical) {
            if (node.point.x() < p.x()) value = -1;
            if (node.point.x() == p.x()) value = 0;
            if (node.point.x() > p.x()) value = 1;
        }
        else {
            if (node.point.y() < p.y()) value = -1;
            if (node.point.y() == p.y()) value = 0;
            if (node.point.y() > p.y()) value = 1;
        }

        return value;
    }

    public void draw()                         // draw all points to standard draw
    {
        if (size > 0) {
            Point2D p = root.point;

            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(0, 0, 0);
            StdDraw.point(p.x(), p.y());
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(0xFF, 0x00, 0x00);
            StdDraw.line(p.x(), 0, p.x(), 1);

            drawHelper(root, root.left);
            drawHelper(root, root.right);
        }
    }

    private void drawHelper(Node parent, Node node) {
        if (node == null) return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(0, 0, 0);
        StdDraw.point(node.point.x(), node.point.y());
        StdDraw.setPenRadius(0.001);

        if (node.isVertical) {
            StdDraw.setPenColor(0xFF, 0x00, 0x00);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());

        }
        else {
            StdDraw.setPenColor(0x00, 0x00, 0xFF);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        StdDraw.setPenColor(0x00, 0x00, 0x00);
        StdDraw.setPenRadius(0.0005);
        StdDraw.rectangle(node.rect.xmax(), node.rect.ymax(), node.rect.width(),
                          node.rect.height());
        drawHelper(node, node.left);
        drawHelper(node, node.right);
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (root == null) return null;
        if (rect == null) return null;

        ArrayList<Point2D> points = new ArrayList<Point2D>();
        rangeHelper(root, rect, points);

        return points;
    }

    private void rangeHelper(Node node, RectHV rect, ArrayList<Point2D> points) {
        if (node == null) return;

        if (node.rect.intersects(rect)) {
            if (node.point.x() >= rect.xmin() && node.point.x() <= rect.xmax()
                    && node.point.y() >= rect.ymin() && node.point.y() <= rect.ymax())
                points.add(node.point);

            rangeHelper(node.left, rect, points);
            rangeHelper(node.right, rect, points);
        }
    }

    /*public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (root == null) return null;
        if (p == null) throw new IllegalArgumentException();

        ArrayList<Point2D> nearest = new ArrayList<Point2D>();
        nearest.add(root.point);
        double shortestDistance = nearest.get(0).distanceTo(p);
        nearestHelper(root, p, nearest);

        return nearest.get(0);
    }

    private void nearestHelper(Node node, Point2D p, ArrayList<Point2D> nearest) {
        if (node == null) return;
        double distanceToNodeRect = node.rect.distanceTo(p);
        double shortestDistance = nearest.get(0).distanceTo(p);

        double distance = node.point.distanceTo(p);
        if (distance < shortestDistance) {
            nearest.clear();
            nearest.add(node.point);
        }

        nearestHelper(node.left, p, nearest);
        nearestHelper(node.right, p, nearest);

    }*/


    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (root == null) return null;
        if (p == null) throw new IllegalArgumentException();

        ArrayList<Point2D> nearest = new ArrayList<Point2D>();
        nearest.add(root.point);
        double shortestDistance = nearest.get(0).distanceTo(p);
        nearestHelper(root, p, nearest);

        return nearest.get(0);
    }

    private void nearestHelper(Node node, Point2D p, ArrayList<Point2D> nearest) {
        if (node == null) return;
        double distanceToNodeRect = node.rect.distanceTo(p);
        double shortestDistance = nearest.get(0).distanceTo(p);
        if (shortestDistance > distanceToNodeRect) {
            double distance = node.point.distanceTo(p);
            if (distance < shortestDistance) {
                nearest.clear();
                nearest.add(node.point);
            }
            nearestHelper(node.left, p, nearest);
            nearestHelper(node.right, p, nearest);
        }
    }

    public static void main(
            String[] args)                  // unit testing of the methods (optional)
    {
        KdTree set = new KdTree();

        int n = 10;
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            set.insert(p);
        }

        Point2D compare = new Point2D(0.0, 0.5);
        set.insert(compare);
        StdOut.println(set.contains(compare));

        //StdOut.println(set.nearest(compare).toString());
    }
}
