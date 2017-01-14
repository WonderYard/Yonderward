package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public abstract class Ref extends AST
{
	public Ref(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"Ref\": {\"ref\": %s}}", value);
	}

	public abstract boolean refEqualToID(World world, Point me, Integer stateRefID);
}
