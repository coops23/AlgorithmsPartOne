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

public class KdTree {
    Node root;
    int size;

    private class Node
    {
        private Node left;
        private Node right;
        private Point2D point;
        private boolean isVertical;

        public Node(Node l, Node r, Point2D p, boolean isV)
        {
            left = l;
            right = r;
            point = p;
            isVertical = isV;
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

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (!contains(p))
        {
            if (isEmpty())
            {
                root = new Node(null, null, p, true);
            }
            else {
                insertHelper(root, p);
            }

            size++;
        }
    }

    private void insertHelper(Node root, Point2D p)
    {
        if (root.isVertical)
        {
            if (root.point.x() > p.x())
            {
                if (root.left == null)
                {
                    Node newNode = new Node(null, null, p, false);
                    root.left = newNode;
                }
                else
                {
                    insertHelper(root.left, p);
                }
            }
            else
            {
                if (root.right == null)
                {
                    Node newNode = new Node(null, null, p, false);
                    root.right = newNode;
                }
                else
                {
                    insertHelper(root.right, p);
                }
            }
        }
        else
        {
            if (root.point.y() > p.y())
            {
                if (root.left == null)
                {
                    Node newNode = new Node(null, null, p, true);
                    root.left = newNode;
                }
                else
                {
                    insertHelper(root.left, p);
                }
            }
            else
            {
                if (root.right == null)
                {
                    Node newNode = new Node(null, null, p, true);
                    root.right = newNode;
                }
                else
                {
                    insertHelper(root.right, p);
                }
            }
        }
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return containsHelper(root, p);
    }

    private boolean containsHelper(Node root, Point2D p)
    {
        boolean value = false;
        if (root == null) return false;

        int compare = comparePoint(root, p);
        if(compare < 0)
        {
            value = containsHelper(root.right, p);
        }
        else if(compare > 0)
        {
            value = containsHelper(root.left, p);
        }
        else if (compare == 0)
        {
            value = true;
        }

        return value;
    }

    private int comparePoint(Node node, Point2D p)
    {
        int value = 0;
        if (node.isVertical)
        {
            //compare the x
            if(node.point.x() < p.x()) value = -1;
            if(node.point.x() == p.x()) value = 0;
            if(node.point.x() > p.x()) value = 1;
        }
        else if (!node.isVertical)
        {
            //compare the y
            if(node.point.y() < p.y()) value = -1;
            if(node.point.y() == p.y()) value = 0;
            if(node.point.y() > p.y()) value = 1;
        }

        return value;
    }

    public void draw()                         // draw all points to standard draw
    {
        if (size > 0)
        {
            Point2D p = root.point;

            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(0, 0, 0);
            StdDraw.point(p.x(), p.y());
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(0xFF, 0x00, 0x00);
            StdDraw.line(p.x(), 0, p.x(), 1);

            //Draw left sub-tree
            drawHelper(root, root.left);

            //Draw right sub-tree
            drawHelper(root, root.right);
        }
    }

    private void drawHelper(Node parent, Node node)
    {
        if (node == null) return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(0, 0, 0);
        StdDraw.point(node.point.x(), node.point.y());
        StdDraw.setPenRadius(0.001);

        if (node.isVertical)
        {
            StdDraw.setPenColor(0xFF, 0x00, 0x00);
            int compare = comparePoint(parent, node.point);
            if (compare < 0)
            {
                StdDraw.line(node.point.x(), parent.point.y(), node.point.x(), 1.00);
            }
            else
            {
                StdDraw.line(node.point.x(), 0.00, node.point.x(), parent.point.y());
            }

            //Draw left sub-tree
            drawHelper(node, node.left);

            //Draw right sub-tree
            drawHelper(node, node.right);
        }
        else //!isVertical
        {
            StdDraw.setPenColor(0x00, 0x00, 0xFF);
            int compare = comparePoint(parent, node.point);
            if (compare < 0)
            {
                StdDraw.line(parent.point.x(), node.point.y(), 1.0, node.point.y());
            }
            else
            {
                StdDraw.line(0.0, node.point.y(), parent.point.x(), node.point.y());
            }

            //Draw left sub-tree
            drawHelper(node, node.left);

            //Draw right sub-tree
            drawHelper(node, node.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        return null;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        return null;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
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
