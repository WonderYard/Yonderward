package blocks;

import lexicalpkg.Lexer.Token;

public abstract class Defn extends AST
{
	private String name;
	
	public Defn(Token token)
	{
		this.name = token.data;
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"Defn\": {\"defn\": %s}}", value);
	}

	public String getName()
	{
		return name;
	}
}
