package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class AdjacencyPred extends Term
{
	private NbhdDecl nbhdDecl;
	private Ref ref;

	public AdjacencyPred(Token value)
	{
		super(value);
	}

	public void setNbhdDecl(AST root)
	{
		if(root instanceof NbhdDecl) this.nbhdDecl = (NbhdDecl) root;
		else throw new RuntimeException();
	}

	public void setRef(AST root)
	{
		if(root instanceof Ref) this.ref = (Ref) root;
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"AdjacencyPred\": {\"number\": %s, \"nbhdDecl\": %s, \"ref\": %s}}", value, nbhdDecl, ref);
	}

	public boolean apply(World world, Point me)
	{
		if(nbhdDecl == null)
			return Integer.parseInt(value.data) <= world.neighborhood.countNeighbors(world, me, ref);
		
		return Integer.parseInt(value.data) <= nbhdDecl.countNeighbors(world, me, ref);
	}
}
