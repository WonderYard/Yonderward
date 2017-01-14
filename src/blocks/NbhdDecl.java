package blocks;

import automaton.Point;
import automaton.World;

public abstract class NbhdDecl extends AST
{
	@Override
	public String toString()
	{
		return String.format("{\"NbhdDecl\": {\"nbhdDecl\": %s}}", value);
	}
	
	public abstract int countNeighbors(World world, Point me, Ref ref);
}
