package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class Expression extends AST
{
	public Expression() {}
	
	public Expression(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"Expression\": {\"expression\": %s}}", value);
	}

	public boolean apply(World world, Point me)
	{
		throw new RuntimeException();
	}
}
