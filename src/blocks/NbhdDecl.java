package blocks;

public class NbhdDecl extends AST
{
	@Override
	public String toString()
	{
		return String.format("{\"NbhdDecl\": {\"nbhdDecl\": %s}}", value);
	}
}
