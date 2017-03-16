package lexicalpkg;

import java.util.regex.Pattern;

//Contains all the type of tokens allowed by our grammar
public enum TokenType 
{
	NEWLINE("\\n",true),
	CELL("cell\\b"),
	CLASS("class\\b"),
	BECOME("become\\b"),
	REPR("repr\\b"),
	NEIGH("neigh\\b"),
	RULES("rules\\b"),
	WHEN("when\\b"),
	COORDINATE("[(]-?[0-9]+,-?[0-9]+[)]"),
	ARROWCHAIN("[<>^v]"),
	COLORS("(blue|white|black)\\b"),
	EXNUMBER("(#[0-9 a-f]?[0-9 a-f]?[0-9 a-f])\\b"),
	IN("in\\b"),
	NOT("not\\b"),
	BINARYOP("(xor|and|or)\\b"),
	BOOLEAN("(true|false|guess)\\b"),
	IDENTIFIER("([a-zA-Z][0-9a-zA-Z]+)\\b"),
	NUMBER("-?[0-9]+"),
	LEFTPS("\\{"),
	RIGHTPS("\\}"),
	LEFTP("\\("),
	RIGHTP("\\)"),
	POINTS(":"),
	WHITESPACE("\\s", true),
	SEMICOLON(";"),
	RELSYM("(=|<|>)"),
	EOF("$");
	
	public final Pattern pattern;
	boolean ignore;
	
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