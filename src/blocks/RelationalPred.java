package blocks;

import automaton.Point;
import automaton.World;
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
	
	public boolean apply(World world, Point me)
	{
		Integer stateRefID = stateRef.getID(world, me);
		if(stateRefID == null) return false;
		if(ref instanceof StateRef) {
			return stateRefID == ((StateRef) ref).getID(world, me);
		}
		else if(ref instanceof ClassRef) {
			for(ClassRef classRef : ((StateDefn) world.stateDefns.get(stateRefID)).classRefs)
			{
				if(classRef.value.data.equals(((ClassRef) ref).value.data)) return true;
			}
			return false;
		}
		throw new RuntimeException();
	}
}
