package test.test4.sig;

import org.scribble.runtime.message.ScribMessage;
import org.scribble.type.name.Op;

public abstract class Test4Message extends ScribMessage
{
	private static final long serialVersionUID = 1L;
	
	/*public Test4Message(Op op)
	{
		super(op);
	}*/

	public Test4Message(Op op, Object body)
	{
		super(op, body);
	}
	
	public abstract Object getBody();

	public byte[] toBytes()
	{
		// op is 3-char header (Foo/Bar)
		byte[] bs = (this.op.toString() + getBody() + "\n").getBytes(Test4Formatter.cs);
		return bs;
	}
	
	@Override
	public String toString()
	{
		return new String(toBytes());
	}
}
