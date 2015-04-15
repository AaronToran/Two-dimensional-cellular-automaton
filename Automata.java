/*
    Aaron Toran
    Assignment 4
    D Klick
    Two-dimensional cellular automaton
    2/12/15
*/

public class Automata {
    public Automata() {
        reset();
    }
    private static final char LIVE = '*';
    private static final char DEAD = ' ';
    private static final int ROWS = 20;
    private static final int COLS= 60;

    int generation;
    char[][] grid = new char[ROWS][COLS];
    int[][] count = new int[ROWS][COLS];
    

    //returns the array  to default state
    public void reset(){
            for(int row = 0; row < ROWS; row++) {
                for(int col= 0; col < COLS; col++) {
                    grid[row][col] = DEAD;
                    count[row][col] =0;
                }
            }
        generation = 0;
    }
    //sets cell to live '*'
    public void setCell(int row, int col){
        if (isValidCell(row , col) == true){
            grid[row][col] = LIVE;
        }
    }
    //sets cell to dead  ' '
    public void clearCell(int row,int col){
        if (isValidCell(row , col) == true){
            grid[row][col] = DEAD;
        }
    }
    //makes sure cell is inside the range of the arrays
    protected boolean isValidCell(int row, int col){
        if(row  >= 0 && row < ROWS-1 && col >= 0&& col < COLS-1){
            return true;
        }else{
            return false;
        }
    }
    //get method to return int generation
    public int getGeneration(){
        return generation;
    }
    //checks for living Neighbors around the cell
    public  int getLiveNeighbors(int r, int c){
        int living = 0;
        if ( isValidCell(r,c)== true && grid[r][c] ==LIVE ) living--;
        for(int row = r-1; row < r+1; row++) {
            for( int col = -1; col < c+1; col++) {
                if ( isValidCell(row,col)== true && grid[row][col] ==LIVE ) living++;
            }
        }
       
        return living;
    }
    //creates the next generation based on the algo and incerments the generation int
    //problem here
    
    public int nextGeneration(){
        
        //gets the live neighbors for the count[][]
        for(int r = 0; r < ROWS; r++) {
            for(int c= 0; c < COLS; c++) {
                count[r][c] = getLiveNeighbors(r,c);
            }
        }
        //sets cells in grid[][] to live or dead
        for(int r = 0; r < ROWS; r++) {
            for(int c= 0; c < COLS; c++) {
                if(count[r][c] < 2|| count[r][c] >3){
                    clearCell(r,c);
            }else if(count[r][c] ==3){
                setCell(r,c);
            }else{}
             
            }
        }

        generation++;
        return generation;
    }
    
    public String toString(){
        String lifeGameBorad = "";
        for(int r = 0; r < ROWS; r++) {
            for(int c= 0; c< COLS; c++) {
                lifeGameBorad +=grid[r][c];
            }
            lifeGameBorad +=System.getProperty("line.separator");
        } 
        
      ///*Debuging
        lifeGameBorad +=System.getProperty("line.separator");
        lifeGameBorad+=System.getProperty("line.separator");
        lifeGameBorad+=System.getProperty("line.separator");
        for(int r = 0; r < ROWS; r++) {
            for(int c= 0; c< COLS; c++) {
                lifeGameBorad +=count[r][c];
               //lifegrid.append(grid[r][c]);
            }
          //  lifegrid.append("/n");
             lifeGameBorad +=System.getProperty("line.separator");
        } 
        //*/
        return lifeGameBorad;
    }
}




