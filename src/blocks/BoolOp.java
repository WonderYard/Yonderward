package blocks;

import automaton.Point;
import automaton.World;
import lexicalpkg.Lexer.Token;

public class BoolOp extends Term
{
	private Expression firstTerm;
	private Expression secondTerm;

	public BoolOp(Token value)
	{
		super(value);
	}

	public void setFirstTerm(AST root)
	{
		if(root instanceof Expression) this.firstTerm = (Expression) root;
		else throw new RuntimeException();
	}

	public void setSecondTerm(AST root)
	{
		if(root instanceof Expression) this.secondTerm = (Expression) root;
		else throw new RuntimeException();
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"BoolOp\": {\"firstTerm\": %s, \"boolOp\": %s, \"secondTerm\": %s}}", firstTerm, value, secondTerm);
	}
	
	public boolean apply(World world, Point me)
	{
		boolean res1 = firstTerm.apply(world, me);
		boolean res2 = secondTerm.apply(world, me);
		
		if(value.data.equals("and")) return res1 && res2;
		else if(value.data.equals("or")) return res1 || res2;
		else if(value.data.equals("xor")) return res1 ^ res2;
		else throw new RuntimeException();
	}

}
