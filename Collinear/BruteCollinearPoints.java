/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {
    private int segmentCount;
    private List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {
        lineSegments = new ArrayList<LineSegment>();
        List<Point> pointList = new ArrayList<Point>();
        for (int i = 0; i < points.length; i++) {
            pointList.add(points[i]);
        }
        Collections.sort(pointList);

        for (int p0 = 0; p0 < pointList.size(); p0++) {
            for (int p1 = p0 + 1; p1 < pointList.size(); p1++) {
                for (int p2 = p1 + 1; p2 < pointList.size(); p2++) {
                    for (int p3 = p2 + 1; p3 < pointList.size(); p3++) {
                        Point origin = pointList.get(p0);
                        double pq = origin.slopeTo(pointList.get(p1));
                        double pr = origin.slopeTo(pointList.get(p2));
                        double ps = origin.slopeTo(pointList.get(p3));

                        if ((pq == pr) && (pr == ps)) {
                            lineSegments.add(new LineSegment(origin, pointList.get(p3)));
                            segmentCount++;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        LineSegment[] returnValue = new LineSegment[lineSegments.size()];

        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = lineSegments.get(i);
        }

        return returnValue;
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<Point>();

        points.add(new Point(10, 2560));
        points.add(new Point(10, 1280));
        points.add(new Point(10, 640));
        points.add(new Point(10, 320));
        points.add(new Point(10, 160));
        points.add(new Point(10, 40));
        points.add(new Point(10, 80));
        points.add(new Point(10, 20));
        points.add(new Point(10, 10));
        points.add(new Point(0, 0));
        points.add(new Point(1, 0));
        points.add(new Point(0, 1));

        Point[] returnValue = new Point[points.size()];

        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = points.get(i);
        }

        BruteCollinearPoints brute = new BruteCollinearPoints(returnValue);

        System.out.print(brute.numberOfSegments() + "\n");
        for (LineSegment segment : brute.segments()) {
            System.out.print(segment.toString() + "\n");
        }
    }
}
