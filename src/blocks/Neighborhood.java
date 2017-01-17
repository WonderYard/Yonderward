package blocks;

import java.util.ArrayList;
import java.util.List;

import automaton.Point;
import automaton.World;

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
	
	@Override
	public int countNeighbors(World world, Point me, Ref ref)
	{
		int count = 0;
		for(ArrowChain chain : arrowChains)
		{
			Integer stateRefID = world.resolveArrowChain(chain.value.data, me);
			if(stateRefID == null) continue;
			if(ref.refEqualToID(world, me, stateRefID)) count++;
		}
		return count;
	}
}
