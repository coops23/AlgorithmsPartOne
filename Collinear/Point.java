/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double rise = this.y - that.y;
        double run = this.x - that.x;

        if (rise == 0 && run == 0) { /* at the same spot */
            return Double.NEGATIVE_INFINITY;
        }

        if (rise == 0) { /* horizontal */
            return 0.0;
        }

        if (run == 0) { /* verticle */
            return Double.POSITIVE_INFINITY;
        }

        return (rise / run);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {
        if (this.y > that.y) {
            return 1;
        }
        else if (this.y == that.y) {
            if (this.x > that.x) {
                return 1;
            }
            else if (this.x == that.x) {
                return 0;
            }
        }

        return -1;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeComparitor(this.x, this.y);
    }

    private class SlopeComparitor implements Comparator<Point> {
        private final int x;     // x-coordinate of this point
        private final int y;     // y-coordinate of this point

        SlopeComparitor(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compare(Point o1, Point o2) {
            double slope0 = slopeTo(o1);
            double slope1 = slopeTo(o2);

            if (slope0 == slope1) {
                return 0;
            }
            else if (slope0 > slope1) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }

    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        List<Point> points = new ArrayList<Point>();

        points.add(new Point(9000, 9000));
        points.add(new Point(8000, 8000));
        points.add(new Point(5000, 5000));
        points.add(new Point(4000, 4000));
        points.add(new Point(3000, 3000));
        points.add(new Point(2000, 2000));
        points.add(new Point(1000, 1000));
        points.add(new Point(7000, 7000));
        points.add(new Point(6000, 6000));

        Collections.sort(points);

        System.out.println("Printing points from least to greatest");
        for (Point p : points) {
            System.out.print(p.toString() + "\n");
        }
        System.out.println("");

        Point origin = new Point(5000, 5000);
        Collections.sort(points, origin.slopeOrder());

        System.out.println("Printing slopes from origin from least to greatest");
        for (Point p : points) {
            System.out.print(p.toString() + "\n");
        }
    }
}
