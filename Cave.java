package wumpus;
import java.util.ArrayList;
/**
 * A class that will model a single cave for "Hunt the Wampus"
 * each cave has its own name and number
 * and it is connected to other caves through tunnels
 * the caves are empty by default but can be updated
 * @author Catie Baker edited by Jordan Burgmeier
 */

public class Cave extends java.lang.Object{
	// Fields for class Cave
	private String caveName;
	private int caveNum;
	private ArrayList<Integer> adjCave;
	private CaveContents contents;
	private boolean visited;
    
    /**
     * Constructor of class Cave
     * Creates a new cave with the specified characteristics
     * @param name - the name of the cave
     * @param num - the cave number
     * @param adj - a list of the numbers for the adjacent caves
     */ 
    public Cave(String name,int num, ArrayList<Integer> adj){
        // initialize instance variables
        this.caveName = name;
        this.caveNum = num;
        this.adjCave =adj; 
        this.contents = CaveContents.EMPTY;
        this.visited = false;
    }
    
    /**
     * An accessor for the number of an adjacent cave
     * @param tunnel - the tunnel number (1-3)
     * @returns number of adjacent cave through that 
     *          tunnel or -1 if there is no tunnel
     */
    public int getCaveDownTunnel(int tunnelNum){
    	if ((tunnelNum <= this.adjCave.size()) && (tunnelNum > 0)) {
    		return this.adjCave.get(tunnelNum -1);
    	}
    	else {
    			return -1;
    	}
    }
    	
    /**8
     * An accessor for the cave name
     * @return    the name of the cave if it has been visited 
     *            "unknown" if it has not been visited
     */
    public String getCaveName(){
        if(this.visited == false){
            return "unknown"; 
        }
        else {
            return this.caveName; 
        }
    }
    
    /** 
     * An accessor for the number of the cave.
     * @return the cave's number
     */ 
    public int getCaveNum(){
       return this.caveNum;
    }
    
    /** 
     * An accessor method for the contents (CaveContents.EMPTY, CaveContents.WUMPUS, 
     *                                      CaveContents.BATS, or CaveContents.PIT)
     * @return the cave contents
     */ 
    public CaveContents getContents(){
        return this.contents;
    }
    
    /** 
     * An accessor for the number of caves
     * 		that are adjacent to this one
     * @return number of 
     */ 
    public int getNumTunnels() {
    	return this.adjCave.size();		
    } 
    
    /** 
     * Marks the cave having been visited
     * @return as being listed as visited
     */
    public void markAsVisited(){
        this.visited = true;
    }
    
    /**
     *Sets the contents of the cave (CaveContents.EMPTY, CaveContents.WUMPUS, 
     *                                CaveContents.BATS, or CaveContents.PIT)
     * @param contents - the new contents                            
     */
    public void setContents(CaveContents contents){
        this.contents = contents;
    }


}

    
    
    
    
    
    
    
    
    
    
    
    