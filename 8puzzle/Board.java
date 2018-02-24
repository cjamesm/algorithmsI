import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;


public class Board {
    private int[][] tiles;
    private int N;
    
    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = copyOfTiles(blocks);        
    } // construct a board from an n-by-n array of blocks    
    // (where blocks[i][j] = block in row i, column j)    
    // 0 represents the blank sq.
    
    public int dimension()  { return N; }               // board dimension n
    
    public int hamming()   { 
        int hammingDist = 0;
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(tiles[i][j] == 0)
                    continue;
                else if(tiles[i][j] != (turn2DTo1D(i,j)+1))
                    hammingDist++;
            }
        }
        // Hamming priority function. The number of blocks in the wrong position, plus the number of moves made so far 
        // to get to the search node. Intuitively, a search node with a small number of blocks in the wrong position 
        // is close to the goal, and we prefer a search node that have been reached using a small number of moves. 
        
        return hammingDist; 
    }                // number of blocks out of place
        
    private int turn2DTo1D(int x, int y) {
        return x*N + y;
    }
    
    public int manhattan() { 
        int manhattanDist = 0;
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(tiles[i][j] == 0)
                    continue;
                else //if(tiles[i][j] != turn2DTo1D(i,j))
                {
                    //Take off one here as we're zero indexing obv. 
                    //not that it matters but I think i got the i and 
                    //j mixed up. 
                    int content = tiles[i][j] - 1;
                    
                    //what is the y of content's real position?
                    //it's the remainder modulo N
                    int y = content % N; //
                    
                    //what is the j of content's real position?
                    int x = (content - y)/N;                    
                    
                    //StdOut.println("content " + content + "; x " + x + "; y " + y);
                    manhattanDist += (Math.abs(i-x) + Math.abs(j-y));

                }
            }
        }
        // Manhattan priority function. The sum of the Manhattan distances (sum of the vertical and horizontal distance) 
        // from the blocks to their goal positions, plus the number of moves made so far to get to the search node.        
        
        return manhattanDist; 
    }              // sum of Manhattan distances between blocks and goal
    
    public boolean isGoal() { 
        return manhattan() == 0;
    }          // is this board the goal board?
    
    public Board twin() { 
        int[][] newTiles = copyOfTiles(tiles);
        if(newTiles[0][0] != 0 && newTiles[0][1] != 0)
        {
            int x = newTiles[0][1];
            int y = newTiles[0][0];
            newTiles[0][1] = y;
            newTiles[0][0] = x;
        }
        else
        {
            int x = newTiles[1][0];
            int y = newTiles[1][1];
            newTiles[1][0] = y;
            newTiles[1][1] = x;
        }       
        
        return new Board(newTiles); 
    }           // a board that is obtained by exchanging any pair of blocks
    
    public boolean equals(Object other) { 
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        
        Board otherBoard = (Board) other;        
        
        return otherBoard.neighbors() == this.neighbors();
    }     // does this board equal y?
    
    public Iterable<Board> neighbors()  {         
        Stack<Board> BoardStack = new Stack<Board>();
        int zeroX = 0;
        int zeroY = 0;
        
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(tiles[i][j] == 0) 
                {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }        
        if(zeroX != 0)
            BoardStack.push(new Board(exchange(zeroX - 1, zeroY, zeroX, zeroY)));
        if(zeroX != N-1)
            BoardStack.push(new Board(exchange(zeroX + 1, zeroY, zeroX, zeroY)));
        if(zeroY != 0)
            BoardStack.push(new Board(exchange(zeroX, zeroY-1, zeroX, zeroY)));
        if(zeroY != N -1)
            BoardStack.push(new Board(exchange(zeroX, zeroY + 1, zeroX, zeroY)));       
        
        return BoardStack;
    }   // all neighboring boards
        
    private int[][] copyOfTiles(int[][] these) {
        int[][] otherBoard = new int[N][N];
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                otherBoard[i][j] = these[i][j];
            }
        }
        return otherBoard;
    }
    
    public String toString() { 
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }           // string representation of this board (in the output format specified below)
  
    private int[][] exchange(int x1, int y1, int x2, int y2)
    {
        int[][] theseTiles = copyOfTiles(tiles);
        int content1 = theseTiles[x1][y1];
        int content2 = theseTiles[x2][y2];
        
        theseTiles[x2][y2] = content1;
        theseTiles[x1][y1] = content2;
        
        return theseTiles;
    }
    
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {            
            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            
            // solve the slider puzzle
            Board initial = new Board(tiles);
            
            StdOut.println("N for this board is: " + initial.dimension());
            StdOut.println("Board looks like: ");            
            StdOut.println(initial.toString());
            StdOut.println("Hamming of board is: " + initial.hamming());
            StdOut.println("Manhattan of board is: " + initial.manhattan());
            initial.isGoal();
            
            StdOut.println("Neighbors are: " + initial.neighbors());
            
            
        } // unit tests (not graded)
    }
}