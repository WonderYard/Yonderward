package blocks;

import java.util.ArrayList;
import java.util.List;

public class Defns extends AST
{
	private List<Defn> stateDefns = new ArrayList<Defn>();
	private List<Defn> classDefns = new ArrayList<Defn>();
	private List<Defn> nbhdDefns = new ArrayList<Defn>();

	public void addDefn(AST root)
	{
		if(root instanceof StateDefn) this.stateDefns.add((StateDefn) root);
		else if(root instanceof ClassDefn) this.classDefns.add((ClassDefn) root);
		else if(root instanceof NbhdDefn) this.nbhdDefns.add((NbhdDefn) root);
		else throw new RuntimeException();
	}
		
	@Override
	public String toString() {
		return String.format("{\"Defns\": {\"stateDefns\": %s, \"classDefns\": %s, \"nbhdDefns\": %s}}", stateDefns, classDefns, nbhdDefns);
	}

	public StateDefn getStateDefn(int cell)
	{
		return (StateDefn) stateDefns.get(cell);
	}

	public Defns validate()
	{
		// Check if identifiers are always referencing existing definitions (states, classes and neighborhoods)
		return this;
	}

	public List<Defn> getStateDefns()
	{
		return stateDefns;
	}
	
	public List<Defn> getClassDefns()
	{
		return classDefns;
	}
	
	public List<Defn> getNbhdDefns()
	{
		return nbhdDefns;
	}
}
