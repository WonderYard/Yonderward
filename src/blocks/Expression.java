package blocks;

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
}
