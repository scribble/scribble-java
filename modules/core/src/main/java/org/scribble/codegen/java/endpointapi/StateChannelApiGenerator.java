package org.scribble.codegen.java.endpointapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Job;
import org.scribble.visit.Projector;

// TODO: "wildcard" unary async: op doesn't matter -- for branch-receive op "still needed" to cast to correct branch state
// TODO: "functional state interfaces", e.g. for smtp ehlo and quit actions

// FIXME: selector(?) hanging on runtimeexception (from message formatter)
// FIXME: consume futures before wrap/reconnect
public class StateChannelApiGenerator extends ApiGenerator
{
	public static final String SCRIBMESSAGE_CLASS = "org.scribble.net.ScribMessage";
	public static final String SCRIBBLERUNTIMEEXCEPTION_CLASS = "org.scribble.main.ScribbleRuntimeException";
	public static final String RECEIVE_OP_PARAM = "op";
	public static final String SCRIBMESSAGE_OP_FIELD = "op";

	private final Role self;
	private final LProtocolName lpn;
	private final EndpointState init;
	//private final String root;

	private int counter = 1;
	private Map<EndpointState, String> classNames = new HashMap<>();  // Doesn't include terminal states

	private Map<String, TypeBuilder> types = new HashMap<>();  // class/iface name key

	public StateChannelApiGenerator(Job job, GProtocolName fullname, Role self)
	{
		super(job, fullname);
		this.self = self;
		this.lpn = Projector.projectFullProtocolName(fullname, self);
		this.init = job.getContext().getEndpointGraph(fullname, self).init;
		generateClassNames(this.init);
		//this.root = this.classNames.get(this.init);
		constructClasses(this.init);

		EndpointState term = EndpointState.findTerminalState(new HashSet<>(), this.init);
		if (term != null)
		{
			ClassBuilder cb = new EndSocketGenerator(this, term).generateType();
			this.types.put(cb.getName(), cb);
		}
	}
	
	// Return: key (package and Java class file path) -> val (Java class source) 
	@Override
	public Map<String, String> generateApi()
	{
		Map<String, String> map = new HashMap<String, String>();
		// FIXME: factor out with ScribSocketBuilder.getPackageName
		String prefix = SessionApiGenerator.getEndpointApiRootPackageName(this.gpn).replace('.', '/') + "/channels/" + this.self + "/" ;
		for (String s : this.types.keySet())
		{
			String path = prefix + s + ".java";
			map.put(path, this.types.get(s).build());
		}
		return map;
	}
	
	private void generateClassNames(EndpointState ps)
	{
		if (this.classNames.containsKey(ps))
		{
			return;
		}
		if (ps.isTerminal())
		{
			//this.classNames.put(ps, ENDSOCKET_CLASS);  // FIXME: add generic parameters?  or don't record?
			return;
		}
		this.classNames.put(ps, newSocketClassName());
		for (EndpointState succ : ps.getSuccessors())
		{
			generateClassNames(succ);
		}
	}
	
	private String newSocketClassName()
	{
		return this.lpn.getSimpleName().toString() +  "_" + this.counter++;
	}

	private void constructClasses(EndpointState curr)
	{
		if (curr.isTerminal())
		{
			return;  // Generic EndSocket for terminal states
		}
		String className = this.classNames.get(curr);
		if (this.types.containsKey(className))
		{
			return;
		}
		this.types.put(className, constructClass(curr));
		for (EndpointState succ : curr.getSuccessors())
		{
			constructClasses(succ);
		}

		// Depends on the above being done first (for this.root)
		/*String init = this.lpn.getSimpleName().toString() + "_" + 0;  // FIXME: factor out with newClassName
		this.classes.put(init, constructInitClass(init));*/
	}
	
	// Pre: curr is not terminal state
	private ClassBuilder constructClass(EndpointState curr)
	{
		Set<IOAction> as = curr.getAcceptable();
		IOAction a = as.iterator().next();
		if (a instanceof Send)
		{
			return new SendSocketGenerator(this, curr).generateType();
		}
		else if (a instanceof Receive)
		{
			return (as.size() > 1)
					? new BranchSocketGenerator(this, curr).generateType()
					: new ReceiveSocketGenerator(this, curr).generateType();
		}
		else
		{
			throw new RuntimeException("TODO: " + curr);
		}
	}
	
	public GProtocolName getGProtocolName()
	{
		return this.gpn;
	}
	
	public Role getSelf()
	{
		return this.self;
	}
	
	protected EndpointState getInitialState()
	{
		return this.init;
	}
	
	protected Module getMainModule()
	{
		return this.job.getContext().getMainModule();
	}
	
	protected void addTypeDecl(TypeBuilder tb)
	{
		this.types.put(tb.getName(), tb);
	}

	public String getSocketClassName(EndpointState s)
	{
		return this.classNames.get(s);
	}
	
	public TypeBuilder getType(String key)
	{
		return this.types.get(key);
	}
}
