package ast.global;


public class GlobalSendCase
{
	public final String data;
	public final GlobalType body;
	
	public GlobalSendCase(String data, GlobalType body)
	{
		this.data = data;
		this.body = body;
	}
	
	@Override
	public String toString()
	{
		return "(" + this.data + "):" + this.body;
	}
}
