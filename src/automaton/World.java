package automaton;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blocks.*;
import lexicalpkg.AnotherParser;
import lexicalpkg.InvalidTokenException;
import lexicalpkg.ParserTester;
import lexicalpkg.UnexpectedTokenException;

public class World
{
	public enum Neighborhood
	{
		MOORE(new Point[]{
			new Point(-1, -1),
			new Point(-1,  0),
			new Point(-1,  1),
			new Point( 0, -1),
			new Point( 0,  1),
			new Point( 1, -1),
			new Point( 1,  0),
			new Point( 1,  1)
		})/*,
		VON_NEUMANN(new Point[]{
			new Point(-1,  0),
			new Point( 0, -1),
			new Point( 0,  1),
			new Point( 1,  0)
		})*/;
		
		public Point[] points;
		
		private Neighborhood(Point[] neighborhood)
		{
			this.points = neighborhood;
		}
	}
	
	private int cols, rows;
	private int[][] grid;
	private Point[] neighborhood;
	private Defns defns;

	public World(int cols, int rows, Defns defns) {
		this.cols = cols;
		this.rows = rows;
		grid = new int[cols][rows];
		this.defns = defns;
		this.neighborhood = Neighborhood.MOORE.points;
	}
	
	public Point getSize()
	{
		return new Point(cols, rows);
	}
	
	public int getCell(int x, int y) {
		return grid[y][x];
	}
	
	public void setCell(int x, int y, int state) {
		grid[y][x] = state;
	}
	
	public void evolve() {
		int[][] newGrid = new int[cols][rows];
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				int cell = getCell(x, y);
				StateDefn state = defns.getStateDefn(cell);
				List<Rule> rules = state.getRules().getRules();
				newGrid[y][x] = cell;
				
				for(int i = 0; i < rules.size(); i++) {
					StateRef newCell = rules.get(i).apply(this);
					if(!(newCell == null)) {
						newGrid[y][x] = newCell.getID(this, new Point(x, y));
						break;
					}
				}
			}
		}
		grid = newGrid;
	}

	public int getCellID(Point point) {
		return grid[point.y][point.x];
	}

	public int resolveArrowChain(String chain, Point me)
	{
		int x = 0;
		int y = 0;
		for(char c : chain.toCharArray())
		{
			switch(c)
			{
				case '>':
					x++; break;
				case '<':
					x--; break;
				case '^':
					y--; break;
				case 'v':
					y++; break;
			}
		}
		return getCellID(me.add(x, y));
	}
	
	public int getStateID(String stateName)
	{
		List<Defn> stateDefns = defns.getStateDefns();
		for(int i = 0; i < stateDefns.size(); i++)
		{
			if(stateDefns.get(i).getName().equals(stateName)) return i;
		}
		throw new RuntimeException();
	}
	
	@Override
	public String toString()
	{
		String s = "";
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				System.out.print(getCell(x, y));
			}
			System.out.println();
		}
		return s;
	}
	
	public static void main(String[] args) throws InvalidTokenException, UnexpectedTokenException
	{
		String slash=File.separator;
		AnotherParser parser = new AnotherParser();
		String code = ParserTester.getCodeFromFile(slash+"test"+slash+"gol.txt");
		Defns ast = (Defns) parser.parse(code);
		System.out.println(ast);
		World world = new World(5, 5, ast);
		System.out.println(world);
		world.evolve();
		System.out.println(world);		
	}
}
