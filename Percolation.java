import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    
    private static int openSites;    
    private WeightedQuickUnionUF a;
    private WeightedQuickUnionUF b;
    private int size;
    private boolean[][] matrix;

    
    // Creates an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        
        if (N < 0)
            throw new IllegalArgumentException("Contstuctor:" 
            + "  Size must be larger than 0.");
        
        a = new WeightedQuickUnionUF(N*N+2);
        b = new WeightedQuickUnionUF(N*N+1);
        
        size = N;
        matrix = new boolean[N][N];
        
        for (int k = 1; k <= N; k++) { 
            a.union(encode(0, k), 0);
            b.union(encode(0, k), 0);
        }
            
        for (int l = 1; l <= N; l++)
            a.union(N*N+1, encode(N-1, l));
            
       /* for (int x = 0; x < matrix.length; x++)
            matrix[x] = false;
        */

    }

    // Opens site (i, j) if it is not open already.
    public void open(int i, int j) {
        
        if (i < 0 || j < 0)
            throw new IndexOutOfBoundsException("open"
            + "   Refrencing an index < 0.");
      
      matrix[i][j] = true;
      openSites++;
      
      if (i > 0) {
          if (matrix[i-1][j]) {
              a.union(encode(i, j+1), encode(i-1, j+1));
              b.union(encode(i, j+1), encode(i-1, j+1));
        }
    }
      if (i < size-1) {
          if (matrix[i+1][j]) {
              a.union(encode(i, j+1), encode(i+1, j+1));
              b.union(encode(i, j+1), encode(i+1, j+1));
        }
    }
      if (j > 0) {
          if (matrix[i][j-1]) {
              a.union(encode(i, j+1), encode(i, j));
              b.union(encode(i, j+1), encode(i, j));
        }
    }
      if (j < size-1) {
          if (matrix[i][j+1]) {
          a.union(encode(i, j+1), encode(i, j+2));
          b.union(encode(i, j+1), encode(i, j+2));
        }
    }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        
        if (i < 0 || j < 0)
            throw new IndexOutOfBoundsException("isOpen"
            + "   Refrencing an index < 0.");
            
        return matrix[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {     
            
        if (i < 0 || j < 0) throw new IndexOutOfBoundsException("isFull"
        + "   Refrencing an index < 0.");
            
        if (!isOpen(i, j)) return false;
            
       return b.connected(0, encode(i, j+1));    
    }

    // Returns number of open sites.
    public int numberOfOpenSites() { return openSites; }

    // Returns true if the system percolates, and false otherwise.
    public boolean percolates() {
        
        if (openSites == 0)
            return false;
        
        return a.connected(0, size*size+1);
    }

    // Returns an integer ID (1...N) for site (i, j).
    private int encode(int i, int j) { return i*size+j; }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation perc = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }            
    }
}

