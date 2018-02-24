import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private static int OPEN = 1;
    private static int CLOSED = 0;  
    
    private int virtualSiteTop = 0;
    private int virtualSiteBottom = 0;
    
    private int N;
    private int[] id;
    
    private WeightedQuickUnionUF wu;
    
    // create n-by-n grid, with all sites blocked
    //Repeat the following until the system percolates: 
    // - Choose a site uniformly at random among all blocked sites
    // - Open it
    public Percolation(int n)
    {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        
        N = n;    
        id = new int[N*N +2];
        for(int i =1; i < N*N +1; i++)
        {
            id[i] = CLOSED;      
        }
        virtualSiteBottom = N*N + 1;
        id[virtualSiteBottom] = OPEN;
        
        wu = new WeightedQuickUnionUF(N*N+2);    
    }
    
    
    //connected
    //count
    //find
    //union
//    1 2 3 
//    4 5 6
//    7 8 9
    
//  1 2
//  3 4
    
    
    
    private int xyTo1D(int x, int y)
    {
        return (y-1)*N + x; 
    }
    private void checkArgument(int i)
    {
        if(i <= 0 || i > N*N+1) 
            throw new java.lang.IllegalArgumentException();    
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col)
    {
        int index = xyTo1D(row, col);
        checkArgument(index);
        if (id[index] == OPEN)
            return;
        id[index] = OPEN;    
        
        int up = xyTo1D(row - 1, col);
        int left = xyTo1D(row, col - 1);
        int right = xyTo1D(row, col + 1);
        int down = xyTo1D(row + 1, col);
        
        if (row == 1)
            wu.union(index, virtualSiteTop);
        if (row == N)
            wu.union(index, virtualSiteBottom);
        
        if (row != 1 && id[up] == OPEN)
            wu.union(index, up);
        if (col != 1 && id[left] == OPEN)
            wu.union(index, left);
        if (row != N && id[down] == OPEN)
            wu.union(index, down);    
        if (col != N && id[right] == OPEN)
            wu.union(index, right);  
    }
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = xyTo1D(row, col);
        checkArgument(index);
        return id[index] == OPEN;
    }
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        checkArgument(index);
        return wu.connected(index, virtualSiteTop);
    }  
    
    // number of open sites
    public int numberOfOpenSites() {
        int returnValue = 0;
        for(int i = 1; i < N*N+1; i++) {
            if(id[i] == OPEN) returnValue++;
        }
        return returnValue;
    }       
    
    // does the system percolate?
    public boolean percolates() {
        return wu.connected(virtualSiteTop, virtualSiteBottom);
    }              
    
    
    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]); 
        Percolation perc = new Percolation(n); 
        perc.open(1, 1);
        
        
        System.out.println(perc.numberOfOpenSites());
        
    }   
}



