package blocks;

import lexicalpkg.Lexer.Token;

public class AST
{
	protected Token value;

	public AST() {};

	public AST(Token value)
	{
		this.value = value;
	}
	
	private String toString(String className)
	{
		return String.format("{\"%s\": {\"value\": %s}}", className, value);
	}
	
	@Override
	public String toString()
	{
		return toString(this.getClass().getSimpleName());
	}
}