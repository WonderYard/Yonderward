package blocks;

import java.util.List;

import lexicalpkg.Lexer.Token;

public class ClassDefn extends Defn
{
	private List<ClassRef> classRefs;
	private Rules rules;

	public ClassDefn(Token value)
	{
		super(value);
	}

	public void addClassRef(AST root)
	{
		if(root instanceof ClassRef) this.classRefs.add((ClassRef) root);
		else throw new RuntimeException();
	}
	
	public void setRules(AST root)
	{
		if(root instanceof Rules) this.rules = (Rules) root;
		else throw new RuntimeException();
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"ClassDefn\": {\"classID\": %s, \"classRefs\": %s, \"rules\": %s}}", value, classRefs, rules);
	}
}
