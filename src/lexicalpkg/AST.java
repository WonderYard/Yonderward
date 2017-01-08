package lexicalpkg;

import lexicalpkg.Lexer.Token;

public class AST
{
	//token
	protected Token token;
	protected AST children;
	
	public AST(Token token)
	{
		this.token = token;
	}
	
	public void addChild(Token t)
	{
		children=new AST(t);
	}
	
	@Override
	public String toString()
	{
		return String.format("AST(%s, %s)", token, children);
	}
}
