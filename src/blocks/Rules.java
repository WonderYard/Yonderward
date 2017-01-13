package blocks;

import java.util.ArrayList;
import java.util.List;

import automaton.Point;
import automaton.World;

public class Rules extends AST
{
	private List<Rule> rules = new ArrayList<Rule>();

	public void addRule(AST root)
	{
		if(root instanceof Rule) this.rules.add((Rule) root);
		else throw new RuntimeException();
	}

	@Override
	public String toString()
	{
		return String.format("{\"Rules\": {\"rules\": %s}}", rules);
	}

	public Integer apply(World world, Point me, List<ClassRef> classRefs)
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
