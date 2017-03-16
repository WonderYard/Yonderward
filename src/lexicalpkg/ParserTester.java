package lexicalpkg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ParserTester 
{
	private static String getCodeFromFile(String filename)
	{
		StringBuilder str = new StringBuilder();
		try {
			Files.newBufferedReader(Paths.get(filename)).lines().forEach(l -> {
				str.append(l+"\n");
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str.toString();
	}
	
	public static void main (String[] args) throws InvalidTokenException
	{
		String slash=File.separator;

		

		Lexer l =new Lexer(getCodeFromFile("test"+slash+"gol1.txt"));
		Parser p = new Parser(l);
		p.parse();
		System.out.println("OKGOL1");
		
		l = new Lexer(getCodeFromFile("test"+slash+"water.txt"));
		p = new Parser(l);
		p.parse();
		System.out.println("OKWATER");	
		
		l = new Lexer(getCodeFromFile("test"+slash+"strange.txt"));
		p = new Parser(l);
		p.parse();
		System.out.println("OKSTRANGE");	
	}
}
