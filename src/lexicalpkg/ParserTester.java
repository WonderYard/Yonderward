package lexicalpkg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserTester 
{
	public static void main (String[] args) throws InvalidTokenException, UnexpectedTokenException, UndeclaredStateException
	{
		String slash=File.separator;
		AnotherParser parser = new AnotherParser();
		String code = getCodeFromFile(slash+"test"+slash+"gol.txt");
		AST ast = parser.parse(code);
		System.out.println(ast);
	}
	
	public static String getCodeFromFile(String filename)
	{
		StringBuilder str = new StringBuilder();
		try {
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/"+filename)).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str.toString();
	}
}