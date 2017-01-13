package blocks;

import java.util.ArrayList;
import java.util.List;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class ClassDefn extends Defn
{
	private List<ClassRef> classRefs = new ArrayList<ClassRef>();
	private Rules rules;

	public ClassDefn(Token value)
	{
		super(value);
	}

	public void addClassRef(AST root)
	{
		if(root instanceof ClassRef) this.classRefs.add((ClassRef) root);
		else throw new RuntimeException();
	}
	
	public void setRules(AST root)
	{
		if(root instanceof Rules) this.rules = (Rules) root;
		else throw new RuntimeException();
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"ClassDefn\": {\"classID\": %s, \"classRefs\": %s, \"rules\": %s}}", value, classRefs, rules);
	}

	public Integer applyRules(World world, Point me)
	{
		return rules.apply(world, me, classRefs);
	}
}
