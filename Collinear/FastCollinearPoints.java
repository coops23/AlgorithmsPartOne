/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private int segmentCount;
    private List<Point> pointsList;
    private List<LineSegment> lineSegments;

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
    {
        segmentCount = 0;
        lineSegments = new ArrayList<LineSegment>();
        pointsList = new ArrayList<Point>();
        for (int i = 0; i < points.length; i++) {
            pointsList.add(points[i]);
        }
        Collections.sort(pointsList);

        for (int i = 0; i < pointsList.size(); i++) {
            Point origin = points[i];
            List<Point> slopeSortedPoints = new ArrayList<Point>();
            for (int y = 0; y < i; y++) {
                slopeSortedPoints.add(points[y]);
            }
            for (int y = i + 1; y < points.length; y++) {
                slopeSortedPoints.add(points[y]);
            }

            Collections.sort(slopeSortedPoints, origin.slopeOrder());

            double prevSlope = 0;
            int count = 0;
            List<Point> collinearPoints = new ArrayList<Point>();
            for (Point p : slopeSortedPoints) {
                double slope = origin.slopeTo(p);

                if (prevSlope == slope) {
                    prevSlope = slope;
                    count++;
                    collinearPoints.add(p);
                }
                else {
                    if (count >= 3) {
                        Point endPoint = collinearPoints.get(collinearPoints.size() - 1);
                        if (origin.compareTo(endPoint) >= 0) {
                            lineSegments.add(new LineSegment(origin, endPoint));
                        }
                        else {
                            lineSegments.add(new LineSegment(endPoint, origin));
                        }

                        collinearPoints.clear();
                    }

                    prevSlope = slope;
                    count = 1;
                }
            }

            if (count >= 3) {
                Point endPoint = collinearPoints.get(collinearPoints.size() - 1);
                if (origin.compareTo(endPoint) >= 0) {
                    lineSegments.add(new LineSegment(origin, endPoint));
                }
                else {
                    lineSegments.add(new LineSegment(endPoint, origin));
                }
            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return lineSegments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] returnValue = new LineSegment[lineSegments.size()];

        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = lineSegments.get(i);
        }

        return returnValue;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("input8.txt");
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

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
