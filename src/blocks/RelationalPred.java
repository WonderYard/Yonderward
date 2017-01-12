package blocks;

import lexicalpkg.Lexer.Token;

public class RelationalPred extends Term
{
	public RelationalPred() {}

	public RelationalPred(Token value)
	{
		super(value);
	}

	private StateRef stateRef;
	private Ref ref;
	
	public void addStateRef(AST root)
	{		
		if(root instanceof StateRef) this.stateRef = (StateRef) root;
		else throw new RuntimeException();
	}

	public void addRef(AST root)
	{
		if(root instanceof Ref) this.ref = (Ref) root;
		else throw new RuntimeException();
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"RelationalPred\": {\"stateRef\": %s, \"ref\": %s}}", stateRef, ref);
	}
}
