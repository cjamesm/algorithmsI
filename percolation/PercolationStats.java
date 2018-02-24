import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private int T;
   private double[] pprimeGuesses;
   //public PercolationStats(int n, int trials){}    // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials)
   {
     if(n <= 0 || trials <= 0) 
       throw new java.lang.IllegalArgumentException();
     
     pprimeGuesses = new double[trials];
     T = trials;
     for(int i = 0; i < T; i++)
     {
       Percolation perc = new Percolation(n);
       while(!perc.percolates())
       {      
         int randomRow = StdRandom.uniform(n)+1;
         int randomColumn = StdRandom.uniform(n)+1;
         
         perc.open(randomRow, randomColumn);
       }
       double pprime = (double)perc.numberOfOpenSites()/(double)(n*n);
       pprimeGuesses[i] = pprime;
     }
   }  
     
   public double mean(){
     return StdStats.mean(pprimeGuesses);
   }                          // sample mean of percolation threshold
   public double stddev(){
     return StdStats.stddev(pprimeGuesses);
   }                        // sample standard deviation of percolation threshold
   public double confidenceLo(){
   return mean() - ((double)1.96*stddev())/(double)java.lang.Math.sqrt(T);
   }                  // low  endpoint of 95% confidence interval
   public double confidenceHi()  {
   return mean() + ((double)1.96*stddev())/(double)java.lang.Math.sqrt(T);
   }                // high endpoint of 95% confidence interval

   public static void main(String[] args)        // test client (described below)
   {
    int n = Integer.parseInt(args[0]); 
    int trials = Integer.parseInt(args[1]);
    
    PercolationStats percStats = new PercolationStats(n, trials);
    System.out.println(percStats.mean());
    System.out.println(percStats.stddev());
    System.out.println(percStats.confidenceLo());
    System.out.println(percStats.confidenceHi());
   }
 //perform T independent computationcal
}