package lexicalpkg;

import java.util.List;

import lexicalpkg.Lexer.Token;

public class AST
{
	//token
	private Token token;
	private List<AST> children;
	
	public AST(Token token, List<AST> children)
	{
		this.token = token;
		this.children = children;
	}
	
	@Override
	public String toString()
	{
		return String.format("AST(%s, %s)", token, children);
	}
}
