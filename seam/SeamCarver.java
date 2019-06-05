import edu.princeton.cs.algs4.AcyclicSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private double[][] energyData;

    public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
    {
        if (picture == null) throw new IllegalArgumentException();

        this.picture = new Picture(picture);
        energyData = new double[width()][height()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                energyData[col][row] = computeEnergy(col, row, width(), height());
            }
        }
    }

    public Picture picture()                          // current picture
    {
        return picture;
    }

    public int width()                            // width of current picture
    {
        return picture.width();
    }

    public int height()                           // height of current picture
    {
        return picture.height();
    }

    public double energy(int x, int y)               // energy of pixel at column x and row y
    {
        if (x < 0 || y < 0 || x >= picture.width() || y >= picture.height()) throw new IllegalArgumentException();

        return energyData[x][y];
    }

    public int[] findHorizontalSeam()               // sequence of indices for horizontal seam
    {
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(width() * height() + 2);

        for (int col = width() - 1; col >= 0; col--)
        {
            for (int row = height() - 1; row >= 0; row--)
            {
                int v = TwoDToOneD(col, row);

                //top right
                if (row > 0 && col < width() - 1)
                {
                    int wru = TwoDToOneD(col + 1, row - 1);
                    double energy = energyData[col + 1][row - 1];
                    DirectedEdge edge = new DirectedEdge(v, wru, energy);
                    graph.addEdge(edge);
                }

                //right
                if (col < width() - 1)
                {
                    int wr = TwoDToOneD(col + 1, row );
                    double energy = energyData[col + 1][row];
                    DirectedEdge edge = new DirectedEdge(v, wr, energy);
                    graph.addEdge(edge);
                }

                //bottom right
                if (row < height() - 1 && col < width() - 1)
                {
                    int wrl = TwoDToOneD(col + 1, row + 1);
                    double energy = energyData[col + 1][row + 1];
                    DirectedEdge edge = new DirectedEdge(v, wrl, energy);
                    graph.addEdge(edge);
                }

                if (col == 0)
                {
                    int source = height() * width();
                    DirectedEdge edge = new DirectedEdge(source, v, 0);
                    graph.addEdge(edge);
                }

                if (col == width() - 1)
                {
                    int end = height() * width() + 1;
                    DirectedEdge edge = new DirectedEdge(v, end, 0);
                    graph.addEdge(edge);
                }
            }
        }

        AcyclicSP path = new AcyclicSP(graph,  height() * width());

        int[] seam = new int[width()];
        int i = 0;
        for (DirectedEdge pos : path.pathTo(height() * width() + 1))
        {
            int row = (pos.to() / width());
            int col = pos.to() - (row * width());
            if (i < width())
                seam[i] = row;
            i++;
        }

        return seam;
    }

    public int[] findVerticalSeam()                 // sequence of indices for vertical seam
    {
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(width() * height() + 2);

        for (int row = height() - 1; row >= 0; row--)
        {
            for (int col = width() - 1; col >= 0; col--)
            {
                int v = TwoDToOneD(col, row);

                //bottom left
                if (row < height() - 1 && col > 0)
                {
                    int wbl = TwoDToOneD(col - 1, row + 1);
                    double energy = energyData[col - 1][row + 1];
                    DirectedEdge edge = new DirectedEdge(v, wbl, energy);
                    graph.addEdge(edge);
                }

                //bottom
                if (row < height() - 1)
                {
                    int wb = TwoDToOneD(col, row + 1);
                    double energy = energyData[col][row + 1];
                    DirectedEdge edge = new DirectedEdge(v, wb, energy);
                    graph.addEdge(edge);
                }

                //bottom right
                if (row < height() - 1 && col < width() - 1)
                {
                    int wbr = TwoDToOneD(col + 1, row + 1);
                    double energy = energyData[col + 1][row + 1];
                    DirectedEdge edge = new DirectedEdge(v, wbr, energy);
                    graph.addEdge(edge);
                }

                if (row == 0)
                {
                    int source = height() * width();
                    DirectedEdge edge = new DirectedEdge(source, v, 0);
                    graph.addEdge(edge);
                }

                if (row == height() - 1)
                {
                    int end = height() * width() + 1;
                    DirectedEdge edge = new DirectedEdge(v, end, 0);
                    graph.addEdge(edge);
                }
            }
        }

        AcyclicSP path = new AcyclicSP(graph,  height() * width());

        int[] verticalSeam = new int[height()];
        int i = 0;
        for (DirectedEdge pos : path.pathTo(height() * width() + 1))
        {
            int row = (pos.to() / width());
            int col = pos.to() - (row * width());
            if (i < height())
                verticalSeam[i] = col;
            i++;
        }

        return verticalSeam;
    }

    public void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
    {
        if (seam == null) throw new IllegalArgumentException();
        if (picture.height() <= 1) throw new IllegalArgumentException();

        Picture newPicture = new Picture(width(), height() - 1);
        for (int col = 0; col < width(); col++)
        {
            for (int row = 0; row < height() - 1; row++)
            {
                if (col < width() - 1) {
                    if (Math.abs(seam[col] - seam[col + 1]) > 1)
                        throw new IllegalArgumentException();
                }

                if (row < seam[col])
                {
                    newPicture.set(col, row, picture.get(col, row));
                }
                else if (row >= seam[col])
                {
                    newPicture.set(col, row, picture.get(col, row + 1));
                    energyData[col][row] = energyData[col][row + 1];
                }
            }
        }

        picture = newPicture;
    }

    public void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
    {
        if (seam == null) throw new IllegalArgumentException();
        if (picture.width() <= 1) throw new IllegalArgumentException();

        Picture newPicture = new Picture(width() - 1, height());
        for (int row = 0; row < height(); row++)
        {
            for (int col = 0; col < width() - 1; col++)
            {
                if (row < height() - 1)
                {
                    if (Math.abs(seam[row] - seam[row + 1]) > 1) throw new IllegalArgumentException();
                }

                if (col < seam[row])
                {
                    newPicture.set(col, row, picture.get(col, row));
                }
                else if (col >= seam[row])
                {
                    newPicture.set(col, row, picture.get(col + 1, row));
                    energyData[col][row] = energyData[col + 1][row];
                }

            }
        }

        picture = newPicture;
    }

    private double computeEnergy(int x, int y, int width, int height)
    {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
        {
            return 1000;
        }

        int rgbLeft = picture.getRGB(x - 1, y); //col, row
        int rLeft = (rgbLeft >> 16) & 0xFF;
        int gLeft = (rgbLeft >> 8) & 0xFF;
        int bLeft = (rgbLeft >> 0) & 0xFF;

        int rgbRight = picture.getRGB(x + 1, y); //col, row
        int rRight = (rgbRight >> 16) & 0xFF;
        int gRight = (rgbRight >> 8) & 0xFF;
        int bRight = (rgbRight >> 0) & 0xFF;

        double rxSqrd = Math.pow(rRight - rLeft, 2);
        double gxSqrd = Math.pow(gRight - gLeft, 2);
        double bxSqrd = Math.pow(bRight - bLeft, 2);
        double xGrad = rxSqrd + gxSqrd + bxSqrd;


        int rgbUp = picture.getRGB(x, y - 1); //col, row
        int rUp = (rgbUp >> 16) & 0xFF;
        int gUp = (rgbUp >> 8) & 0xFF;
        int bUp = (rgbUp >> 0) & 0xFF;

        int rgbDown = picture.getRGB(x, y + 1); //col, row
        int rDown = (rgbDown >> 16) & 0xFF;
        int gDown = (rgbDown >> 8) & 0xFF;
        int bDown = (rgbDown >> 0) & 0xFF;

        double rySqrd = Math.pow(rDown - rUp, 2);
        double gySqrd = Math.pow(gDown - gUp, 2);
        double bySqrd = Math.pow(bDown - bUp, 2);
        double yGrad = rySqrd + gySqrd + bySqrd;

        return Math.sqrt(xGrad + yGrad);
    }

    private int TwoDToOneD(int col, int row)
    {
        return (width() * row) + col;
    }
}