package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class ClassRef extends Ref
{
	public ClassRef(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"ClassRef\": {\"classRef\": %s}}", value);
	}

	@Override
	public boolean refEqualToID(World world, Point me, Integer stateRefID)
	{
		for(ClassRef classRef : ((StateDefn) world.stateDefns.get(stateRefID)).classRefs)
		{
			if(classRef.value.data.equals(value.data)) return true;
		}
		return false;
	}
}
