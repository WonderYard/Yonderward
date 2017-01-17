package blocks;

import lexicalpkg.Lexer.Token;

public class ArrowChain extends AST
{
	public ArrowChain(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"ArrowChain\": {\"arrowChain\": %s}}", value);
	}
}
