import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        if (wordnet == null) throw new IllegalArgumentException();

        wordNet = wordnet;
    }

    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        if (nouns == null) throw new IllegalArgumentException();
        String outcast = "";
        int dt = 0;
        for (int i = 0; i < nouns.length; i++)
        {
            int di = 0;
            for (int y = 0; y < nouns.length; y++)
            {
                if (y != i)
                {
                    di += wordNet.distance(nouns[i], nouns[y]);
                }
            }

            if (di > dt)
            {
                dt = di;
                outcast = nouns[i];
            }
        }

        return outcast;
    }

    public static void main(String[] args)  // see test client below
    {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String[] argss = {"outcast2.txt", "outcast3.txt","outcast4.txt","outcast5.txt","outcast7.txt","outcast8a.txt",
                          "outcast8b.txt", "outcast8c.txt","outcast9.txt","outcast9a.txt","outcast10.txt","outcast10a.txt","outcast11.txt",
                          "outcast12.txt", "outcast12a.txt","outcast17.txt","outcast20.txt","outcast29.txt"};

        for (int t = 0; t < argss.length; t++) {
            In in = new In(argss[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(argss[t] + ": " + outcast.outcast(nouns));
        }
    }
}