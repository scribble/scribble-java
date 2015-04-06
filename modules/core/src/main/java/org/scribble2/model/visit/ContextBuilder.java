package org.scribble2.model.visit;

import org.scribble2.model.ModelNode;
import org.scribble2.util.ScribbleException;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
public class ContextBuilder extends ModelVisitor
{
	//private Stack<NodeContext> contexts = new Stack<>();
	
	//... module and protocoldecl contexts (delegates) ...
	
	/*private ModuleContext mcontext;
	private ProtocolDeclContext pdcontext;*/
	
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
	protected ContextBuilder enter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//System.out.println("2a: " + child.getClass() + ", " + child.del());
		
		return child.del().enterContextBuilding(parent, child, this);
	}

	@Override
	protected ModelNode leave(ModelNode parent, ModelNode child, ModelVisitor builder, ModelNode visited) throws ScribbleException
	{
		//System.out.println("2b: " + visited.getClass() + ", " + visited.del());

		return visited.del().leaveContextBuilding(parent, child, (ContextBuilder) this, visited);
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
	}
	
	public ModuleContext getModuleContext()
	{
		return (ModuleContext) this.contexts.get(0);
	}*/
}
