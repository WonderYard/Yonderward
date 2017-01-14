package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class NbhdID extends NbhdDecl
{
	public NbhdID(Token value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"NbhdID\": {\"nbhdID\": %s}}", value);
	}

	@Override
	public int countNeighbors(World world, Point me, Ref ref)
	{
		return world.nbhdMap.get(value.data).neighborhood.countNeighbors(world, me, ref);
	}
}
