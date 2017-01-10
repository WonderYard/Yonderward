package lexicalpkg;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lexicalpkg.TokenType.BasicTokenType;

public class Lexer
{
	//Contains all the type of tokens allowed by our grammar
	
	
	//Allows to create Tokens with associated data
	public static class Token 
	{
		public TokenType type;
		public String data;

		public Token(TokenType type, String data)
		{
			this.type = type;
			this.data = data;
		}
		
		//Utility for printing
		@Override
		public String toString() 
		{
			return String.format("(%s %s)", type.getName(), data);
		}
	}
	
	private String text;
	private int index;
	
	public Lexer(String filename) throws InvalidTokenException
	{
		String code = getCodeFromFile(filename);
		this.text = code;
	}
	
	public Token lex() throws InvalidTokenException 
	{
	   if(index == text.length()) return new Token(BasicTokenType.EOF, "EOF");
		
		for (TokenType tokenType : BasicTokenType.values())
	    {
			Matcher match = tokenType.getPattern().matcher(text);
			match.region(index, text.length());
			if(match.lookingAt())
			{
				String group = match.group();
				index += group.length();
				if(tokenType.isIgnored()) return lex();
				return new Token(tokenType, group);
			}
	    }
	    throw new InvalidTokenException();
	}
	

	private static String getCodeFromFile(String filename)
	{
		StringBuilder str = new StringBuilder();
		try {
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/"+filename)).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str.toString();
	}

}
