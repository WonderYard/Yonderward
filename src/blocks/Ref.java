package blocks;

import lexicalpkg.Lexer.Token;

public class Ref extends AST
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
}
