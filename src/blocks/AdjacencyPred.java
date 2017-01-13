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
		if(nbhdDecl == null) {
			return Integer.parseInt(value.data) <= world.neighborhood.count(world, ref, me);
		}
		if(nbhdDecl instanceof Neighborhood) {
			Neighborhood neighborhood = (Neighborhood) nbhdDecl;
			return Integer.parseInt(value.data) <= neighborhood.count(world, ref, me);
		}
		if(nbhdDecl instanceof NbhdID)
		{
			NbhdID nbhdID = (NbhdID) nbhdDecl;
			return Integer.parseInt(value.data) <= world.nbhdMap.get(nbhdID.value.data).neighborhood.count(world, ref, me);
		}
		throw new RuntimeException();
	}
}
