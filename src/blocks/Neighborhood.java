package blocks;

import java.util.ArrayList;
import java.util.List;

import lexicalpkg.Lexer.Token;

public class Neighborhood extends NbhdDecl
{
	private List<ArrowChain> arrowChains = new ArrayList<ArrowChain>();

	public void addArrowChain(ArrowChain chain)
	{
		this.arrowChains.add(chain);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"Neighborhood\": {\"arrowChains\": %s}}", arrowChains);
	}
}
