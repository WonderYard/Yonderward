package lexicalpkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lexicalpkg.Lexer.Token;

public class AST
{
	public enum NodeType
	{
		STATE_DEFN,
		NBHD_DEFN,
		CLASS_DEFN,
		MEMBERSHIP_DECL,
		CLASS_REF,
		RULES,
		COLOR,
		ARROW_CHAIN,
		RULE,
		STATE_REF,
		BIN_OP,
		NOT,
		BOOL_CONST,
		IN_NBHD, IN_NBHD_ID,
		ADJ_PRED,
		REL_PRED,
		ROOT;
	}

	private NodeType type;
	private Token value;
	private List<AST> children;
	
	public AST(NodeType type, Token value)
	{
		this.type = type;
		this.value = value;
		this.children = new ArrayList<>();
	}
	
	public AST(NodeType type, Token value, List<AST> children)
	{
		this(type, value);
		this.children.addAll(children);
	}
	
	public void addChild(AST child)
	{
		this.children.add(child);
	}
	
	@Override
	public String toString()
	{
		return String.format("{\"%s\": {\"value\": %s, \"children\": %s}}", type, value, children);
	}
}
