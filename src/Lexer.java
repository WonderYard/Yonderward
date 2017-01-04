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
	//Contains all the type of tokens allowed by our grammar
	public static enum TokenType 
	{
		NUMBER("-?[0-9]+"),
		BINARYOP("xor|and|or"),
		EVOLVE("evolve"), 
		STATE("state"),
		TO("to"),
		WHEN("when"),
		COMMA(","),
		EQUAL("="),
		IN("in"),
		IDENTIFIER("[a-zA-Z][0-9a-zA-Z]+"),
		LEFTP("\\("),
		RIGHTP("\\)"),
		LEFTPS("\\["),
		RIGHTPS("\\]"),
		BOOLEAN("true|false|guess"),
		COORDINATE("(-?[0-9]+,-?[0-9]+)"),
		EXANUMBER("#[0-9a-fA-F]+");
		  
		public final String pattern;
		
		private TokenType(String pattern) 
		{
			this.pattern = pattern;
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
	
	public static Token lex(String token) throws InvalidTokenException 
	{

	    // Lexer logic begins here
	    for (TokenType tokenType : TokenType.values())
	    {
	    	//System.out.println(tokenType.pattern);
	    	Pattern p=Pattern.compile(tokenType.pattern);
			Matcher m=p.matcher(token);
			if(m.matches())
			{
				return new Token(tokenType,token);
			};
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
		String[] tokens = getCodeFromFile("gol.txt").replace("\n", " ").replace("\t", " ").split(" +");
		for (String s: tokens)
		{
			System.out.println(lex(s));
		}
	}
}
