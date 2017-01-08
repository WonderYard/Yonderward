package lexicalpkg;

import lexicalpkg.Lexer.TokenType;

public class InvalidTokenException extends Exception 
{
	public InvalidTokenException() {};
	
	public InvalidTokenException(String message) {
		System.out.println(message);
	}

}
