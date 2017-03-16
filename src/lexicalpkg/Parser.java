package lexicalpkg;

import java.util.ArrayList;
import java.util.Arrays;
public class Parser 
{

	Lexer l;
	ArrayList<TokenType> attributes;
	
	public Parser (Lexer l) throws InvalidTokenException
	{
		this.l=l;
	}


	void neighDefn() throws InvalidTokenException
	{
		l.expect(TokenType.POINTS);
		l.expect(TokenType.COORDINATE,TokenType.ARROWCHAIN);
		if(l.get().type==TokenType.COORDINATE) while(l.search(TokenType.COORDINATE));
		if(l.get().type==TokenType.ARROWCHAIN) while(l.search(TokenType.ARROWCHAIN));
		l.expect(TokenType.SEMICOLON);
	}
	
	void rulesDefn() throws InvalidTokenException
	{
		l.expect(TokenType.POINTS);

		while(l.search(TokenType.BECOME))
		{
			l.expect(TokenType.IDENTIFIER);
			l.expect(TokenType.WHEN);
			do
			{
				l.expect(TokenType.IDENTIFIER);
				l.expect(TokenType.RELSYM);
				l.expect(TokenType.NUMBER);
				if(l.search(TokenType.IN))
				{
					if(!l.search(TokenType.ARROWCHAIN)) l.expect(TokenType.COORDINATE);
				}
			}while(l.search(TokenType.BINARYOP));
			l.expect(TokenType.SEMICOLON);
		}
		

	}
	
	void reprDefn() throws InvalidTokenException
	{
		l.expect(TokenType.POINTS);
		if (!l.search(TokenType.COLORS)) l.expect(TokenType.EXNUMBER);
		l.expect(TokenType.SEMICOLON);
	}
	
	void bodyDefn() throws InvalidTokenException
	{
		while(attributes.size()>0 && !l.search(TokenType.RIGHTPS))
		{
			l.expect(attributes);
			{
				if(l.get().type==TokenType.RULES) 
				{
					attributes.remove(TokenType.RULES);
					rulesDefn();
					
				}
				else if (l.get().type==TokenType.NEIGH)
				{
					attributes.remove(TokenType.NEIGH);
					neighDefn();

				}
			}
		}
		if(attributes.size()==0) l.expect(TokenType.RIGHTPS);
	}
	
	
	
	void cellDefn() throws InvalidTokenException
	{
		l.expect(TokenType.IDENTIFIER);
		l.expect(TokenType.LEFTPS);
		l.expect(TokenType.REPR);
		reprDefn();
		bodyDefn();

	}
	
	
	
	void classDefn() throws InvalidTokenException
	{
		l.expect(TokenType.IDENTIFIER);
		l.expect(TokenType.LEFTPS);
		bodyDefn();
	}
	
	
	
	void parse() throws InvalidTokenException, InvalidTokenException
	{
		while(l.search(TokenType.CELL,TokenType.CLASS))
		{
			attributes= new ArrayList<TokenType>(Arrays.asList(TokenType.RULES,TokenType.NEIGH));
			if(l.get().type==TokenType.CELL) cellDefn();
			else if(l.get().type==TokenType.CLASS) classDefn();
		}
		l.expect(TokenType.EOF);
	}
	
}
