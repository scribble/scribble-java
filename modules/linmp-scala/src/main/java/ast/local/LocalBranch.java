package ast.local;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import ast.name.MessageLab;
import ast.name.Role;

public class LocalBranch implements LocalType
{
	//public final Role self;
	
	public final Role src;
	public final Map<MessageLab, LocalCase> cases;
	
	//public LocalBranch(Role self, Role src, Map<MessageLab, LocalCase> cases)
	public LocalBranch(Role src, Map<MessageLab, LocalCase> cases)
	{
		//this.self = self;
		this.src = src;
		this.cases = Collections.unmodifiableMap(cases);
	}
	
	// A ? { l1 : S1, l2 : S2, ... }
	@Override
	public String toString()
	{
		return this.src + "?{" +
				this.cases.entrySet().stream()
					.map((e) -> e.getKey().toString() + e.getValue().toString())
					.collect(Collectors.joining(", ")) + "}";
	}
}
