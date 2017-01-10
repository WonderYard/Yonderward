package lexicalpkg;

import java.util.ArrayList;

import lexicalpkg.Lexer.Token;
import lexicalpkg.TokenType.BasicTokenType;


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
	
	boolean accept(BasicTokenType t) throws InvalidTokenException
	{
		if(currToken.type==t)
		{
			currBranch.add(currToken);
			currToken=l.lex();
			return true;
		}
		return false;
	}
	
	boolean expect(BasicTokenType t) throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(t)) return true;
		throw new UnexpectedTokenException();
	}

	void stateID() throws InvalidTokenException, UnexpectedTokenException
	{
		expect(BasicTokenType.IDENTIFIER);
	}
	void nbhdID() throws InvalidTokenException, UnexpectedTokenException
	{
		expect(BasicTokenType.IDENTIFIER);
	}
	
	void stateRef() throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(BasicTokenType.COORDINATE));
		else expect(BasicTokenType.IDENTIFIER);
		
	}
	
	void coordinates() throws InvalidTokenException, UnexpectedTokenException
	{
		expect(BasicTokenType.COORDINATE);
		while (accept(BasicTokenType.COORDINATE));
		
	}
	
	void neighbourhood() throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(BasicTokenType.COORDINATE));
		else expect(BasicTokenType.IDENTIFIER);
		
	}
	
	void adjacencyPred() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		expect(BasicTokenType.NUMBER);
		if(accept(BasicTokenType.NUMBER));
		if(accept(BasicTokenType.IN)) neighbourhood();
	}
	
	//Not in our grammar atm
	void relationalPred() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		expect(BasicTokenType.EQUAL);
		stateRef();
	}
	
	void term() throws InvalidTokenException, UnexpectedTokenException
	{
		if(accept(BasicTokenType.LEFTP))
		{
			expression();
			expect(BasicTokenType.RIGHTP);
		}
		else if(accept(BasicTokenType.NOT)) term();
		adjacencyPred();
	}
	void expression() throws InvalidTokenException, UnexpectedTokenException
	{
		term();
		while(accept(BasicTokenType.BINARYOP)) term();
	}
	
	void rule() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		expect(BasicTokenType.TO);
		stateRef();
		while(accept(BasicTokenType.WHEN)) expression();
		if(accept(BasicTokenType.IN)) neighbourhood();

	}
	
	void stateDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		stateID();
		expect(BasicTokenType.EXANUMBER);

	}
	
	void defns() throws InvalidTokenException, UnexpectedTokenException
	{
		currBranch= new ArrayList<>();
		
		if(accept(BasicTokenType.STATE))
		{
			stateDefn();
			branches.add(currBranch);
		}
		
		currBranch= new ArrayList<>();
		
		if(accept(BasicTokenType.EVOLVE))
		{
			rule();
			branches.add(currBranch);
		}
	}
	
	ArrayList<ArrayList<Token>> body() throws InvalidTokenException, UnexpectedTokenException
	{
		
		while(currToken.type==BasicTokenType.STATE || currToken.type==BasicTokenType.EVOLVE) defns();
		if(currToken.type!=BasicTokenType.EOF) throw new InvalidTokenException();
		return branches;
	}
}
