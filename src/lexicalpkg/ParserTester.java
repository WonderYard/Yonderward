package lexicalpkg;

import java.io.File;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class ParserTester 
{

	public static void main (String[] args) throws InvalidTokenException, UnexpectedTokenException
	{
		/*
		Lexer l = new Lexer("\\test\\neon.txt");
		Token t=l.lex();
		while(t.type!=TokenType.EOF)
		{
			System.out.println(t);
			t=l.lex();
		}
		*/
		String slash=File.separator;
		Lexer l = new Lexer(slash+"test"+slash+"gol.txt");
		Parser p = new Parser(l);
		p.body();
		System.out.println("fatto1");
		l = new Lexer(slash+"test"+slash+"neon.txt");
		p = new Parser(l);
		p.body();
		System.out.println("fatto2");
		l = new Lexer(slash+"test"+slash+"wireworld.txt");
		p = new Parser(l);
		p.body();
		System.out.println("fatto3");
		
		
	}
}
