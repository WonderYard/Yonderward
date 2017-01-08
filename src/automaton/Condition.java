package automaton;
// ola
import java.util.Map;

public class Condition
{
	private int cell;
	private int min;
	private int max;

	public Condition(int cell, int min, int max) {
		this.cell = cell;
		this.min = min;
		this.max = max;
	}

	public boolean check(Map<Integer, Integer> neighbors) {
		int count = neighbors.getOrDefault(cell, 0);
		return count >= min && count <= max;
	}

}
