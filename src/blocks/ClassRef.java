package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

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
	
	public int getID(World world, Point me)
	{
		if(value.type.equals(TokenType.ME)) return world.getCellID(me);
		if(value.type.equals(TokenType.ARROWCHAIN)) return world.resolveArrowChain(value.data, me);
		if(value.type.equals(TokenType.IDENTIFIER)) return world.getClassID(value.data);
		throw new RuntimeException();
	}
}
