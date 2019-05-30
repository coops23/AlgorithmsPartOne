import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNet {
    private final Digraph digraph;
    private final HashMap<Integer, String> synsetTable;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        //check if arguments to constructor are null
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        In in = new In(synsets);
        synsetTable = new HashMap<Integer, String>();
        while (in.hasNextLine()) {
            String[] temp = in.readLine().split(",");
            int id = Integer.parseInt(temp[0]);
            String synset = temp[1];
            synsetTable.put(id, synset);
        }

        in = new In(hypernyms);
        digraph = new Digraph(synsetTable.size());
        while (in.hasNextLine()) {
            String[] data = in.readLine().split(",");
            int v = Integer.parseInt(data[0]);
            for (int i = 1; i < data.length; i++) {
                int w = Integer.parseInt(data[i]);
                digraph.addEdge(v, w);
            }
        }

        //check if there is a single root
        int indegreeIsZero = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) indegreeIsZero++;
        }

        if (indegreeIsZero > 1) throw new IllegalArgumentException();
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
            String item = synsetTable.get(i);
            String[] items = item.split(" ");
            for (String subItem : items) {
                if (subItem.equals(word)) return true;
            }
        }

        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        //check if any noun argument in distance() or sap() is not a WordNet noun
        if (!isNoun(nounA) || !isNoun(nounB) || nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        List<Integer> a = new ArrayList<Integer>();
        List<Integer> b = new ArrayList<Integer>();

        for (int i = 0; i < synsetTable.size(); i++) {
            for (String noun : synsetTable.get(i).split(" ")) {
                if (noun.equals(nounA)) a.add(i);
                if (noun.equals(nounB)) b.add(i);
            }
        }

        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph, a);
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph, b);

        int shortestCommonAncestor = 0;
        int shortestDistance = digraph.V() * 2;
        boolean valid = false;
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                int distance = nounASearch.distTo(i) + nounBSearch.distTo(i);
                if (shortestDistance > distance) shortestDistance = distance;
                valid = true;
            }
        }

        if (!valid) throw new ArithmeticException();

        return shortestDistance;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        //check if any noun argument in distance() or sap() is not a WordNet noun
        if (!isNoun(nounA) || !isNoun(nounB) || nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        List<Integer> a = new ArrayList<Integer>();
        List<Integer> b = new ArrayList<Integer>();

        for (int i = 0; i < synsetTable.size(); i++) {
            String entry = synsetTable.get(i);
            if (entry.contains(nounA)) a.add(i);
            if (entry.contains(nounB)) b.add(i);
        }

        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph, a);
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph, b);

        int shortestCommonAncestor = 0;
        int shortestDistance = digraph.V() * 2;
        boolean valid = false;
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                int distance = nounASearch.distTo(i) + nounBSearch.distTo(i);
                if (shortestDistance > distance) {
                    shortestDistance = distance;
                    shortestCommonAncestor = i;
                }
                valid = true;
            }
        }

        if (!valid) throw new ArithmeticException();

        return synsetTable.get(shortestCommonAncestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");

        String nounA = "genus_Parrotiopsis";
        String nounB = "ball-breaker";
        StdOut.println(wordNet.distance(nounA, nounB));
        StdOut.println(wordNet.sap(nounA, nounB));

        String word = "genus_Parrotiopsis";
        StdOut.println(wordNet.isNoun(word));
    }
}
