package blocks;

import lexicalpkg.Lexer.Token;

public class NbhdID extends NbhdDecl
{
	public NbhdID(Token value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"NbhdID\": {\"nbhdID\": %s}}", value);
	}
	
	public int count(Ref ref)
	{
		return 0;
	}
}
