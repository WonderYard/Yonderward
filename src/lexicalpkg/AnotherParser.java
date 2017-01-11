package lexicalpkg;

import java.util.ArrayList;
import java.util.List;

import lexicalpkg.AST.NodeType;
import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class AnotherParser
{
	private Lexer lexer;
	
	public AnotherParser() throws InvalidTokenException
	{
		this.lexer = new Lexer();
	}
	
	public AST parse(String text) throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.init(text);
		return defns();
	}
	
	private AST defns() throws InvalidTokenException, UnexpectedTokenException
	{
		List<AST> defns = new ArrayList<AST>();
		do
		{
			defns.add(defn());
			lexer.expect(TokenType.SEMICOLON);
		}
		while(!lexer.on(TokenType.EOF));
		
		return new AST(NodeType.ROOT, null, defns);
	}
	
	private AST defn() throws UnexpectedTokenException, InvalidTokenException
	{	
		AST defn;
		
		if(lexer.on(TokenType.STATE))
		{
			defn = stateDefn();
		}
		else if(lexer.on(TokenType.NEIGHBORHOOD))
		{
			defn = nbhdDefn();
		}
		else if(lexer.on(TokenType.CLASS))
		{
			defn = classDefn();
		}
		else throw new UnexpectedTokenException(lexer, TokenType.STATE, TokenType.NEIGHBORHOOD, TokenType.CLASS);
		
		return defn;
	}
	
	private AST stateDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.STATE);
		
		Token stateID = lexer.expect(TokenType.IDENTIFIER);

		Token color = lexer.expect(TokenType.HEXNUMBER);
		
		
		List<AST> classes = new ArrayList<AST>();
		while(lexer.on(TokenType.IS))
		{
			classes.add(membershipDecl());
		}
		
		List<AST> rules = rules();
		
		AST state = new AST(NodeType.STATE_DEFN, stateID);
		state.addChild(new AST(NodeType.COLOR, color));
		state.addChild(new AST(NodeType.MEMBERSHIP_DECL, null, classes));
		state.addChild(new AST(NodeType.RULES, null, rules));
		return state;
	}
	
	private AST nbhdDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.NEIGHBORHOOD);
		Token nbhdID = lexer.expect(TokenType.IDENTIFIER);
		List<AST> nbhd = neighborhood();
		AST nbhdDefn = new AST(NodeType.NBHD_DEFN, nbhdID, nbhd);
		return nbhdDefn;
	}
	
	private AST classDefn() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.CLASS);
		Token classID = lexer.expect(TokenType.IDENTIFIER);
		
		List<AST> classes = new ArrayList<AST>();
		while(lexer.on(TokenType.IS))
		{
			classes.add(membershipDecl());
		}
		List<AST> rules = rules();
		
		AST classDefn = new AST(NodeType.CLASS_DEFN, classID);
		classDefn.addChild(new AST(NodeType.MEMBERSHIP_DECL, null, classes));
		classDefn.addChild(new AST(NodeType.RULES, null, rules));
		return classDefn;
	}
	
	private AST membershipDecl() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.IS);
		Token _class = lexer.expect(TokenType.IDENTIFIER);
		return new AST(NodeType.CLASS_REF, _class);
	}
	
	private List<AST> rules() throws InvalidTokenException, UnexpectedTokenException
	{
		List<AST> rules = new ArrayList<AST>();
		if(lexer.on(TokenType.TO))
		{
			rules.add(rule());
			while(lexer.on(TokenType.COMMA))
			{
				lexer.expect(TokenType.COMMA);
				rules.add(rule());
			}
		}
		return rules;
	}
	
	private AST rule() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.TO);
		AST evolveTo = stateRef();
		AST rule = new AST(NodeType.RULE, null);
		if(lexer.on(TokenType.WHEN))
		{
			lexer.expect(TokenType.WHEN);
			AST expr = expression();
			rule.addChild(expr);
		}
		rule.addChild(evolveTo);
		return rule;
	}
	
	private AST stateRef() throws InvalidTokenException, UnexpectedTokenException
	{
		AST stateRef;
		if(lexer.on(TokenType.IDENTIFIER))
		{
			Token evolveTo = lexer.expect(TokenType.IDENTIFIER);
			stateRef = new AST(NodeType.STATE_REF, evolveTo);
		}
		else if(lexer.on(TokenType.ARROWCHAIN))
		{
			Token chain = lexer.expect(TokenType.ARROWCHAIN);
			stateRef = new AST(NodeType.STATE_REF, chain);
		}
		else if(lexer.on(TokenType.ME))
		{
			Token me = lexer.expect(TokenType.ME);
			stateRef = new AST(NodeType.STATE_REF, me);
		}
		else throw new UnexpectedTokenException(lexer, TokenType.IDENTIFIER, TokenType.LEFTPS, TokenType.ME);
		return stateRef;
	}
	
	private List<AST> neighborhood() throws InvalidTokenException, UnexpectedTokenException
	{
		lexer.expect(TokenType.LEFTP);
		List<AST> chains = new ArrayList<AST>();
		while(lexer.on(TokenType.ARROWCHAIN)) {
			chains.add(new AST(NodeType.ARROW_CHAIN, lexer.expect(TokenType.ARROWCHAIN)));
		}
		lexer.expect(TokenType.RIGHTP);
		return chains;
		
	}
	
	private AST expression() throws InvalidTokenException, UnexpectedTokenException
	{
		AST expr = term();
		while(lexer.on(TokenType.BINARYOP))
		{
			Token binOp = lexer.expect(TokenType.BINARYOP);
			AST term2 = term();
			List<AST> oldExpr = new ArrayList<AST>();
			oldExpr.add(expr);
			expr = new AST(NodeType.BIN_OP, binOp, oldExpr);
			expr.addChild(term2);
		}
		return expr;
	}
	
	private AST term() throws UnexpectedTokenException, InvalidTokenException
	{
		if(lexer.on(TokenType.NUMBER))
		{
			return adjacencyPred();
		}
		else if(lexer.on(TokenType.LEFTP))
		{
			lexer.expect(TokenType.LEFTP);
			AST expr = expression();
			lexer.expect(TokenType.RIGHTP);
			return expr;
		}
		else if(lexer.on(TokenType.NOT))
		{
			lexer.expect(TokenType.NOT);
			AST term = term();
			AST not = new AST(NodeType.NOT, null);
			not.addChild(term);
			return not;
		}
		else if(lexer.on(TokenType.BOOL_CONST))
		{
			return new AST(NodeType.BOOL_CONST, lexer.expect(TokenType.BOOL_CONST));
		}
		else if(lexer.on(TokenType.IDENTIFIER) || lexer.on(TokenType.ARROWCHAIN))
		{
			return relationalPred();
		}
		else throw new UnexpectedTokenException(lexer, TokenType.NUMBER, TokenType.LEFTP, TokenType.NOT, TokenType.BOOL_CONST, TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.ARROWCHAIN);
	}

	private AST adjacencyPred() throws InvalidTokenException, UnexpectedTokenException
	{
		Token num = lexer.expect(TokenType.NUMBER);
		AST adjPred = new AST(NodeType.ADJ_PRED, num);
		
		if(lexer.on(TokenType.IN))
		{
			lexer.expect(TokenType.IN);
			AST in;
			List<AST> nbhd;
			if(lexer.on(TokenType.LEFTP))
			{
				nbhd = neighborhood();
				in = new AST(NodeType.IN_NBHD, null, nbhd);
			}
			else if(lexer.on(TokenType.IDENTIFIER))
			{
				Token nbhdID = lexer.expect(TokenType.IDENTIFIER);
				in = new AST(NodeType.IN_NBHD_ID, nbhdID);
			}
			else throw new UnexpectedTokenException(lexer, TokenType.LEFTP, TokenType.IDENTIFIER);

			adjPred.addChild(in);
		}
		if(lexer.on(TokenType.IDENTIFIER) || lexer.on(TokenType.ARROWCHAIN) || lexer.on(TokenType.ME))
		{
			AST stateRef = stateRef();
			adjPred.addChild(stateRef);
		}
		else if(lexer.on(TokenType.IS))
		{
			AST classRef = membershipDecl();
			adjPred.addChild(classRef);
		}
		else throw new UnexpectedTokenException(lexer, TokenType.IDENTIFIER, TokenType.ARROWCHAIN, TokenType.ME, TokenType.IS);
		return adjPred;
	}

	private AST relationalPred() throws InvalidTokenException, UnexpectedTokenException
	{
		AST stateRef = stateRef();
		if(lexer.on(TokenType.EQUAL))
		{
			lexer.expect(TokenType.EQUAL);
		}
		AST relPred = new AST(NodeType.REL_PRED, null);
		relPred.addChild(stateRef);
		if(lexer.on(TokenType.IDENTIFIER) || lexer.on(TokenType.ARROWCHAIN) || lexer.on(TokenType.ME))
		{
			AST stateRef2 = stateRef();
			relPred.addChild(stateRef2);
		}
		else if(lexer.on(TokenType.IS))
		{
			AST classRef = membershipDecl();
			relPred.addChild(classRef);
		}
		else throw new UnexpectedTokenException(lexer, TokenType.IDENTIFIER, TokenType.ARROWCHAIN, TokenType.ME, TokenType.IS);
		return relPred;
	}
}
