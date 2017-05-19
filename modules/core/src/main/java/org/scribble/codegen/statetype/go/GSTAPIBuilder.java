package org.scribble.codegen.statetype.go;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.main.Job;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class GSTAPIBuilder extends STAPIBuilder
{
	private int counter = 1;
	
	public GSTAPIBuilder(Job job, GProtocolName gpn, Role role, EGraph graph)
	{
		super(job, gpn, role, graph,
				new GSTOutputStateBuilder(new GSTSendActionBuilder()),
				new GSTReceiveStateBuilder(new GSTReceiveActionBuilder()),
				new GSTEndStateBuilder());
	}

	@Override
	public String getPackage()
	{
		return this.gpn.getSimpleName().toString();
	}
	
	@Override
	protected String makeSTStateName(EState s)
	{
		return s.isTerminal() ? "EndSocket" : this.gpn.getSimpleName() + "_" + role + "_" + this.counter++;
	}

	@Override
	public String getFilePath(EState s)
	{
		return this.gpn.toString().replaceAll("\\.", "/") + "/" + getSTStateName(s) + ".go";
	}
	
	@Override
	public Map<String, String> buildSessionAPI()
	{
		Map<String, String> res = new HashMap<>();
		List<Role> roles = this.job.getContext().getModule(this.gpn.getPrefix()).getProtocolDecl(this.gpn.getSimpleName()).header.roledecls.getRoles();

		// endpoints
		String endpoints =
				  "package " + getPackage() + "\n"
				+ roles.stream().map(r -> 
						  "type endpoint" + r + " struct {\n"
						+ roles.stream().filter(rr -> !rr.equals(r)).map(rr -> rr + " chan T").collect(Collectors.joining("\n")) + "\n"
						+ "}\n"
						+ "\n"
						+ "func (ep endpoint" + r + ") Close() error {\n"
						+ roles.stream().filter(rr -> rr.toString().compareTo(r.toString()) > 0).map(rr -> "close(ep" + "." + rr + ")").collect(Collectors.joining("\n")) + "\n"
						+ "return nil\n"
						+ "}\n"
						+ "\n"
						+ "var role" + r + " endpoint" + r
				).collect(Collectors.joining("\n\n"));
		res.put(this.gpn.toString().replaceAll("\\.", "/") + "/endpoints.go", endpoints);

		// roles
		for (Role r : roles)
		{
			String init = this.gpn.getSimpleName() + "_" + r + "_" + 1;  // FIXME: factor out naming scheme
			String role =
					  "package " + getPackage() + "\n"
					+ "\n"
					+ "import \"io\"\n"
					+ "\n"
					+ "func New" + r + "("
					+ roles.stream().filter(rr -> !rr.equals(r)).map(rr -> rr + " chan T").collect(Collectors.joining(", "))
					+ ") (" + init + ", io.Closer) {\n"
					+ "role" + r + " = endpoint" + r + "{\n"
					+ roles.stream().filter(rr -> !rr.equals(r)).map(rr -> rr + ": " + rr).collect(Collectors.joining(",\n")) + ",\n"
					+ "}\n"
					+ "return " + init + "{}, role" + r + "\n"
					+ "}";
			res.put(this.gpn.toString().replaceAll("\\.", "/") + "/role_" + r + ".go", role);
		}

		// labels
		String labels = 
				  "package " + getPackage() + "\n";
		res.put(this.gpn.toString().replaceAll("\\.", "/") + "/types.go", labels);

		// types
		String types =
				  "package " + getPackage() + "\n"
				+ "\n"
				+ "type T interface {}";
		res.put(this.gpn.toString().replaceAll("\\.", "/") + "/types.go", types);
		
		return res;
	}
}
