package lexicalpkg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;


public class Lexer
{

	private Token currToken;
	private String text;
	private int index;
	private int line;
	
	public Lexer(String text) throws InvalidTokenException
	{
		this.text = text;
		line=1;
		index=0;
	}
	
	public Token get()
	{
		return currToken; 
	}
	
	public boolean search(TokenType... l) 
	{
		//System.out.println(currToken);
		l=Arrays.copyOf(l, l.length+2);
		l[l.length-2]=TokenType.NEWLINE;
		l[l.length-1]=TokenType.WHITESPACE;
		
		for (TokenType tokenType : l)
	    {
			Matcher match = tokenType.pattern.matcher(text);
			match.region(index, text.length());
			if(match.lookingAt())
			{
				String group = match.group();
				index += group.length();
				if(tokenType.ignore)
				{
					if(tokenType==TokenType.NEWLINE){line++;}
					return search(l);
				}
				currToken= new Token(tokenType, group);
				return true;
			}
	    }
		return false;
	}
	
	
	public void expect(TokenType... l) throws InvalidTokenException
	{
		int errLine=line; 
		if(!search(l)) throw new InvalidTokenException("Syntax error at line: "+errLine +" expected: "+Arrays.toString(l));
	}
		
	
	public void expect(ArrayList<TokenType> l) throws InvalidTokenException{ expect(l.toArray(new TokenType[l.size()])); }
	public boolean search() { return search(TokenType.values()); }
	public boolean search(ArrayList<TokenType> l) { return search(l.toArray(new TokenType[l.size()])); }
	


}
