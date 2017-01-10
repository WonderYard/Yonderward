package lexicalpkg;

import java.util.regex.Pattern;

/**
 * Enum doesn't support inheritance, but this pattern can solve the problem 
 * @author francesco
 *
 */
public interface TokenType
{
	public String getName();
	public Pattern getPattern();
	public boolean isIgnored();
	
	/**
	 * These tokens are used by the lexer
	 * @author francesco
	 *
	 */
	public static enum BasicTokenType implements TokenType
	{
		WHITESPACE("\\s+", true),
		NUMBER("-?[0-9]+"),
		COORDINATE("(-?[0-9]+,-?[0-9]+)"),
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
		LEFTP("\\("),
		RIGHTP("\\)"),
		LEFTPS("\\["),
		RIGHTPS("\\]"),
		BOOLEAN("(true|false|guess)\\b"),
		
		EXANUMBER("(#[0-9a-fA-F]+)\\b"),
		
		EOF("x\\by");
		
		public final Pattern pattern;
		private boolean ignore;
		
		private BasicTokenType(String pattern) 
		{
			this.pattern = Pattern.compile(pattern);
		}
		
		private BasicTokenType(String pattern, boolean ignore)
		{
			this(pattern);
			this.ignore = ignore;
		}

		@Override
		public String getName(){return name();}

		@Override
		public Pattern getPattern() {return pattern;}

		@Override
		public boolean isIgnored() {return ignore;}
	}
	
	
	
	/**
	 * These tokens are used by the parser
	 * @author francesco
	 *
	 */	
	public static enum RefinedTokenType implements TokenType
	{
		PROVA("x\\by");
		
		public final Pattern pattern;
		
		@Override
		public String getName(){return name();}

		@Override
		public Pattern getPattern() {return pattern;}
		
		private RefinedTokenType(String pattern) {this.pattern = Pattern.compile(pattern);}

		@Override
		public boolean isIgnored() {return false;}
	
	}
}
