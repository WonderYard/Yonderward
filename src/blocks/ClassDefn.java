package blocks;

import lexicalpkg.Lexer.Token;

public class ClassDefn extends RulerDefn
{

	public ClassDefn(Token value)
	{
		super(value);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"ClassDefn\": {\"classID\": %s, \"classRefs\": %s, \"rules\": %s}}", value, classRefs, rules);
	}
}
