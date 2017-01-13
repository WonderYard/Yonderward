package blocks;

import java.util.ArrayList;
import java.util.List;

import automaton.Point;
import automaton.World;
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
	
	public int count(World world, Ref ref, Point me)
	{
		int count = 0;
		for(ArrowChain chain : arrowChains)
		{
			Integer stateID = world.resolveArrowChain(chain.value.data, me);
			if(stateID == null) continue;

			if(ref instanceof StateRef) {
				StateRef stateRef = (StateRef) ref;
				if(stateRef.getID(world, me) == stateID) {
					count++;
				}
			}
			else if(ref instanceof ClassRef) {
				ClassRef classRef = (ClassRef) ref;
				if(classRef.getID(world, me) == stateID) {
					count++;
				}
			}
			else throw new RuntimeException();

		}
		return count;
	}
}
