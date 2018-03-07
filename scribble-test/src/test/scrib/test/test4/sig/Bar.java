package test.test4.sig;

import test.test4.Test4.Proto1.Proto1;

public class Bar extends Test4Message
{
	private static final long serialVersionUID = 1L;

	public Bar(int body)
	{
		super(Proto1.Bar, body);
	}

	@Override
	public Integer getBody()
	{
		return (Integer) this.payload[0];
	}
}
