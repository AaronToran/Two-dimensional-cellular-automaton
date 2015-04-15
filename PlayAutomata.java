/*
    PlayAutomata.java
    CIS 250
    Dave Klick
    2015-02-06
    
    This program can be used to test out the class that
    implements the 2-D cellular automata assignment.
*/

import java.util.ArrayList;
import java.util.StringTokenizer;

class Point {
    public int x, y;
    public Point(final int n1, final int n2) {
        x = n1;
        y = n2;
    }
}

class Pattern {
    public String name;
    public ArrayList<Point> coord;
    public Pattern(String name) {
        this.name = name;
        coord = new ArrayList<Point>();
    }
    public Pattern() {
        this("");
    }
}

public class PlayAutomata {
    public static void main(String[] args) {
        char again;                    // used to play a new 2-d automata pattern
        String strQuit;
        char quit;                     // used to keep playing an existing pattern
        int i, j, k;
        int choice;                    // keeps track of user menu item choice
        String[] data;
        ArrayList<Pattern> pattern = new ArrayList<Pattern>(); // pattern data parsed into Pattern objects
        ArrayList<Integer> xy = new ArrayList<Integer>();      // stores coordinate data during parsing
        Keyboard kbd = new Keyboard();
        
        // Starting pattern data for 2-d cellular automata. This is used
        // like a DATA statement in old Basic programs. The alternative,
        // which would be much more flexible, would be to use a data file -
        // but fewer pieces was a higher priority for this assignment.
        String patternList = "block,1,1,1,2,2,1,2,2;"
            + "block,1,1,1,2,2,1,2,2;"
            + "behive,1,2,1,3,2,1,2,4,3,2,3,3;"
            + "loaf,1,2,1,3,2,1,2,4,3,2,3,4,4,3;"
            + "boat,1,1,1,2,2,1,2,3,3,2;"
            + "blinker,2,1,2,2,2,3;"
            + "toad,2,2,2,3,2,4,3,1,3,2,3,3;"
            + "beacon,1,1,1,2,2,1,3,4,4,3,4,4;"
            + "pulsar,1,5,1,11,2,5,2,11,3,5,3,6,3,10,3,11,5,1,5,2,5,3,5,6,5,7,5,9,5,10,5,13,5,14,5,15,6,3,6,5,6,7,6,9,6,11,6,13,7,5,7,6,7,10,7,11,9,5,9,6,9,10,9,11,10,3,10,5,10,7,10,9,10,11,10,13,11,1,11,2,11,3,11,6,11,7,11,9,11,10,11,13,11,14,11,15,13,5,13,6,13,10,13,11,14,5,14,11,15,5,15,11,;"
            + "glider,1,2,2,3,3,1,3,2,3,3;"
            + "lwss,1,2,1,5,2,6,3,2,3,6,4,3,4,4,4,5,4,6;"
            + "r-pentomino,1,2,1,3,2,1,2,2,3,2;"
            + "diehard,1,7,2,1,2,2,3,2,3,6,3,7,3,8;"
            + "acorn,1,2,2,4,3,1,3,2,3,5,3,6,3,7;"
            + "gosper-glider-gun,1,25,2,23,2,25,3,13,3,14,3,21,3,22,3,35,3,36,4,12,4,16,4,21,4,22,4,35,4,36,5,1,5,2,5,11,5,17,5,21,5,22,6,1,6,2,6,11,6,15,6,17,6,18,6,23,6,25,7,11,7,17,7,25,8,12,8,16,9,13,9,14;"
            + "switch-engine-1,1,7,2,5,2,7,2,8,3,5,3,7,4,5,5,3,6,1,6,3;"
            + "switch-engine-2,1,1,1,2,1,3,1,5,2,1,3,4,3,5,4,2,4,3,4,5,5,1,5,3,5,5;"
            + "switch-engine-3,1,1,1,2,1,3,1,4,1,5,1,6,1,7,1,8,1,10,1,11,1,12,1,13,1,14,1,18,1,19,1,20,1,27,1,28,1,29,1,30,1,31,1,32,1,33,1,35,1,36,1,37,1,38,1,39";

        // This section parses the strings of pattern data into
        // Pattern objects so they are easier to use.
        data = patternList.split(";");
        for (i=0; i<data.length; i++) {
            String[] token = data[i].split(",");
            // Check for and reject obvious errors.
            int numTokens = token.length;
            if (numTokens < 1) {
                System.out.println("Error: No name supplied for start pattern " + (i+1));
            } else if (numTokens < 2) {
                System.out.println("Error: No coordinates supplied for start pattern " + (i+1));
            } else if (numTokens % 2 != 1) {
                System.out.println("Error: Invalid number of values for coordinates for start pattern " + (i+1));
            } else {
                // Now parse the tokenized coordinate values into int values.
                // The first token must be skipped since it is a pattern name.
                xy.clear();
                boolean coordsValid = true;
                for (j=1; j<numTokens; j++) {
                    try {
                        xy.add(Integer.parseInt(token[j]));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: invalid coordinate for start pattern " + (i+1));
                        coordsValid = false;
                        break;
                    }
                }
                
                // If the coordinates were all valid, create and store a
                // Pattern object to represent the current data string.
                if (coordsValid) {
                    Pattern pat = new Pattern(token[0]);
                    for (k=0; k<xy.size(); k+=2) {
                        pat.coord.add(new Point(xy.get(k), xy.get(k+1)));
                    }
                    pattern.add(pat);
                }
            }
        }
        
        // Finally we get to play the game. Create one.
        Automata game = new Automata();
        
        again = 'Y';    // again is used to play another game
        while (again == 'Y' || again == 'y') {
            // choice stores the pattern the user chooses to play
            choice = 0;
            
            // create a menu of pattern names
            System.out.println("Choose a pattern:");
            for (i=0; i<pattern.size(); i++) {
                System.out.println((i+1) + ". " + pattern.get(i).name);
            }
            // add "Quit" to the end of the menu
            System.out.println((pattern.size()+1) + ". Quit");
            
            // get user choice and exit if user chose to quit
            choice = kbd.getInt("Your choice: ", 1, pattern.size()+1);
            if (choice == pattern.size()+1) break;
            
            // reset the 2-d automata to the pattern the user chose
            game.reset();
            int len = pattern.get(choice-1).coord.size();
            for (Point p : pattern.get(choice-1).coord) game.setCell(p.x, p.y);
            
            // quit is used to break out of a running game
            quit = 'N';
            while (quit != 'Q' && quit != 'q') {
                // display the current generation of the automata
                System.out.println("Generation: " + game.getGeneration());
                System.out.println(game);
                // increment the automata to the next generation
                strQuit = kbd.getString("Press enter to continue (Q to quit)...");
                if (strQuit.length() == 1) quit = strQuit.toUpperCase().charAt(0);
                if (quit != 'Q' && quit != 'q') game.nextGeneration();
            }
            
            // see if the user wants to play another game
            again = kbd.getChar("Play again (Y/N)? ", "YyNn");
        }
    }
}
