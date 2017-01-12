package blocks;

import lexicalpkg.Lexer.Token;

public class NbhdDefn extends Defn
{
	private Neighborhood neighborhood;
	
	public NbhdDefn(Token value)
	{
		super(value);
	}
	
	public void setNeighborhood(AST root)
	{
		if(root instanceof Neighborhood) this.neighborhood = (Neighborhood) root;
		else throw new RuntimeException();
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"NbhdDefn\": {\"nbhdID\": %s, \"neighborhood\": %s}}", value, neighborhood);
	}
}
