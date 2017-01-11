package lexicalpkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class UnexpectedTokenException extends Exception
{
	private static final long serialVersionUID = 1L;

	public UnexpectedTokenException(Lexer lexer, TokenType... types)
	{
		String[] names = new String[types.length];
		for(int i = 0; i < types.length; i++)
		{
			names[i] = types[i].name();
		}
		System.out.println(String.format("Line %s, Column %s: %s expected but %s found.", lexer.lineNumber(), lexer.colNumber(), String.join(" or ", names), lexer.currToken()));
	}

}
