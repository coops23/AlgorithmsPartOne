import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private final Digraph digraph;
    private final HashMap<Integer, String> synsetTable;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        //check if arguments to constructor are null
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        //check if there is a single root

        //check if any noun argument in distance() or sap() is not a WordNet noun

        In in = new In(synsets);
        synsetTable = new HashMap<Integer, String>();
        int count = 0;
        while (in.hasNextLine()) {
            String[] temp = in.readLine().split(",");
            int id = Integer.parseInt(temp[0]);
            String synset = temp[1];
            synsetTable.put(id, synset);
            count++;
        }


        in = new In(hypernyms);
        HashMap<Integer, Integer> hypernymTable = new HashMap<Integer, Integer>();
        while (in.hasNextLine()) {
            String[] data = in.readLine().split(",");
            int id = Integer.parseInt(data[0]);
            for (int i = 1; i < data.length; i++) {
                int hypernym = Integer.parseInt(data[i]);
                hypernymTable.put(id, hypernym);
            }
        }

        digraph = new Digraph(hypernymTable.size() + 1);
        for (int key : hypernymTable.keySet()) {
            int hypernym = hypernymTable.get(key);
            digraph.addEdge(key, hypernym);
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        ArrayList<String> nouns = new ArrayList<String>();

        for (int i = 0; i < digraph.V(); i++) {
            Iterable<Integer> it = digraph.adj(0);
            String item = synsetTable.get(i);
            String[] items = item.split(" ");
            for (String subItem : items) {
                nouns.add(subItem);
            }

        }

        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        for (int i = 0; i < synsetTable.size(); i++) {
            if (synsetTable.get(i).contains(word)) {
                return true;
            }
        }

        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int a = 0;
        int b = 0;
        boolean a_set = false;
        boolean b_set = false;

        for (int i = 0; i < synsetTable.size(); i++) {
            if (a_set && b_set) break;

            if (synsetTable.get(i).contains(nounA)) {
                if (!a_set) {
                    a = i;
                    a_set = true;
                }
            }
            if (synsetTable.get(i).contains(nounB)) {
                if (!b_set) {
                    b = i;
                    b_set = true;
                }
            }
        }

        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph, a);
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph, b);

        int shortestCommonAncestor = 0;
        int shortestDistanceA = digraph.V();
        int shortestDistanceB = digraph.V();
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                int distanceA = nounASearch.distTo(i);
                int distanceB = nounBSearch.distTo(i);

                if (shortestDistanceA > distanceA) shortestDistanceA = distanceA;
                if (shortestDistanceB > distanceB) shortestDistanceB = distanceB;
            }
        }

        return shortestDistanceA + shortestDistanceB;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int a = 0;
        int b = 0;

        for (int i = 0; i < synsetTable.size(); i++) {
            if (synsetTable.get(i).contains(nounA)) {
                a = i;
            }
            if (synsetTable.get(i).contains(nounB)) {
                b = i;
            }
        }

        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph, a);
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph, b);

        int shortestCommonAncestor = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                shortestCommonAncestor = i;
            }
        }

        return synsetTable.get(shortestCommonAncestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");

        String nounA = "quadrangle";
        String nounB = "mountain_devil";
        StdOut.println(wordNet.distance(nounA, nounB));
        StdOut.println(wordNet.sap(nounA, nounB));
    }
}
