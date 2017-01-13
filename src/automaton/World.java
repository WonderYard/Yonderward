package automaton;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blocks.*;
import lexicalpkg.AnotherParser;
import lexicalpkg.InvalidTokenException;
import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;
import lexicalpkg.ParserTester;
import lexicalpkg.UnexpectedTokenException;

public class World
{	
	private int cols, rows;
	private int[][] grid;
	public Neighborhood neighborhood;
	public List<Defn> stateDefns = new ArrayList<Defn>();
	private List<Defn> classDefns = new ArrayList<Defn>();
	private List<Defn> nbhdDefns = new ArrayList<Defn>();
	public Map<String,NbhdDefn> nbhdMap = new HashMap<String, NbhdDefn>();
	public Map<String, ClassDefn> classMap = new HashMap<String, ClassDefn>();

	public World(int cols, int rows, Defns defns) {
		this.cols = cols;
		this.rows = rows;
		grid = new int[cols][rows];
		this.stateDefns.addAll(defns.getStateDefns());
		this.classDefns.addAll(defns.getClassDefns());
		this.nbhdDefns.addAll(defns.getNbhdDefns());
		
		for(Defn nbhdDefn : nbhdDefns)
			this.nbhdMap.put(nbhdDefn.getName(), (NbhdDefn) nbhdDefn);
		for(Defn classDefn : classDefns)
			this.classMap.put(classDefn.getName(), (ClassDefn) classDefn);
		
		
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
				int cell = getCell(x, y);
				StateDefn stateDefn = (StateDefn) stateDefns.get(cell);
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
	
	@Override
	public String toString()
	{
		String s = "";
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				System.out.print( ((StateDefn) stateDefns.get(getCell(x, y))).color.data.charAt(1));
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
		World world = new World(10, 10, ast);
		for(int i = 0; i < 100; i++) {
			System.out.println(world);
			world.evolve();
		}
	}
}
