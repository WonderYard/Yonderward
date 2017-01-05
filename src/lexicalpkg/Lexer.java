import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer
{
	//?
	//Contains all the type of tokens allowed by our grammar
	public static enum TokenType 
	{
		NUMBER("-?[0-9]+"),
		BINARYOP("(xor|and|or)\\b"),
		EVOLVE("evolve\\b"), 
		STATE("state\\b"),
		TO("to\\b"),
		WHEN("when\\b"),
		COMMA(","),
		EQUAL("="),
		IN("in"),
		IDENTIFIER("([a-zA-Z][0-9a-zA-Z]+)\\b"),
		LEFTP("\\("),
		RIGHTP("\\)"),
		LEFTPS("\\["),
		RIGHTPS("\\]"),
		BOOLEAN("(true|false|guess)\\b"),
		COORDINATE("(-?[0-9]+,-?[0-9]+)"),
		EXANUMBER("(#[0-9a-fA-F]+)\\b");
		  
		public final Pattern pattern;
		
		private TokenType(String pattern) 
		{
			this.pattern = Pattern.compile("^" + pattern);
		}
	}
	
	public static enum IgnoreTokenType
	{
		WHITESPACE("\\s+");
		
		public final Pattern pattern;
		
		IgnoreTokenType(String pattern)
		{
			this.pattern = Pattern.compile("^" + pattern);
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
		}
		
		//Utility for printing
		@Override
		public String toString() 
		{
			return String.format("(%s %s)", type.name(), data);
		}
	}
	
	private String text;
	
	public Lexer(String text)
	{
		this.text = text;
	}
	
	public Token lex() throws InvalidTokenException 
	{
	   if(text.length() == 0) return new Token(TokenType.IDENTIFIER, "EOF");
		
	    for(IgnoreTokenType ignore : IgnoreTokenType.values())
	    {
	    	Matcher match = ignore.pattern.matcher(text);
			if(match.find()) {
				text = text.substring(match.end(), text.length());
				return lex();
			}
	    }
		
		for (TokenType tokenType : TokenType.values())
	    {
			Matcher match = tokenType.pattern.matcher(text);
			if(match.find())
			{
				text = text.substring(match.end(), text.length());
				return new Token(tokenType, match.group());
			}
	    }
	    throw new InvalidTokenException();
	}
	
	//This is just to make the Lexer class stand-alone, it will be deleted in the final version
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
	//To try this class
	public static void main (String[] args) throws InvalidTokenException
	{
		String code = getCodeFromFile("test/rules.txt");
		Lexer lexer = new Lexer(code);
		
		Token token;
		do {
			token = lexer.lex();
			System.out.println(token);
		}
		while(token.data != "EOF");
	}
}
