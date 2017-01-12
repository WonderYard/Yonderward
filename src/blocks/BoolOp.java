package blocks;

import lexicalpkg.Lexer.Token;

public class BoolOp extends Expression
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

}
