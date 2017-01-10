package automaton;

import java.util.ArrayList;

import lexicalpkg.Lexer.Token;

public class Builder 
{
	ArrayList<ArrayList<Token>> branches;
	
	public Builder(	ArrayList<ArrayList<Token>> branches)
	{
		this.branches= branches;
	}

}
