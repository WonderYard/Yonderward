package blocks;

import java.util.ArrayList;
import java.util.List;

public class Rules extends AST
{
	private List<Rule> rules = new ArrayList<Rule>();

	public void addRule(AST root)
	{
		if(root instanceof Rule) this.rules.add((Rule) root);
		else throw new RuntimeException();
	}

	@Override
	public String toString()
	{
		return String.format("{\"Rules\": {\"rules\": %s}}", rules);
	}
	
	public List<Rule> getRules()
	{
		return rules;
	}
}
