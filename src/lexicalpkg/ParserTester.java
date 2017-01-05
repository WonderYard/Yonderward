package lexicalpkg;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class ParserTester 
{
	public static void main (String[] args) throws InvalidTokenException, UnexpectedTokenException
	{
		
		Lexer l = new Lexer("\\test\\neon.txt");
		Token t=l.lex();
		while(t.type!=TokenType.EOF)
		{
			System.out.println(t);
			t=l.lex();
		}
		
		/*
		Lexer l = new Lexer("\\test\\gol.txt");
		Parser p = new Parser(l);
		p.body();
		System.out.println("fatto1");
		l = new Lexer("\\test\\neon.txt");
		p = new Parser(l);
		p.body();
		System.out.println("fatto2");
		l = new Lexer("\\test\\rules.txt");
		p = new Parser(l);
		p.body();
		System.out.println("fatto3");
		l = new Lexer("\\test\\wireworld.txt");
		p = new Parser(l);
		p.body();
		System.out.println("fatto4");
		*/
	}
}
