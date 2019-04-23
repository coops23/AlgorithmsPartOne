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
    private List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        segmentCount = 0;
        lineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++)
        {
            Point origin = points[i];
            List<Point> slopeSortedPoints = new ArrayList<Point>();
            for (int y = i + 1; y < points.length; y++) {
                slopeSortedPoints.add(points[y]);
            }

            Collections.sort(slopeSortedPoints, origin.slopeOrder());

            double prevSlope = 0;
            Point prevPoint = origin;
            int count = 0;
            for (Point p : slopeSortedPoints) {
                double slope = origin.slopeTo(p);

                if (prevSlope == slope) {
                    prevSlope = slope;
                    prevPoint = p;
                    count++;
                }
                else {
                    if (count >= 3) {
                        segmentCount++;
                        lineSegments.add(new LineSegment(origin, prevPoint));
                    }

                    prevSlope = slope;
                    count = 1;
                }
            }

            if(count >= 3) {
                segmentCount++;
                lineSegments.add(new LineSegment(origin, prevPoint));
            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return segmentCount;
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
        In in = new In("input6.txt");
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
