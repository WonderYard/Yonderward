package blocks;

import lexicalpkg.Lexer.Token;

public class Defn extends AST
{
	public Defn(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"Defn\": {\"defn\": %s}}", value);
	}

	public String getName()
	{
		return value.data;
	}
}
