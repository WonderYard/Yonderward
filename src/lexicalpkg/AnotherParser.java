package lexicalpkg;

import blocks.*;
import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class AnotherParser
{
	private Lexer lexer;
	private AST root;
	
	public AnotherParser() throws InvalidTokenException
	{
		this.lexer = new Lexer();
	}
	
	public AST parse(String text) throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.init(text);
		defns();
		return root;
	}
	
	private void defns() throws InvalidTokenException, UnexpectedTokenException
	{
		Defns defns = new Defns();
		do
		{
			defn();
			defns.addDefn(root);
			lexer.expect(TokenType.SEMICOLON);
		}
		while(!lexer.on(TokenType.EOF));
		root = defns.validate();
	}
	
	private void defn() throws UnexpectedTokenException, InvalidTokenException
	{	
		
		if(lexer.on(TokenType.STATE))
		{
			stateDefn();
		}
		else if(lexer.on(TokenType.NEIGHBORHOOD))
		{
			nbhdDefn();
		}
		else if(lexer.on(TokenType.CLASS))
		{
			classDefn();
		}
		else throw new UnexpectedTokenException(lexer, TokenType.STATE, TokenType.NEIGHBORHOOD, TokenType.CLASS);
	}
	
	private void stateDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.STATE);
		Token id = lexer.expect(TokenType.IDENTIFIER);
		Token hex = lexer.expect(TokenType.HEXNUMBER);
		StateDefn stateDefn = new StateDefn(id, hex);
		
		while(lexer.on(TokenType.IS))
		{
			membershipDecl();
			stateDefn.addClassRef(root);
		}
		
		rules();
		stateDefn.setRules(root);
		root = stateDefn;
	}
	
	private void nbhdDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.NEIGHBORHOOD);
		Token id = lexer.expect(TokenType.IDENTIFIER);
		neighborhood();
		NbhdDefn nbhdDefn = new NbhdDefn(id);
		nbhdDefn.setNeighborhood(root);
		root = nbhdDefn;
		
	}
	
	private void classDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.CLASS);
		Token id = lexer.expect(TokenType.IDENTIFIER);
		ClassDefn classDefn = new ClassDefn(id);
		while(lexer.on(TokenType.IS))
		{
			membershipDecl();
			classDefn.addClassRef(root);
		}
		rules();
		classDefn.setRules(root);
		root = classDefn;
	}
	
	private void membershipDecl() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.IS);
		Token id = lexer.expect(TokenType.IDENTIFIER);
		root = new ClassRef(id);
	}
	
	private void rules() throws InvalidTokenException, UnexpectedTokenException
	{
		Rules rules = new Rules();
		if(lexer.on(TokenType.TO))
		{
			rule();
			rules.addRule(root);
			while(lexer.on(TokenType.COMMA))
			{
				lexer.expect(TokenType.COMMA);
				rule();
				rules.addRule(root);
			}
		}
		root = rules;
	}
	
	private void rule() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.TO);
		stateRef();
		Rule rule = new Rule();
		rule.setStateRef(root);
		if(lexer.on(TokenType.WHEN))
		{
			lexer.expect(TokenType.WHEN);
			expression();
			rule.setExpression(root);
			
		}
		root = rule;
	}
	
	private void stateRef() throws InvalidTokenException, UnexpectedTokenException
	{
		if(lexer.on(TokenType.IDENTIFIER))
		{
			Token id = lexer.expect(TokenType.IDENTIFIER);
			StateRef stateRef = new StateRef(id);
			root = stateRef;
		}
		else if(lexer.on(TokenType.ARROWCHAIN))
		{
			Token chain = lexer.expect(TokenType.ARROWCHAIN);
			StateRef stateRef = new StateRef(chain);
			root = stateRef;
		}
		else if(lexer.on(TokenType.ME))
		{
			Token me = lexer.expect(TokenType.ME);
			StateRef stateRef = new StateRef(me);
			root = stateRef;
		}
		else throw new UnexpectedTokenException(lexer, TokenType.IDENTIFIER, TokenType.LEFTPS, TokenType.ME);
	}
	
	private void neighborhood() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.LEFTP);
		Neighborhood nbhd = new Neighborhood();
		while(lexer.on(TokenType.ARROWCHAIN))
		{
			Token t = lexer.expect(TokenType.ARROWCHAIN);
			ArrowChain chain = new ArrowChain(t);
			nbhd.addArrowChain(chain);
		}
		lexer.expect(TokenType.RIGHTP);
		root = nbhd;
	}
	
	private void expression() throws InvalidTokenException, UnexpectedTokenException
	{
		term();
		while(lexer.on(TokenType.BINARYOP))
		{
			Token binOp = lexer.expect(TokenType.BINARYOP);
			BoolOp boolOp = new BoolOp(binOp);
			boolOp.setFirstTerm(root);
			term();
			boolOp.setSecondTerm(root);
			root = boolOp;
		}
	}
	
	private void term() throws UnexpectedTokenException, InvalidTokenException
	{
		if(lexer.on(TokenType.NUMBER))
		{
			adjacencyPred();
		}
		else if(lexer.on(TokenType.LEFTP))
		{
			lexer.expect(TokenType.LEFTP);
			expression();
			lexer.expect(TokenType.RIGHTP);
		}
		else if(lexer.on(TokenType.NOT))
		{
			Token not = lexer.expect(TokenType.NOT);
			term();
			Term term = new Term(not);
			term.setExpression(root);
			root = term;
		}
		else if(lexer.on(TokenType.BOOL_CONST))
		{
			Token bool = lexer.expect(TokenType.BOOL_CONST);
			root = new Term(bool);
		}
		else if(lexer.on(TokenType.IDENTIFIER) || lexer.on(TokenType.ARROWCHAIN))
		{
			relationalPred();
		}
		else throw new UnexpectedTokenException(lexer, TokenType.NUMBER, TokenType.LEFTP, TokenType.NOT, TokenType.BOOL_CONST, TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.ARROWCHAIN);
	}

	private void adjacencyPred() throws InvalidTokenException, UnexpectedTokenException
	{
		Token num = lexer.expect(TokenType.NUMBER);
		AdjacencyPred adjacencyPred = new AdjacencyPred(num);
		if(lexer.on(TokenType.IN))
		{
			lexer.expect(TokenType.IN);

			if(lexer.on(TokenType.LEFTP))
			{
				neighborhood();
				adjacencyPred.setNbhdDecl(root);
			}
			else if(lexer.on(TokenType.IDENTIFIER))
			{
				Token id = lexer.expect(TokenType.IDENTIFIER);
				NbhdID nbhdID = new NbhdID(id);
				adjacencyPred.setNbhdDecl(nbhdID);
			}
			else throw new UnexpectedTokenException(lexer, TokenType.LEFTP, TokenType.IDENTIFIER);

		}
		if(lexer.on(TokenType.IDENTIFIER) || lexer.on(TokenType.ARROWCHAIN) || lexer.on(TokenType.ME))
		{
			stateRef();
			adjacencyPred.setRef(root);

		}
		else if(lexer.on(TokenType.IS))
		{
			membershipDecl();
			adjacencyPred.setRef(root);

		}
		else throw new UnexpectedTokenException(lexer, TokenType.IDENTIFIER, TokenType.ARROWCHAIN, TokenType.ME, TokenType.IS);
		root = adjacencyPred;
	}

	private void relationalPred() throws InvalidTokenException, UnexpectedTokenException
	{
		stateRef();
		RelationalPred relationalPred = new RelationalPred();
		relationalPred.addStateRef(root);
		if(lexer.on(TokenType.EQUAL))
		{
			lexer.expect(TokenType.EQUAL);
		}

		if(lexer.on(TokenType.IDENTIFIER) || lexer.on(TokenType.ARROWCHAIN) || lexer.on(TokenType.ME))
		{
			stateRef();
			relationalPred.addRef(root);

		}
		else if(lexer.on(TokenType.IS))
		{
			membershipDecl();
			relationalPred.addRef(root);

		}
		else throw new UnexpectedTokenException(lexer, TokenType.IDENTIFIER, TokenType.ARROWCHAIN, TokenType.ME, TokenType.IS);
		root = relationalPred;
	}
}
