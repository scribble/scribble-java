package test.test4.sig;

import test.test4.Test4.Proto1.Proto1;

public class Foo extends Test4Message
{
	private static final long serialVersionUID = 1L;

	public Foo(String body)
	{
		super(Proto1.Foo, body);
	}

	@Override
	public String getBody()
	{
		return (String) this.payload[0];
	}
}
