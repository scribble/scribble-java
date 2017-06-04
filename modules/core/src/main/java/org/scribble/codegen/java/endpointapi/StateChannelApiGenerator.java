/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.codegen.java.endpointapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.Module;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.Projector;

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
	private final EState init;
	//private final String root;

	protected final boolean skipIOInterfacesGeneration;

	private int counter = 1;
	private Map<EState, String> classNames = new HashMap<>();  // Doesn't include terminal states

	private Map<String, TypeBuilder> types = new HashMap<>();  // class/iface name key

	public StateChannelApiGenerator(Job job, GProtocolName fullname, Role self) throws ScribbleException  // FIXME: APIGenerationException?
	{
		super(job, fullname);

		this.self = self;
		this.lpn = Projector.projectFullProtocolName(fullname, self);
		//this.init = job.getContext().getEndpointGraph(fullname, self).init;
		JobContext jc = job.getContext();
		this.init = job.minEfsm ? jc.getMinimisedEGraph(fullname, self).init : jc.getEGraph(fullname, self).init;
		
		this.skipIOInterfacesGeneration = skipIOInterfacesGeneration(this.init);
			
		generateClassNames(this.init);
		//this.root = this.classNames.get(this.init);
		constructClasses(this.init);

		//EndpointState term = EndpointState.findTerminalState(new HashSet<>(), this.init);
		EState term = EState.getTerminal(this.init);
		if (term != null)
		{
			ClassBuilder cb = new EndSocketGenerator(this, term).generateType();
			this.types.put(cb.getName(), cb);
		}
	}

	// Cf. IOInterfacesGenerator constructor
	private static boolean skipIOInterfacesGeneration(EState init)
	{
		Set<EAction> as = EState.getReachableActions(init);
		if (as.stream().anyMatch((a) -> !a.isSend() && !a.isReceive()))  // HACK FIXME (connect/disconnect)
		{
			return true;
		}
		return false;
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
	
	private void generateClassNames(EState ps)
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
		for (EState succ : ps.getAllSuccessors())
		{
			generateClassNames(succ);
		}
	}
	
	private String newSocketClassName()
	{
		return this.lpn.getSimpleName().toString() +  "_" + this.counter++;
	}

	private void constructClasses(EState curr) throws ScribbleException
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
		for (EState succ : curr.getAllSuccessors())
		{
			constructClasses(succ);
		}

		// Depends on the above being done first (for this.root)
		/*String init = this.lpn.getSimpleName().toString() + "_" + 0;  // FIXME: factor out with newClassName
		this.classes.put(init, constructInitClass(init));*/
	}
	
	// Pre: curr is not terminal state
	private ClassBuilder constructClass(EState curr) throws ScribbleException  // FIXME: APIGenerationException?
	{
		switch (curr.getStateKind())
		{
			case OUTPUT:
			{
				/*Set<IOAction> as = curr.getTakeable();
				if (as.stream().allMatch((a) -> a.isSend()))*/
				{
					return new OutputSocketGenerator(this, curr).generateType();
				}
				//throw new RuntimeException("TODO: " + curr.toLongString());
			}
			case ACCEPT:
			{
				return new AcceptSocketGenerator(this, curr).generateType();
			}
			case UNARY_INPUT:
			{
				return new ReceiveSocketGenerator(this, curr).generateType();
			}
			case POLY_INPUT:
			{
				// Receive only
				return new BranchSocketGenerator(this, curr).generateType();
			}
			default:
			{
				throw new RuntimeException("[TODO] State Channel API generation not supported for: " + curr.getStateKind() + ", " + curr.toLongString());
			}
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
	
	protected EState getInitialState()
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

	public String getSocketClassName(EState s)
	{
		return this.classNames.get(s);
	}
	
	public TypeBuilder getType(String key)
	{
		return this.types.get(key);
	}
}
