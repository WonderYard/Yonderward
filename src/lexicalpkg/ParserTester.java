package lexicalpkg;

import java.io.File;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;
import java.util.ArrayList;

public class ParserTester 
{
	public static void stampaLista(ArrayList<ArrayList<Token>> a )
	{
		for(ArrayList<Token> t : a)
		{
			System.out.println(t);
		}
		System.out.println();
	}

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
		stampaLista(p.body());
		

		l = new Lexer(slash+"test"+slash+"neon.txt");
		p = new Parser(l);
		stampaLista(p.body());
		

		l = new Lexer(slash+"test"+slash+"wireworld.txt");
		p = new Parser(l);
		stampaLista(p.body());

	}
}
