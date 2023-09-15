package maze.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import core.Pos;

public class MazeExplorer {
	private Maze m;
	private Pos location;
	private TreeSet<Pos> treasureFound;
	private MazeExplorer goal;
	
	public MazeExplorer(Maze m, Pos location) {
		this.m = m;
		this.location = location;
		treasureFound = new TreeSet<>();
	}
	
	public Pos getLocation() {return location;}

	public Set<Pos> getAllTreasureFromMaze() {
		return m.getTreasures();
	}

	public Set<Pos> getAllTreasureFound() {
		return treasureFound;
	}

	public int getNumTreasuresFound() {
		return treasureFound.size();
	}

	public MazeExplorer getGoal() {
		if (goal == null) {
			goal = m.getGoal();
		}
		return goal;
	}

	public ArrayList<MazeExplorer> getSuccessors() {
		// TODO: It should add as a successor every adjacent, unblocked neighbor square.
		// I added a comment for demonstration purposes.

		//https://github.com/alexander-jackson/maze-solving/blob/master/AStarMazeSolver.java
		// This repo helped me sort of figure out how to structure and include in my maze explorer
		// I had to sort out what needed to be left out and how to fit it into what functions I had
		// already provided to see how it would work best with this repo.

		ArrayList<MazeExplorer> results = new ArrayList<>();
		ArrayList<Pos> adjacent = m.getNeighbors(this.location);
		for( Pos neighbors :adjacent) {
			if(!m.blocked(this.location,neighbors)){
				MazeExplorer successor = new MazeExplorer(m, neighbors);
				//getAllTreasureFromMap only worked for passing the first unit test but didn't work for the second
				successor.addTreasures(getAllTreasureFound());
				if(m.isTreasure(successor.getLocation())){
					successor.treasureFound.add(successor.location);
				}
				results.add(successor);
			}
		}
        return results;
	}
	
	public void addTreasures(Collection<Pos> treasures) {
		treasureFound.addAll(treasures);
	}
	
	public String toString() {
		StringBuilder treasures = new StringBuilder();
		for (Pos t: treasureFound) {
			treasures.append(";");
			treasures.append(t.toString());
		}
		return "@" + location.toString() + treasures;
	}
	
	@Override
	public int hashCode() {return toString().hashCode();}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MazeExplorer that) {
			return this.location.equals(that.location) && this.treasureFound.equals(that.treasureFound);
		} else {
			return false;
		}
	}

	public boolean achievesGoal() {
		return this.equals(getGoal());
	}

	public Maze getM() {
		return m;
	}
}
