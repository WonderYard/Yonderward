package automaton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World
{
	private int cols, rows;
	private int[][] grid;
	private List<State> states;
	private Point[] neighborhood;

	public World(int cols, int rows, List<State> states, Point[] neighborhood) {
		this.cols = cols;
		this.rows = rows;
		grid = new int[cols][rows];
		this.states = states;
		this.neighborhood = neighborhood;
	}
	
	public int getCell(int x, int y) {
		return grid[y][x];
	}
	
	public void setCell(int x, int y, int state) {
		grid[y][x] = state;
	}
	
	public void addState(String color) {
		states.add(new State(color));
	}
	
	private Map<Integer, Integer> getNeighbors(int x, int y) {
		Map<Integer, Integer> neighbors = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < neighborhood.length; i++) {
			int nx = this.neighborhood[i].x + x;
			int ny = this.neighborhood[i].y + y;
			if(validateNeighbor(nx, ny)) {
				int cell = this.getCell(nx, ny);
				neighbors.put(cell, neighbors.getOrDefault(cell, 0) + 1);
			}
		}
		return neighbors;
	}
	
	private boolean validateNeighbor(int x, int y) {
		if(x < 0 || y < 0 || x >= this.rows || y >= this.cols) return false;
		return true;
	}
	
	public void evolve() {
		int[][] newGrid = new int[cols][rows];
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				int cell = getCell(x, y);
				Map<Integer, Integer> neighbors = getNeighbors(x, y);
				List<Rule> rules = states.get(cell).rules;
				newGrid[y][x] = cell;
				
				for(int i = 0; i < rules.size(); i++) {
					int newCell = rules.get(i).apply(neighbors);
					if(!(newCell == -1)) {
						newGrid[y][x] = newCell;
						break;
					}
				}
			}
		}
		grid = newGrid;
	}
}
