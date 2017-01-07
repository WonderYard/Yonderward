package automaton;

import java.util.List;
import java.util.Map;

public class Rule
{
	private int evolveTo;
	private List<Condition> conditions;

	public Rule() {
		
	}
	
	public int apply(Map<Integer, Integer> neighbors) {
		for(int i = 0; i < conditions.size(); i++) {
			if(!conditions.get(i).check(neighbors)) return -1;
		}
		return evolveTo;
	}
}
