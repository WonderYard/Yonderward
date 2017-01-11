package lexicalpkg;

import java.util.ArrayList;
import java.util.List;

import automaton.Condition;
import automaton.State;
import automaton.StateRef;
import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class Parser 
{
	//token returned by the lexer
	Token currToken;
	Lexer l;
	
	List<State> states = new ArrayList<>();
	List<String> names = new ArrayList<>();
	
	
	public Parser (Lexer l) throws InvalidTokenException
	{
		this.l=l;
	}

	Token stateID() throws InvalidTokenException, UnexpectedTokenException
	{
		Token t = currToken;
		l.expect(TokenType.IDENTIFIER);
		return t;
	}
	void nbhdID() throws InvalidTokenException, UnexpectedTokenException
	{
		l.expect(TokenType.IDENTIFIER);
	}
	
	Token stateRef() throws InvalidTokenException, UnexpectedTokenException
	{
		Token t = currToken;
		if(l.accept(TokenType.COORDINATE));
		else l.expect(TokenType.IDENTIFIER);
		return t;
	}
	
	void coordinates() throws InvalidTokenException, UnexpectedTokenException
	{
		l.expect(TokenType.COORDINATE);
		while (l.accept(TokenType.COORDINATE));
		
	}
	
	void neighbourhood() throws InvalidTokenException, UnexpectedTokenException
	{
		if(l.accept(TokenType.COORDINATE));
		else l.expect(TokenType.IDENTIFIER);
		
	}
	
	Condition adjacencyPred() throws InvalidTokenException, UnexpectedTokenException, UndeclaredStateException
	{
		StateRef cell = new StateRef(stateRef(), names);
		Token t =  currToken;
		l.expect(TokenType.NUMBER);
		int min = Integer.parseInt(t.data);
		Token u = currToken;
		int max = min;
		if(l.accept(TokenType.NUMBER)) max = Integer.parseInt(u.data);
		
		if(l.accept(TokenType.IN)) {
			Token p = currToken;
			neighbourhood();
			return new Condition(cell, min, max, new StateRef[]{new StateRef(p, names)});
		}
		
		return new Condition(cell, min, max, new StateRef[]{});
	}
	
	//Not in our grammar atm
	void relationalPred() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		l.expect(TokenType.EQUAL);
		stateRef();
	}
	
	Condition term() throws InvalidTokenException, UnexpectedTokenException, UndeclaredStateException
	{
		if(l.accept(TokenType.LEFTP))
		{
			expression();
			l.expect(TokenType.RIGHTP);
		}
		else if(l.accept(TokenType.NOT)) term();
		return adjacencyPred();
	}
	Condition expression() throws InvalidTokenException, UnexpectedTokenException, UndeclaredStateException
	{
		return term();
		// while(accept(TokenType.BINARYOP)) term();
	}
	
	void rule() throws InvalidTokenException, UnexpectedTokenException, UndeclaredStateException
	{
		String data = stateID().data;
		int stateIndex = names.indexOf(data);
		if(stateIndex == -1) throw new UndeclaredStateException(data);
		l.expect(TokenType.TO);
		StateRef evolveTo = new StateRef(stateRef(), names);
		List<Condition> conditions = new ArrayList<Condition>();
		
		while(l.accept(TokenType.WHEN)) conditions.add(expression());
		// if(accept(TokenType.IN)) neighbourhood();
		states.get(stateIndex).addRule(evolveTo, conditions);

	}
	
	void stateDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		Token name = stateID();
		names.add(name.data);
		Token color = currToken;
		l.expect(TokenType.HEXNUMBER);

		states.add(new State(color.data));

	}
	
	void defn() throws InvalidTokenException, UnexpectedTokenException, UndeclaredStateException
	{
		if(l.accept(TokenType.STATE)) stateDefn();
		if(l.accept(TokenType.EVOLVE)) rule();
	}
	
	public List<State> body() throws InvalidTokenException, UnexpectedTokenException, UndeclaredStateException
	{
		while(currToken.type==TokenType.STATE || currToken.type==TokenType.EVOLVE)
		{
			defn();
			// l.expect(TokenType.SEMICOLON);
		}
		//if(currToken.type != TokenType.EOF) throw new InvalidTokenException();
		l.expect(TokenType.EOF);
		
		return states;
	}
}
