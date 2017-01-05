package lexicalpkg;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class ParserTester 
{
	public static void main (String[] args) throws InvalidTokenException, UnexpectedTokenException
	{
		Lexer l = new Lexer("\\test\\gol.txt");
		Token t = l.lex();
		while(t.type!=TokenType.EOF)
		{
			System.out.println(t);
			t=l.lex();
		}
		Parser p = new Parser(l);
		p.body();
		//l = new Lexer("YonderWard/neon.txt");
		//p = new Parser(l);
		//p.body();
		//l = new Lexer("YonderWard/wireworld.txt");
		//p = new Parser(l);
		//p.body();
		//l = new Lexer("YonderWard/rules.txt");
		//p = new Parser(l);
		//p.body();
	}
}
