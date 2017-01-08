package automaton;

import lexicalpkg.InvalidTokenException;
import lexicalpkg.UndeclaredStateException;

import java.util.List;

import lexicalpkg.Lexer.Token;
import lexicalpkg.Lexer.TokenType;

public class StateRef
{
	private int stateID;
	private Point point;
	private RefType refType;
	
	public enum RefType
	{
		ID, REL_POINT;
	}

	public StateRef(Token token, List<String> names) throws UndeclaredStateException, InvalidTokenException
	{
		if(token.type == TokenType.IDENTIFIER)
		{
			int index = names.indexOf(token.data);
			if(index == -1) throw new UndeclaredStateException(token.data);
			stateID = index;
			this.refType = RefType.ID;
		}
		else if(token.type == TokenType.COORDINATE)
		{
			String[] point = token.data.substring(1, token.data.length() - 1).split(",");
			this.point = new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1]));
			this.refType = RefType.REL_POINT;
		}
		else {
			throw new InvalidTokenException(token.toString());
		}
	}

	public StateRef(int cell) {
		this.stateID = cell;
		this.refType = RefType.ID;
	}

	public int getID(World world) {
		if(this.refType == RefType.ID) return stateID;
		return world.getCell(point);
	}
	
	public int getID() {
		if(this.refType == RefType.ID) return stateID;
		return -1;
	}
}
