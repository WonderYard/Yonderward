package lexicalpkg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
	public static enum TokenType 
	{
		NEWLINE("\n", true),
		WHITESPACE("\\s+", true),
		COMMENT_MULTI("\\/\\*[\\s\\S]*?\\*\\/", true),

		NUMBER("-?[0-9]+"),
		BINARYOP("(xor|and|or)\\b"),
		EVOLVE("evolve\\b"), 
		STATE("state\\b"),
		NEIGHBORHOOD("neighborhood\\b"),
		CLASS("class\\b"),
		IS("is\\b"),
		TO("to\\b"),
		ME("me\\b"),
		WHEN("when\\b"),
		COMMA(","),
		SEMICOLON(";"),
		EQUAL("="),
		IN("in\\b"),
		NOT("not\\b"),
		BOOL_CONST("(true|false|guess)\\b"),
		ARROWCHAIN("[\\^<v>]+"),
		IDENTIFIER("([a-zA-Z][0-9a-zA-Z]*)\\b"),
		LEFTP("\\("),
		RIGHTP("\\)"),
		LEFTPS("\\["),
		RIGHTPS("\\]"),
		//HEXNUMBER("\".\""),
		HEXNUMBER("#(?:[0-9a-fA-F]{3}){1,2}\\b"),
		
		EOF("$");
		
		public final Pattern pattern;
		private boolean ignore;
		
		private TokenType(String pattern) 
		{
			this.pattern = Pattern.compile(pattern);
		}
		
		private TokenType(String pattern, boolean ignore)
		{
			this(pattern);
			this.ignore = ignore;
		}
	}

	public static class Token 
	{
		public TokenType type;
		public String data;

		public Token(TokenType type, String data)
		{
			this.type = type;
			this.data = data;
//			System.out.println(this);
		}

		@Override
		public String toString() 
		{
			if(type.equals(TokenType.HEXNUMBER)) return data.replace("\"", "");
			return String.format("{\"%s\": \"%s\"}", type.name(), data);
		}
	}
	
	private String text;
	private Token currToken;
	private int index;
	private int lineNumberBuffer, lineNumber, colNumber;
	private int colNumberBuffer;
	
	public void init(String text) throws InvalidTokenException
	{
		this.text = text;
		this.index = 0;
		this.lineNumberBuffer = 1;
		this.lineNumber = 0;
		this.colNumber = 0;
		nextToken();
	}
	
	private Token nextToken() throws InvalidTokenException 
	{	
		for (TokenType tokenType : TokenType.values())
	    {
			Matcher match = tokenType.pattern.matcher(text);
			match.region(index, text.length());
			if(match.lookingAt())
			{
				String group = match.group();
				index += group.length();
				colNumberBuffer += group.length();
				if(tokenType.ignore)
				{
					if(tokenType.equals(TokenType.NEWLINE))
					{
						lineNumberBuffer++;
						colNumberBuffer = 1;
					}
					else if(tokenType.equals(TokenType.COMMENT_MULTI))
					{
						lineNumberBuffer += group.split("\n").length - 1;
					}
					return nextToken();
				}
				return currToken = new Token(tokenType, group);
			}
	    }
	    throw new InvalidTokenException();
	}

	public boolean on(TokenType... types)
	{
		for(TokenType type : types)
		{
			if(currToken.type.equals(type)) return true;
		}
		return false;
	}
	
	Token expect(TokenType type) throws InvalidTokenException, UnexpectedTokenException
	{
		if(on(type))
		{
			lineNumber = lineNumberBuffer;
			colNumber = colNumberBuffer;
			
			Token t = currToken;
			nextToken();
			return t;
		}
		throw new UnexpectedTokenException(this, type);
	}

	public int lineNumber()
	{
		return lineNumber;
	}
	
	public int colNumber()
	{
		return colNumber;
	}
	
	public Token currToken()
	{
		return currToken;
	}
}
