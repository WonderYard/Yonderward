package automaton;

import java.util.Map;

public class Condition
{
	private StateRef cell;
	private int min;
	private int max;

	public Condition(StateRef cell, int min, int max) {
		this.cell = cell;
		this.min = min;
		this.max = max;
	}

	public boolean check(Map<Integer, Integer> neighbors) {
		int count = neighbors.getOrDefault(cell.getID(), 0);
		return count >= min && count <= max;
	}
	
	@Override
	public String toString() {
		return String.format("Condition(cell=%s, (>%s, <%s))", cell.getID(), min, max);
	}

}
