package wumpus;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * Class that models a maze of caves for the "Hunt the Wumpus" game.
 *   @author Catie Baker edited By Jordan Burgmeier
 *   @version 4/10/18
 */
public class CaveMaze {
	  private Cave currentCave;
	  private ArrayList<Cave> caves;
	  private int grenade;
	  private boolean able = true;
	  private int numWumpi;
	  
	  /**
	   * Constructs a CaveMaze from the data found in a file.
	   *   @param filename the name of the cave data file
	   */
	  public CaveMaze(String filename) throws java.io.FileNotFoundException {
	      Scanner infile = new Scanner(new File(filename));
	      
	      int numCaves = infile.nextInt();
	      this.caves = new ArrayList<Cave>();
	      for (int i = 0; i < numCaves; i++) {
	          this.caves.add(null);
	      }
	      
	      for (int i = 0; i < numCaves; i++) {
	          int num = infile.nextInt(); 
	          int numAdj = infile.nextInt(); 
	          ArrayList<Integer> adj = new ArrayList<Integer>();
	          for (int a = 0; a < numAdj; a++) {
	              adj.add(infile.nextInt());
	          }
	          String name = infile.nextLine().trim();          
	          this.caves.set(num, new Cave(name, num, adj));
	      }
	      
	      this.currentCave = this.caves.get(0);
	      this.currentCave.markAsVisited();
	      numWumpi = ((int)( Math.random()* 3) + 1);
	      grenade = (numWumpi * 3);
	      
	      for(int i = numWumpi; i > 0; i--) {
	    	  this.caves.get(findRandomCave()).setContents(CaveContents.WUMPUS);
	      }
	      this.caves.get(findRandomCave()).setContents(CaveContents.BATS);
	      this.caves.get(findRandomCave()).setContents(CaveContents.PIT);
	  }
	  
	  private int findRandomCave() {
		  int randomCave = 0;
		  int totalCaves = this.caves.size();
		  boolean check = false;
		  while (check = false) {
			  randomCave = ((int)(Math.random() * totalCaves));
			  Cave x = this.caves.get(randomCave);
			  if ((randomCave != 0) && (x.getContents()) == CaveContents.EMPTY){
				  check = true;
			  }
		  }
		  return randomCave;
	}

	  /**
	   * Moves the player from the current cave along the specified tunnel, marking the new cave as visited.
	   *   @param tunnel the number of the tunnel to be traversed (1-3)
	   */
	  public String move(int tunnel) {
	      if (tunnel < 1 || tunnel > this.currentCave.getNumTunnels()) {
	          return "There is no tunnel number " + tunnel;
	      }
	      
	      int caveNum = this.currentCave.getCaveDownTunnel(tunnel);
	      this.currentCave = this.caves.get(caveNum);
	      this.currentCave.markAsVisited();
	      
	      if(this.currentCave.getContents() == CaveContents.PIT) {
	    	  return "A swarm of bats carried you into a new part of the cave...";
	    	  this.currentCave = this.caves.get(findRandomCave());
	    	  able = true;
	      }
	      else if(this.currentCave.getContents() == CaveContents.BATS) {
	    	  return "You fell down into a pit... whoops";
	    	  able = false;
	      }
	      
	      else if(this.currentCave.getContents() == CaveContents.WUMPUS) {
	    	  return "A wumpus is in this cave... CHOMP CHOMP CHOMP";
	    	  able = false; 
	      }
	      return "Moving down tunnel " + tunnel + "...";
	  }
	  
	  /**
	   * Attempts to toss a stun grenade into the specified tunnel, but currently no grenades.
	   *   @param tunnel the number of the tunnel to be bombed (1-3)
	   */
	  public String toss(int tunnel) {
		  if ((stillWumpi() = true) && (grenade > 0)) {
			  int caveNum = this.currentCave.getCaveDownTunnel(tunnel);
			  Cave tossCaves = this.caves.get(caveNum);
			  if(tossCaves.getContents() == CaveContents.WUMPUS) {
				  grenade --;
				  numWumpi --;
				  tossCaves.setContents(CaveContents.EMPTY);
				  return "You got one of the wumpus, there are" + numWumpi + "left to get";  
			  }
			  for (int i = 1; i <= this.currentCave.getNumTunnels(); i ++) {
				  int theNum = this.currentCave.getCaveDownTunnel(i);
				  Cave hold = this.caves.get(theNum);
				  if(hold.getContents() == CaveContents.WUMPUS) {
						 System.out.println("You have spooked a wumpus!");
						 int run = hold.getNumTunnels();
						 int pick = (int)(Math.random()*run + 1);
						 hold.setContents(CaveContents.EMPTY);
						 Cave move = this.caves.get(pick);
						 move.setContents(CaveContents.WUMPUS);
						 if(this.currentCave.getContents() == CaveContents.WUMPUS) {
						      able = false;
						      return "The wampus finds you in teh cave... CHOMP CHOMP CHOMP";
						 }
				  }
			  }
			  grenade --;
			  return "You threw a grenade down the tunnel " + tunnel + ". You have " + grenade + "grenades left";
		  }
		  return "You are out of your grenades, there is nothing to throw!";
	  }  
	  
	  /**
	   * Displays the current cave name and the names of adjacent caves. Caves that have not yet been 
	   * visited are displayed as "unknown".  
	   */
	  public String showLocation() {
	    String message = "You are currently in " + this.currentCave.getCaveName();
	    
		for (int i = 1; i <= this.currentCave.getNumTunnels(); i++) {
		    int caveNum = this.currentCave.getCaveDownTunnel(i);
		    Cave adjCave = this.caves.get(caveNum);
		    message += "\n    (" + i + ") " + adjCave.getCaveName();
		}
		for (int i = 1; i <= this.currentCave.getNumTunnels(); i++) {
			int caveNum = this.currentCave.getCaveDownTunnel(i);
		    Cave y = this.caves.get(caveNum);
		    if(y.getContents() == CaveContents.WUMPUS) {
				message += "\n You smell an awful stench coming from somewhere nearby.";
			}
		    else if(y.getContents() == CaveContents.PIT) {
				message += "\n You feel a draft coming out from one of the tunnels.";
			}
			else if(y.getContents() == CaveContents.BATS) {
				message += "\n You hear the flapping of wings close by.";
			}
		}
		return message;
	  }
	  
	  
	  /**
	   * Reports whether the player is still able (healthy and mobile).
	   *   @return able or false
	   */
	  public boolean stillAble() {
	      return able;
	  }

	  /**
	   * Reports whether there are any wumpi remaining.
	   *   @return true 
	   */
	  public boolean stillWumpi() {
		  		if (numWumpi > 0) {
		  		return true;
		  		}
		  		else {
		  			return false;
		  		} 
	  }
	}













