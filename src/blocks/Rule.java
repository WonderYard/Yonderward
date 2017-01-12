package blocks;

import automaton.World;

public class Rule extends AST
{
	private StateRef stateRef;
	private Expression expression;

	public void setStateRef(AST root)
	{
		if(root instanceof StateRef) this.stateRef = (StateRef) root;
		else throw new RuntimeException();
	}
	
	public void setExpression(AST root)
	{
		if(root instanceof Expression) this.expression = (Expression) root;
		else throw new RuntimeException();
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"Rule\": {\"stateRef\": %s, \"expression\": %s}}", stateRef, expression);
	}

	public StateRef apply(World world)
	{
		// TODO apply rules! and classes rules!
		return stateRef;
	}
}
