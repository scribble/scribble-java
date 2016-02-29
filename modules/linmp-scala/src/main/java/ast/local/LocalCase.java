package ast.local;

import ast.PayloadType;


public class LocalCase
{
	public final PayloadType pay;
	public final LocalType body;
	
	public LocalCase(PayloadType pay, LocalType body)
	{
		this.pay = pay;
		this.body = body;
	}
	
	@Override
	public String toString()
	{
		return "(" + ((this.pay == null) ? "" : this.pay) + "):" + this.body;
	}
}
