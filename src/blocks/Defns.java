package blocks;

import java.util.ArrayList;
import java.util.List;

public class Defns extends AST
{
	private List<RulerDefn> stateDefns = new ArrayList<RulerDefn>();
	private List<RulerDefn> classDefns = new ArrayList<RulerDefn>();
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

	public List<RulerDefn> getStateDefns()
	{
		return stateDefns;
	}
	
	public List<RulerDefn> getClassDefns()
	{
		return classDefns;
	}
	
	public List<Defn> getNbhdDefns()
	{
		return nbhdDefns;
	}
}
