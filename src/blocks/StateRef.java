package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class StateRef extends Ref
{

	public StateRef(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"StateRef\": {\"stateRef\": %s}}", value);
	}

	public int getID(World world, Point me)
	{
		if(value.type.equals(TokenType.ME)) return world.getCellID(me);
		if(value.type.equals(TokenType.ARROWCHAIN)) return world.resolveArrowChain(value.data, me);
		if(value.type.equals(TokenType.IDENTIFIER)) return world.getStateID(value.data);
		throw new RuntimeException();
	}
}
