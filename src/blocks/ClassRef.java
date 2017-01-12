package blocks;

import lexicalpkg.Lexer.Token;

public class ClassRef extends Ref
{
	public ClassRef(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"ClassRef\": {\"classRef\": %s}}", value);
	}
}
