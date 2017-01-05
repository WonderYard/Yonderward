package lexicalpkg;

public class ParserTester 
{
	public static void main (String[] args) throws InvalidTokenException, UnexpectedTokenException
	{
		Lexer l = new Lexer("YonderWard/gol.txt");
		Parser p = new Parser(l);
		p.body();
	}
}
