package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class StateDefn extends RulerDefn
{
	public String color;

	public StateDefn(Token token, Token color)
	{
		super(token);
		this.color = color.data;
	}

	@Override
	public String toString()
	{
		return String.format("{\"StateDefn\": {\"stateID\": %s, \"repr\": %s, \"classRefs\": %s, \"rules\": %s}}", value, color, classRefs, rules);
	}

	public Integer applyRules(World world, Point me)
	{
		Integer newCell = super.applyRules(world, me);
		if(newCell == null)
			return world.getCellID(me);
		return newCell;
	}
}
