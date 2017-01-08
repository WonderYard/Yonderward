package automaton;

import java.util.List;
import java.util.Map;

public class Rule
{
	private StateRef evolveTo;
	private List<Condition> conditions;

	public Rule(StateRef evolveTo, List<Condition> conditions) {
		this.evolveTo = evolveTo;
		this.conditions = conditions;
	}
	
	public StateRef apply(Map<Integer, Integer> neighbors) {
		for(int i = 0; i < conditions.size(); i++) {
			if(!conditions.get(i).check(neighbors)) return null;
		}
		return evolveTo;
	}
	
	@Override
	public String toString() {
		return String.format("Rule(evolveTo=%s, conditions=%s", evolveTo.getID(), conditions);
	}
}
