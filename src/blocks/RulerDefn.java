package blocks;

import java.util.ArrayList;
import java.util.List;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public abstract class RulerDefn extends Defn
{
	protected List<Rule> rules = new ArrayList<Rule>();
	protected List<ClassRef> classRefs = new ArrayList<ClassRef>();

	public RulerDefn(Token token)
	{
		super(token);
	}
	
	public void addClassRef(AST root)
	{
		if(root instanceof ClassRef) this.classRefs.add((ClassRef) root);
		else throw new RuntimeException();
	}
	
	public void addRule(AST root)
	{
		this.rules.add((Rule) root);
	}
	
	public Integer applyRules(World world, Point me)
	{
		if(rules != null) {
			for(Rule rule : rules) {
				StateRef newCell = rule.apply(world, me);
				if(newCell != null)
					return newCell.getID(world, me);
			}
		}
		if(classRefs == null) return null;
		for(ClassRef classRef : classRefs) {
			ClassDefn classDefn = world.classMap.get(classRef.value.data);
			Integer newCell = classDefn.applyRules(world, me);
			if(newCell != null)
				return newCell;
		}
		return null;
	}
}
