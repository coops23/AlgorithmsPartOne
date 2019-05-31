/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        List<Integer> a = new ArrayList<Integer>();
        List<Integer> b = new ArrayList<Integer>();
        a.add(v);
        b.add(w);

        return lengthHelper(a, b);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        List<Integer> a = new ArrayList<Integer>();
        List<Integer> b = new ArrayList<Integer>();
        a.add(v);
        b.add(w);

        return ancestorHelper(a, b);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return lengthHelper(v, w);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return ancestorHelper(v, w);
    }

    private int lengthHelper(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph, w);

        boolean hasAncestor = false;
        int shortestDistance = digraph.V() * 2;
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                int distance = nounASearch.distTo(i) + nounBSearch.distTo(i);
                if (shortestDistance > distance) {
                    shortestDistance = distance;
                    hasAncestor = true;
                }
            }
        }

        if (!hasAncestor) return -1;

        return shortestDistance;
    }

    private int ancestorHelper(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph, w);

        int shortestCommonAncestor = 0;
        boolean hasAncestor = false;
        int shortestDistance = digraph.V() * 2;
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                int distance = nounASearch.distTo(i) + nounBSearch.distTo(i);
                if (shortestDistance > distance) {
                    shortestDistance = distance;
                    shortestCommonAncestor = i;
                    hasAncestor = true;
                }
            }
        }

        if (!hasAncestor) return -1;

        return shortestCommonAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph digraph1 = new Digraph(in);
        SAP sap1 = new SAP(digraph1);
        int v1 = 3;
        int w1 = 11;
        StdOut.println(sap1.length(v1, w1));
        StdOut.println(sap1.ancestor(v1, w1));

        in = new In("digraph2.txt");
        Digraph digraph2 = new Digraph(in);
        SAP sap2 = new SAP(digraph2);
        int v2 = 3;
        int w2 = 11;
        StdOut.println(sap1.length(v2, w2));
        StdOut.println(sap1.ancestor(v2, w2));

        in = new In("digraph3.txt");
        Digraph digraph3 = new Digraph(in);
        SAP sap3 = new SAP(digraph3);
        int v3 = 3;
        int w3 = 11;
        StdOut.println(sap3.length(v3, w3));
        StdOut.println(sap3.ancestor(v3, w3));

        in = new In("digraph4.txt");
        Digraph digraph4 = new Digraph(in);
        in = new In("digraph5.txt");
        Digraph digraph5 = new Digraph(in);
        in = new In("digraph6.txt");
        Digraph digraph6 = new Digraph(in);
        in = new In("digraph9.txt");
        Digraph digraph9 = new Digraph(in);
        in = new In("digraph25.txt");
        Digraph digraph25 = new Digraph(in);
        in = new In("digraph-ambiguous-ancestor.txt");
        Digraph digraph_ambiguous_ancestor = new Digraph(in);
        in = new In("digraph-wordnet.txt");
        Digraph digraph_wordnet = new Digraph(in);

    }
}
