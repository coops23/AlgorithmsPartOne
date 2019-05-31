import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

public class WordNet {
    private final Digraph digraph;
    private final ST<String, Bag<Integer>> synsetBag;
    private final ST<Integer, String> synsetRaw;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        //check if arguments to constructor are null
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        In in = new In(synsets);
        synsetBag = new ST<String, Bag<Integer>>();
        synsetRaw = new ST<Integer, String>();
        int count = 0;
        while (in.hasNextLine()) {
            String[] temp = in.readLine().split(",");
            int id = Integer.parseInt(temp[0]);
            synsetRaw.put(id, temp[1]);
            String[] synset = temp[1].split(" ");
            for (String word : synset) {
                if (!synsetBag.contains(word)) {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    synsetBag.put(word, bag);
                }
                else {
                    Bag<Integer> bag = synsetBag.get(word);
                    bag.add(id);
                }
            }
            count++;
        }

        in = new In(hypernyms);
        digraph = new Digraph(count);
        while (in.hasNextLine()) {
            String[] data = in.readLine().split(",");
            int v = Integer.parseInt(data[0]);
            for (int i = 1; i < data.length; i++) {
                int w = Integer.parseInt(data[i]);
                digraph.addEdge(v, w);
            }
        }

        Topological topological = new Topological(digraph);
        if (!topological.hasOrder()) throw new IllegalArgumentException();

        int rootCount = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) rootCount++;
        }

        if (rootCount > 1) throw new IllegalArgumentException();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetBag.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsetBag.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB) || nounA == null || nounB == null) {
            throw new java.lang.IllegalArgumentException();
        }

        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph,
                                                                              synsetBag.get(nounA));
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph,
                                                                              synsetBag.get(nounB));

        int shortestCommonAncestor = 0;
        int shortestDistance = digraph.V() * 2;
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                int distance = nounASearch.distTo(i) + nounBSearch.distTo(i);
                if (shortestDistance > distance) shortestDistance = distance;
            }
        }

        return shortestDistance;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB) || nounA == null || nounB == null) {
            throw new java.lang.IllegalArgumentException();
        }

        BreadthFirstDirectedPaths nounASearch = new BreadthFirstDirectedPaths(digraph,
                                                                              synsetBag.get(nounA));
        BreadthFirstDirectedPaths nounBSearch = new BreadthFirstDirectedPaths(digraph,
                                                                              synsetBag.get(nounB));

        int shortestCommonAncestor = 0;
        int shortestDistance = digraph.V() * 2;
        for (int i = 0; i < digraph.V(); i++) {
            if (nounASearch.hasPathTo(i) && nounBSearch.hasPathTo(i)) {
                int distance = nounASearch.distTo(i) + nounBSearch.distTo(i);
                if (shortestDistance > distance) {
                    shortestDistance = distance;
                    shortestCommonAncestor = i;
                }
            }
        }

        return synsetRaw.get(shortestCommonAncestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets6.txt", "hypernyms6TwoAncestors.txt");
        String nounA = "b";
        String nounB = "d";
        String word = "b";
        StdOut.println("Returns: " + wordNet.distance(nounA, nounB) + " Reference: 2");
        StdOut.println("Returns: " + wordNet.sap(nounA, nounB) + " Reference: d");
        StdOut.println("Returns: " + wordNet.isNoun(word) + " Reference: true");

        WordNet wordNet2 = new WordNet("synsets15.txt", "hypernyms15Tree.txt");
        String nounA2 = "b";
        String nounB2 = "d";
        String word2 = "g";
        StdOut.println("Returns: " + wordNet2.distance(nounA2, nounB2) + " Reference: 2");
        StdOut.println("Returns: " + wordNet2.sap(nounA2, nounB2) + " Reference: d");
        StdOut.println("Returns: " + wordNet2.isNoun(word2) + " Reference: true");

        WordNet wordNet3 = new WordNet("synsets.txt", "hypernyms.txt");
        String nounA3 = "conduction_anaesthesia";
        String nounB3 = "candlestick_tulip";
        String word3 = "conduction_anaesthesia";
        StdOut.println("Returns: " + wordNet3.distance(nounA3, nounB3) + " Reference: 18");
        StdOut.println("Returns: " + wordNet3.sap(nounA3, nounB3) + " Reference: entity");
        StdOut.println("Returns: " + wordNet3.isNoun(word3) + " Reference: true");

        WordNet wordNet4 = new WordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");
        String nounA4 = "b";
        String nounB4 = "d";
        String word4 = "g";
        StdOut.println(wordNet4.distance(nounA4, nounB4));
        StdOut.println(wordNet4.sap(nounA4, nounB4));
        StdOut.println(wordNet4.isNoun(word4));

        WordNet wordNet5 = new WordNet("synsets6.txt", "hypernyms6InvalidTwoRoots.txt");
        String nounA5 = "b";
        String nounB5 = "d";
        String word5 = "g";
        StdOut.println(wordNet5.distance(nounA5, nounB5));
        StdOut.println(wordNet5.sap(nounA5, nounB5));
        StdOut.println(wordNet5.isNoun(word5));
    }
}
