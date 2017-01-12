package blocks;

import lexicalpkg.Lexer.Token;

public class Term extends Expression
{
	public Term() {}

	public Term(Token value)
	{
		super(value);
	}

	@Override
	public String toString()
	{
		return String.format("{\"Term\": {\"term\": %s}}", value);
	}
}
