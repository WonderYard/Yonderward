package lexicalpkg;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class Parser 
{
	Token currToken;
	Lexer l;
	
	public Parser (Lexer l) throws InvalidTokenException
	{
		this.l=l;
		currToken=l.lex();
	}
	
	boolean accept(TokenType t) throws InvalidTokenException
	{
		if(currToken.type==t)
		{
			currToken=l.lex();
			return true;
		}
		return false;
	}
	
	boolean expect(TokenType t) throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(t)) return true;
		throw new UnexpectedTokenException();
	}

	void AdjacencyPred() throws InvalidTokenException
	{
		if(accept(TokenType.NUMBER))
		{
			;
		}
		
	}
}
