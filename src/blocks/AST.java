package blocks;

import java.util.ArrayList;
import java.util.List;

import lexicalpkg.Lexer.Token;

public class AST
{
	protected Token value;
	protected List<AST> children;
	
	public AST()
	{
		this.children = new ArrayList<AST>();
	}
	
	public AST(Token value)
	{
		this();
		this.value = value;
	}
	
	public void addChild(AST child)
	{
		this.children.add(child);
	}
	
	private String toString(String className)
	{
		return String.format("{\"%s\": {\"value\": %s, \"children\": %s}}", className, value, children);
	}
	
	@Override
	public String toString()
	{
		return toString(this.getClass().getSimpleName());
	}
}
