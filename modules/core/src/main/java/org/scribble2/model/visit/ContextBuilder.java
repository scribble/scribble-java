package org.scribble2.model.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.ModelNode;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
public class ContextBuilder extends ModelVisitor
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	// role is the target role?
	private Map<Role, Map<GProtocolName, Set<Role>>> gdeps = new HashMap<>();  // FIXME: generalise to support locals
	private Map<Role, Map<LProtocolName, Set<Role>>> ldeps = new HashMap<>();
	/*private Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> gdeps = new HashMap<>();  // FIXME: generalise to support locals
	private Map<Role, Map<? extends ProtocolName<Local>, Set<Role>>> ldeps = new HashMap<>();*/
	//private Map<Role, Map<? extends ProtocolName<? extends ProtocolKind>, Set<Role>>> deps = new HashMap<>();

	//private Stack<NodeContext> contexts = new Stack<>();
	
	//... module and protocoldecl contexts (delegates) ...
	
	private ModuleContext mcontext;  // The "root" Module context (not the "main" module)
	//private ProtocolDeclContext pdcontext;*/
	
	//private Module module;
	
	// Subprotocol dependencies: full protocol names
	//private ProtocolName root = null;
	/*private final Map<Role, Map<ProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	//private Set<ProtocolName> depenencies;// = new HashSet<>();*/
	
	public ContextBuilder(Job job)
	{
		super(job);
	}
	
	//... do ... role-oriented protocol dependencies (needs substitution?)... or maybe do projection-dependencies later, just do subprotocol visiting first ...
	
	/*public boolean isRootContext()
	{
		return this.root == null;
	}
	
	//public void clearProtocolDependencies()
	public void pushRoot(ProtocolName root)
	{
		//this.depenencies.clear();
		this.root = root;
		this.depenencies = new HashSet<ProtocolName>();
	}
	
	public void addProtocolDependency(ProtocolName pn)
	{
		this.depenencies.add(pn);
	}
	
	//public Set<ProtocolName> getProtocolDependencies()
	public Set<ProtocolName> popRoot()
	{
		Set<ProtocolName> deps = this.depenencies;
		this.depenencies = null;
		return deps;
	}*/

	@Override
	//protected ContextBuilder enter(ModelNode parent, ModelNode child) throws ScribbleException
	protected void enter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//System.out.println("2a: " + child.getClass() + ", " + child.del());
		
		//return child.del().enterContextBuilding(parent, child, this);
		child.del().enterContextBuilding(parent, child, this);
	}

	@Override
	//protected ModelNode leave(ModelNode parent, ModelNode child, ModelVisitor builder, ModelNode visited) throws ScribbleException
	protected ModelNode leave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		//System.out.println("2b: " + visited.getClass() + ", " + visited.del());

		//return visited.del().leaveContextBuilding(parent, child, (ContextBuilder) builder, visited);
		return visited.del().leaveContextBuilding(parent, child, this, visited);
	}
	
	/*public void pushContext(NodeContext ncontext)
	{
		this.contexts.push(ncontext);
	}
	
	public NodeContext popContext()
	{
		return this.contexts.pop();
	}
	
	public NodeContext peekContext()
	{
		return this.contexts.peek();
	}

	public void replaceContext(NodeContext ncontext)
	{
		this.contexts.pop();
		this.contexts.push(ncontext);
	}*/


	public void setModuleContext(ModuleContext mcontext)
	{
		this.mcontext = mcontext;
	}
	
	public ModuleContext getModuleContext()
	{
		//return (ModuleContext) this.contexts.get(0);
		return this.mcontext;
	}
	
	public void clearProtocolDependencies()
	{
		//this.dependencies.clear();  // clears the reference obtained from getProtocolDependencies  // maybe we/client should make a defensive copy
		this.gdeps = new HashMap<>();
		this.ldeps = new HashMap<>();
		//this.deps = new HashMap<>();
	}

	public void addGlobalProtocolDependency(Role self, GProtocolName gpn, Role role)
	{
		addProtocolDependency(this.gdeps, self, gpn, role);
		//addProtocolDependency(this.deps, self, gpn, role);
	}

	public void addLocalProtocolDependency(Role self, LProtocolName gpn, Role role)
	{
		addProtocolDependency(this.ldeps, self, gpn, role);
	}

	//private <K extends ProtocolKind> void addProtocolDependency(Map<Role, Map<ProtocolName<K>, Set<Role>>> map, Role self, ProtocolName<K> gpn, Role role)
	private static <N extends ProtocolName<K>, K extends ProtocolKind> void addProtocolDependency(Map<Role, Map<N, Set<Role>>> map, Role self, N gpn, Role role)
	//private static <K extends ProtocolKind> void addProtocolDependency(Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> map, Role self, ProtocolName<K> gpn, Role role)
	{
		Map<N, Set<Role>> tmp1 = map.get(self);
		//Map<? extends ProtocolName<K>, Set<Role>> tmp1 = map.get(self);
		if (tmp1 == null)
		{
			tmp1 = new HashMap<>();
			map.put(self, tmp1);
		}
		
		Set<Role> tmp2 = tmp1.get(gpn);
		if (tmp2 == null)
		{
			tmp2 = new HashSet<>();
			tmp1.put(gpn, tmp2);
		}
		tmp2.add(role);
	}

	//public Map<Role, Map<GProtocolName, Set<Role>>> getProtocolDependencies()
	public Map<Role, Map<GProtocolName, Set<Role>>> getGlobalProtocolDependencies()
	//public Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> getGlobalProtocolDependencies()
	{
		return this.gdeps;
	}

	public Map<Role, Map<LProtocolName, Set<Role>>> getLocalProtocolDependencies()
	//public Map<Role, Map<? extends ProtocolName<Local>, Set<Role>>> getLocalProtocolDependencies()
	{
		return this.ldeps;
	}

	/*public Map<Role, Map<? extends ProtocolName<? extends ProtocolKind>, Set<Role>>> getProtocolDependencies()
	{
		return this.deps;
	}*/
}
