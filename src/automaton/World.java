package automaton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blocks.*;
import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class World
{	
	private int cols, rows;
	private int[][] grid;
	public Neighborhood neighborhood;
	public List<RulerDefn> stateDefns;
	private List<RulerDefn> classDefns;
	private List<Defn> nbhdDefns;
	public Map<String,NbhdDefn> nbhdMap = new HashMap<String, NbhdDefn>();
	public Map<String, ClassDefn> classMap = new HashMap<String, ClassDefn>();
	
	public World(int cols, int rows)
	{
		this.cols = cols;
		this.rows = rows;
		grid = new int[cols][rows];
		
		this.neighborhood = new Neighborhood();
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, "<^")));
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, "^")));
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, ">^")));
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, "<")));
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, ">")));
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, "<v")));
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, "v")));
		this.neighborhood.addArrowChain(new ArrowChain(new Token(TokenType.ARROWCHAIN, ">v")));
	}
	
	public World(int cols, int rows, Defns defns)
	{
		this(cols, rows);
		setDefns(defns);
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
	
	public void evolve()
	{
		int[][] newGrid = new int[cols][rows];
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				RulerDefn stateDefn = stateDefns.get(getCell(x, y));
				newGrid[y][x] = stateDefn.applyRules(this, new Point(x, y));
			}
		}
		grid = newGrid;
	}

	public Integer getCellID(Point point)
	{
		if(point.x >= 0 && point.x < cols && point.y >= 0 && point.y < rows)
			return grid[point.y][point.x];
		return null;
	}

	public Integer resolveArrowChain(String chain, Point me)
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
		return getCellID(new Point(me.x + x, me.y + y));
	}
	
	public int getStateID(String stateName)
	{
		for(int i = 0; i < stateDefns.size(); i++)
		{
			if(stateDefns.get(i).getName().equals(stateName)) return i;
		}
		throw new RuntimeException();
	}
	
	public int getClassID(String className)
	{
		for(int i = 0; i < classDefns.size(); i++)
		{
			if(classDefns.get(i).getName().equals(className)) return i;
		}
		throw new RuntimeException();
	}

	public void setDefns(Defns defns)
	{
		stateDefns = new ArrayList<RulerDefn>();
		classDefns = new ArrayList<RulerDefn>();
		nbhdDefns = new ArrayList<Defn>();
		stateDefns.addAll(defns.getStateDefns());
		classDefns.addAll(defns.getClassDefns());
		nbhdDefns.addAll(defns.getNbhdDefns());
		
		for(Defn nbhdDefn : nbhdDefns)
			nbhdMap.put(nbhdDefn.getName(), (NbhdDefn) nbhdDefn);
		for(Defn classDefn : classDefns)
			classMap.put(classDefn.getName(), (ClassDefn) classDefn);
		
		int totalStates = stateDefns.size();
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				int cell = getCell(x, y);
				if(cell >= totalStates) setCell(x, y, 0);
			}
		}
	}
}
