package blocks;

import java.util.Random;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class Term extends Expression
{
	private Term term;

	public Term() {}

	public Term(Token value)
	{
		super(value);
	}
	
	public void setExpression(AST root)
	{
		if(root instanceof Expression) this.term = (Term) root;
		else throw new RuntimeException();
	}

	@Override
	public String toString()
	{
		return String.format("{\"Term\": {\"term\": %s}}", value);
	}
	
	public boolean apply(World world, Point me)
	{
		if(term == null) {
			if(value.data.equals("true")) return true;
			if(value.data.equals("false")) return false;
			if(value.data.equals("guess")) return new Random().nextBoolean();
		}
		else if(value.data.equals("not"))
			return ! term.apply(world, me);
		throw new RuntimeException();
	}
}
