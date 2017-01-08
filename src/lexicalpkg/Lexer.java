package lexicalpkg;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
	//Contains all the type of tokens allowed by our grammar
	public static enum TokenType 
	{
		WHITESPACE("\\s+", true),
		NUMBER("-?[0-9]+"),
		BINARYOP("(xor|and|or)\\b"),
		EVOLVE("evolve\\b"), 
		STATE("state\\b"),
		TO("to\\b"),
		WHEN("when\\b"),
		COMMA(","),
		SEMICOLON(";"),
		EQUAL("="),
		IN("in\\b"),
		NOT("not\\b"),
		IDENTIFIER("([a-zA-Z][0-9a-zA-Z]+)\\b"),
		COORDINATE("\\(-?[0-9]+,-?[0-9]+\\)"),
		LEFTP("\\("),
		RIGHTP("\\)"),
		LEFTPS("\\["),
		RIGHTPS("\\]"),
		BOOLEAN("(true|false|guess)\\b"),
		EXANUMBER("(#[0-9a-fA-F]+)\\b"),
		
		EOF("x\\by");
		
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
	
	//Allows to create Tokens with associated data
	public static class Token 
	{
		public TokenType type;
		public String data;

		public Token(TokenType type, String data)
		{
			this.type = type;
			this.data = data;
			//System.out.println(this);
		}
		
		//Utility for printing
		@Override
		public String toString() 
		{
			return String.format("(%s %s)", type.name(), data);
		}
	}
	
	private String text;
	private int index;
	
	public Lexer(String code) throws InvalidTokenException
	{
		// String code = getCodeFromFile(filename);
		this.text = code;
	}
	
	public Token lex() throws InvalidTokenException 
	{
	   if(index == text.length()) return new Token(TokenType.EOF, "EOF");
		
		for (TokenType tokenType : TokenType.values())
	    {
			Matcher match = tokenType.pattern.matcher(text);
			match.region(index, text.length());
			if(match.lookingAt())
			{
				String group = match.group();
				index += group.length();
				if(tokenType.ignore) return lex();
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
