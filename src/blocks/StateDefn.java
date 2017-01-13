package blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class StateDefn extends Defn
{
	public Token color;
	public List<ClassRef> classRefs = new ArrayList<ClassRef>();
	private Rules rules;

	public StateDefn(Token value, Token color)
	{
		super(value);
		this.color = color;
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
		return String.format("{\"StateDefn\": {\"stateID\": %s, \"repr\": %s, \"classRefs\": %s, \"rules\": %s}}", value, color, classRefs, rules);
	}
	
	public Rules getRules()
	{
		return rules;
	}

	public int applyRules(World world, Point me)
	{
		Integer newCell = rules.apply(world, me, classRefs);
		if(newCell == null)
			return world.getCellID(me);
		return newCell;
	}
}
