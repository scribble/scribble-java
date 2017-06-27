package org.scribble.ext.go.codegen.statetype.go;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class GSTSessionAPIBuilder
{
	private GSTStateChanAPIBuilder api;
	
	public GSTSessionAPIBuilder(GSTStateChanAPIBuilder api)
	{
		this.api = api;
	}

	//@Override
	public Map<String, String> buildSessionAPI()  // FIXME: factor out
	{
		Module mod = api.job.getContext().getModule(api.gpn.getPrefix());
		GProtocolName simpname = api.gpn.getSimpleName();
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(simpname);
		/*MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		try
		{
			gpd.accept(coll);
		}
		catch (ScribbleException e)
		{
			throw new RuntimeScribbleException(e);
		}*/

		List<Role> roles = gpd.header.roledecls.getRoles();
		//Set<MessageId<?>> mids = coll.getNames();
		Set<EState> instates = new HashSet<>();
		Predicate<EState> f = (s) -> s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT;
		if (f.test(api.graph.init))
		{
			instates.add(api.graph.init);
		}
		instates.addAll(MState.getReachableStates(api.graph.init).stream().filter(f).collect(Collectors.toSet()));
	
		Map<String, String> res = new HashMap<>();
		String dir = api.gpn.toString().replaceAll("\\.", "/") + "/";

		/*// endpoints
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
		res.put(dir + "endpoints.go", endpoints);*/

		// roles
		String sessclass =
					"package " + api.getPackage() + "\n"  // FIXME: factor out
				+ "\n"
				+ "import \"org/scribble/runtime/net\"\n"
				+ "\n"
				+ roles.stream().map(r -> 
				  	  "type _" + r + " struct { }\n"
				  	+ "\n"
				  	+ "func (*_" + r +") GetRoleName() string {\n"
				  	+ "return \"" + r + "\"\n"
				  	+ "}\n"
				  	+ "\n"
				  	+ "var __" + r + " *_" + r + "\n"
				  	+ "\n"
				  	+ "func new" + r + "() *_" + r + " {\n"  // FIXME: not concurrent
				  	+ "if __" + r + " == nil {\n"
				  	+ "__"+ r + " = &_" + r + "{}\n"
				  	+ "}\n"
				  	+ "return __" + r + "\n"
				  	+ "}"
				  ).collect(Collectors.joining("\n\n")) + "\n"
				+ "\n"
				+ "type " + simpname + " struct {\n"
				+ roles.stream().map(r -> r + " *_" + r).collect(Collectors.joining("\n")) + "\n"  // FIXME: role constants should be pointers?
				+ "}\n"
				+ "\n" 
				+ "func New" + simpname + "() *" + simpname + "{\n"
				+ "return &" + simpname + "{ " + roles.stream().map(r -> "new" + r + "()").collect(Collectors.joining(", ")) + " }\n"
				+ "}";
		
		// Protocol and role specific endpoints
		sessclass +=
				roles.stream().map(r ->
						  "\n\n"
						+ "type _Endpoint" + simpname + "_" + r + " struct {\n"  // FIXME: factor out
						+ r + " *net.MPSTEndpoint\n"  // FIXME: factor out
						+ "}\n"
						+ "\n"
						+ "func NewEndpoint" + simpname + "_" + r + "(P *" + simpname + ") *_Endpoint" + simpname + "_" + r + "{\n"  // FIXME: factor out
						+ "return &_Endpoint" + simpname + "_" + r + " { " + r + ": net.NewMPSTEndpoint(P, P." + r + ") }\n"  // FIXME: factor out
						+ "}"
				).collect(Collectors.joining(""));
		
		res.put(dir + simpname + ".go", sessclass);
		/*for (Role r : roles)
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
			res.put(dir + "role_" + r + ".go", role);
		}*/

		// labels
		/*String labels = 
					"package " + api.getPackage();  // FIXME: factor out
		for (EState s : instates)
		{
			String ename = GSTBranchStateBuilder.getBranchEnumType(this.api, s);
			List<EAction> as = s.getActions();
			labels +=
				  "\n\ntype " + ename + " int\n"
				+ "\n"
				+ "const (\n"
				+ GSTBranchStateBuilder.getBranchEnumValue(as.get(0).mid) + " " + ename + " = iota \n"
				+ as.subList(1, as.size()).stream().map(a -> GSTBranchStateBuilder.getBranchEnumValue(a.mid)).collect(Collectors.joining("\n")) + "\n"
				+ ")";
		}*/
		/*String labels = 
				  "package " + getPackage() + "\n";
		/*for (MessageId<?> mid : mids)
		{
			labels +=
					  "\ntype op" + mid + " string\n"
					+ "\n"
					+ "const " + mid + " op" + mid + " = \"" + mid + "\"\n";
		}*/
		//res.put(dir + "labels_" + api.role + ".go", labels);

		/*// types
		String types =
				  "package " + getPackage() + "\n"
				+ "\n"
				+ "type T interface {}";
		res.put(dir + "types.go", types);*/
		
		return res;
	}
}
