package lexicalpkg;

//Allows to create Tokens with associated data
public class Token 
{
	protected TokenType type;
	protected String data;
	protected int line;

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
