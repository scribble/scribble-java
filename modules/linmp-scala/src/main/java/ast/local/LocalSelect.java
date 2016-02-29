package ast.local;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import ast.name.MessageLab;
import ast.name.Role;

public class LocalSelect implements LocalType
{
	//public final Role self;

	public final Role dest;
	public final Map<MessageLab, LocalCase> cases;
	
	//public LocalSelect(Role self, Role dest, Map<MessageLab, LocalCase> cases)
	public LocalSelect(Role dest, Map<MessageLab, LocalCase> cases)
	{
		//this.self = self;
		this.dest = dest;
		this.cases = Collections.unmodifiableMap(cases);
	}
	
	// A ! { l1 : S1, l2 : S2, ... }
	@Override
	public String toString()
	{
		return this.dest + "!{" +
				this.cases.entrySet().stream()
					.map((e) -> e.getKey().toString() + e.getValue().toString())
					.collect(Collectors.joining(", ")) + "}";
	}
}
