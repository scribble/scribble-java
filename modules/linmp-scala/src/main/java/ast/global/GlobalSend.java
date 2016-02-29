package ast.global;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import ast.name.MessageLab;
import ast.name.Role;

public class GlobalSend implements GlobalType
{
	public final Role src;
	public final Role dest;
	public final Map<MessageLab, GlobalSendCase> cases;
	
	public GlobalSend(Role src, Role dest, Map<MessageLab, GlobalSendCase> cases)
	{
		this.src = src;
		this.dest = dest;
		this.cases = Collections.unmodifiableMap(cases);
	}
	
	@Override
	public String toString()
	{
		return this.src + "->" + this.dest + ":{" +
				this.cases.entrySet().stream()
					.map((e) -> e.getKey().toString() + e.getValue().toString())
					.collect(Collectors.joining(", ")) + "}";
	}
}
