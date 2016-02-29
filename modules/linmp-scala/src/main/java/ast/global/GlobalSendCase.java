package ast.global;

import ast.PayloadType;


public class GlobalSendCase
{
	public final PayloadType pay;
	public final GlobalType body;
	
	public GlobalSendCase(PayloadType pay, GlobalType body)
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
