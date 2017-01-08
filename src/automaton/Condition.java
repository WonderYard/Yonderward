package automaton;

import java.util.Map;

public class Condition
{
	private StateRef cell;
	private int min;
	private int max;
	private StateRef[] neighbors;

	public Condition(StateRef cell, int min, int max, StateRef[] neighbors) {
		this.cell = cell;
		this.min = min;
		this.max = max;
		this.neighbors = neighbors;
	}

	public boolean check(Map<Integer, Integer> neighbors, World world, Point self) {
		int count;
		if(this.neighbors.length == 0)
			count = neighbors.getOrDefault(cell.getID(), 0);
		else {
			count = getCount(this.neighbors, cell.getID(), world, self);
		};
		return count >= min && count <= max;
	}
	
	private int getCount(StateRef[] neighbors, int id, World world, Point self)
	{
		int count = 0;
		for(StateRef sr : neighbors)
		{
			Point p = sr.getPoint();
			StateRef newsr = new StateRef(new Point(Math.abs((p.x + self.x)%world.getSize().x), Math.abs((p.y + self.y)%world.getSize().y)));
			count += newsr.getID(world) == id ? 1 : 0;
		}
		return count;
	}
	
	@Override
	public String toString() {
		return String.format("Condition(cell=%s, (>%s, <%s))", cell.getID(), min, max);
	}

}
