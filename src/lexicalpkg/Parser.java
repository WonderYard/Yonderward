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

	void stateID() throws InvalidTokenException, UnexpectedTokenException
	{
		expect(TokenType.IDENTIFIER);
	}
	void nbhdID() throws InvalidTokenException, UnexpectedTokenException
	{
		expect(TokenType.IDENTIFIER);
	}
	
	void stateRef() throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(TokenType.COORDINATE));
		else expect(TokenType.IDENTIFIER);
		
	}
	
	void coordinates() throws InvalidTokenException, UnexpectedTokenException
	{
		expect(TokenType.COORDINATE);
		while (accept(TokenType.COORDINATE));
		
	}
	
	void neighbourhood() throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(TokenType.COORDINATE));
		else expect(TokenType.IDENTIFIER);
		
	}
	
	void adjacencyPred() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		expect(TokenType.NUMBER);
		if(accept(TokenType.NUMBER));
		if(accept(TokenType.IN)) neighbourhood();
	}
	
	//Not in our grammar atm
	void relationalPred() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		expect(TokenType.EQUAL);
		stateRef();
	}
	
	void term() throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(TokenType.LEFTP))
		{
			expression();
			expect(TokenType.RIGHTP);
		}
		else if(accept(TokenType.NOT)) term();
		adjacencyPred();
	}
	void adjacencyPred() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		expect(TokenType.NUMBER);
		if(accept(TokenType.NUMBER));
		if(accept(TokenType.IN)) neighbourhood();
	}
}
