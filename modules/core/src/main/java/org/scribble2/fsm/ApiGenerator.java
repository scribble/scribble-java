package org.scribble2.fsm;

import java.util.HashMap;
import java.util.Map;

import org.scribble2.model.visit.Job;
import org.scribble2.sesstype.name.LProtocolName;

public class ApiGenerator
{
	private final Job job;
	private final LProtocolName lpn;

	private int counter = 0;
	Map<ProtocolState, String> classNames = new HashMap<>();
	private Map<String, String> classes = new HashMap<>();

	public ApiGenerator(Job job, LProtocolName lpn)
	{
		this.job = job;
		this.lpn = lpn;

		ProtocolState init = job.getContext().getFsm(lpn).init;
		generateClassNames(init);
		for (ProtocolState ps : classNames.keySet())
		{
			generateClass(ps);
		}
	}
	
	public Map<String, String> getClasses()
	{
		return this.classes;
	}

	private void generateClass(ProtocolState ps)
	{
		String className = this.classNames.get(ps);
		String clazz = "";
		clazz += "package foo;\n";
		clazz += "\n";
		clazz += "public class " + className + " " + getSuperClass(ps) + " {\n";
		clazz += "\n";
		for (IOAction a : ps.getAcceptable())
		{
			// Scribble ensures all a are input or all are output
			clazz += generateMethod(a, ps.accept(a));
			clazz += "\n";
		}
		clazz += "}";
		this.classes.put(className, clazz);
	}
	
	private String generateMethod(IOAction a, ProtocolState succ)
	{
		String method = "\tpublic ";
		if (a instanceof Send)
		{
			method += this.classNames.get(succ) + " send(org.scribble2.sesstype.name.Role role, org.scribble2.sesstype.name.Op op) {\n";
			method += "\t\tsuper.send(role, op);\n";
		}
		else if (a instanceof Receive)
		{
			method += this.classNames.get(succ) + " receive(org.scribble2.sesstype.name.Role role) {\n";
			method += "\t\treturn super.receive(role);\n";
		}
		else
		{
			throw new RuntimeException("TODO: " + a);
		}
		method += "}\n";
		return method;
	}
	
	private String getSuperClass(ProtocolState ps)
	{
		if (ps.isTerminal())
		{
			return "org.scribble2.net.EndSocket";
		}
		IOAction a = ps.getAcceptable().iterator().next();
		if (a instanceof Send)
		{
			return "org.scribble2.net.SendSocket";
		}
		else if (a instanceof Receive)
		{
			return "org.scribble2.net.ReceiveSocket";
		}
		else
		{
			throw new RuntimeException("TODO");
		}
	}
	
	private void generateClassNames(ProtocolState ps)
	{
		if (this.classNames.containsKey(ps))
		{
			return;
		}
		this.classNames.put(ps, newClassName(ps));
		for (ProtocolState succ : ps.getSuccessors())
		{
			generateClassNames(succ);
		}
	}
	
	private String newClassName(ProtocolState ps)
	{
		String sn = this.lpn.getSimpleName().toString();
		return sn + "Socket" + nextCount();
	}
	
	private int nextCount()
	{
		return this.counter++;
	}
}
