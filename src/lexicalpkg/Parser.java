package lexicalpkg;

import java.util.ArrayList;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class Parser 
{
	//token returned by the lexer
	Token currToken;
	Lexer l;
	ArrayList<ArrayList<Token>> branches = new ArrayList<>();
	ArrayList<Token> currBranch;
	
	public Parser (Lexer l) throws InvalidTokenException
	{
		this.l=l;
		currToken=l.lex();
	}
	
	boolean accept(TokenType t) throws InvalidTokenException
	{
		if(currToken.type==t)
		{
			currBranch.add(currToken);
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
	void expression() throws InvalidTokenException, UnexpectedTokenException
	{
		term();
		while(accept(TokenType.BINARYOP)) term();
	}
	
	void rule() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		expect(TokenType.TO);
		stateRef();
		while(accept(TokenType.WHEN)) expression();
		if(accept(TokenType.IN)) neighbourhood();

	}
	
	void stateDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		stateID();
		expect(TokenType.EXANUMBER);

	}
	
	void defns() throws InvalidTokenException, UnexpectedTokenException
	{
		currBranch= new ArrayList<>();
		
		if(accept(TokenType.STATE))
		{
			stateDefn();
			branches.add(currBranch);
		}
		
		currBranch= new ArrayList<>();
		
		if(accept(TokenType.EVOLVE))
		{
			rule();
			branches.add(currBranch);
		}
	}
	
	ArrayList<ArrayList<Token>> body() throws InvalidTokenException, UnexpectedTokenException
	{
		
		while(currToken.type==TokenType.STATE || currToken.type==TokenType.EVOLVE) defns();
		if(currToken.type!=TokenType.EOF) throw new InvalidTokenException();
		return branches;
	}
}
