package automaton;

import java.util.ArrayList;
import java.util.List;

public class State
{
	String color;
	List<Rule> rules;
	
	public State(String color) {
		this.color = color;
		rules = new ArrayList<Rule>();
	}
	
	public void addRule(int evolveTo, Condition conditions) {
		
	}

}
