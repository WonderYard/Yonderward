package automaton;

import java.util.ArrayList;
import java.util.List;

public class State
{
	private String color;
	private List<Rule> rules;
	
	public State(String color) {
		this.color = color;
		rules = new ArrayList<Rule>();
	}
	
	public void addRule(StateRef evolveTo, List<Condition> conditions) {
		rules.add(new Rule(evolveTo, conditions));
	}
	
	public String getColor()
	{
		return color;
	}

	public List<Rule> getRules() {
		return rules;
	}

}
