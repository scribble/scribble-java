package org.scribble.ast.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.ast.Module;
import org.scribble.ast.context.ModuleContext;
import org.scribble.del.ModuleDel;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
public class NameDisambiguator extends ModelVisitor
{
	private ModuleContext mcontext;  // FIXME: hack -- factor out ContextVisitor

  // Reset per Module
	//private ModuleContext context;

  // Reset per ProtocolDecl
	//private int counter = 1;

	//private Map<String, KindEnum> params = new HashMap<>();
	//private Map<Name<? extends Kind>, Kind> params = new HashMap<>();
	private Set<Role> roles = new HashSet<>();

	private Map<String, Kind> params = new HashMap<>();

	public NameDisambiguator(Job job)
	{
		super(job);
	}

	/*public void enterModule(Module mod)
	{
		//this.types.clear();
		this.context = new ModuleContext(this.getJobContext(), mod);
	}*/
	
	//public void enterProtocolDecl(ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	public void clear()
	{
		//this.counter = 1;
		this.roles.clear();
		this.params.clear();
	}
	
	/*public ScopeNode getFreshScope()
	{
		return new ScopeNode(null, Scope.IMPLICIT_SCOPE_PREFIX + "." + counter++);
	}
	
	// name is a simple name (compound names are not ambiguous)
	public boolean isVisiblePayloadType(Name name)
	{
		return this.context.isPayloadTypeVisible(new PayloadType(name.toString()));
	}

	public boolean isVisibleMessageSignatureName(Name name)
	{
		return this.context.isMessageSignatureNameVisible(new MessageSignatureName(name.toString()));
	}*/
	
	//public void addParameter(Parameter param)
	//public void addParameter(Parameter param)
	//public <K extends Kind> void addParameter(Name<K> param, K kind)
	public void addParameter(Name<? extends Kind> param, Kind kind)
	{
		//this.params.put(param.toString(), param.getKindEnum());
		//this.params.add(param);
		this.params.put(param.toString(), kind);
	}

	public void addRole(Role role)
	{
		this.roles.add(role);
	}
	
	public boolean isBoundRole(Role role)
	{
		return this.roles.contains(role);
	}
	
	// name is a simple name (compound names are not ambiguous)
	//public boolean isBoundParameter(IName name)
	public boolean isBoundParameter(Name<? extends Kind> name)
	{
		return this.params.containsKey(name.toString());
		//return this.params.containsKey(name);
	}

	//public KindEnum getParameterKind(IName name)
	public Kind getParameterKind(Name<? extends Kind> name)
	{
		return this.params.get(name.toString());
		//return this.params.get(name);
	}
	
	@Override
	//public NameDisambiguator enter(ModelNode parent, ModelNode child) throws ScribbleException
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Module)
		{
			this.mcontext = ((ModuleDel) child.del()).getModuleContext();
		}

		// Could make forwarder methods in ModelNode
		//return (NameDisambiguator) child.del().enterDisambiguation(parent, child, this);
		child.del().enterDisambiguation(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		//return visited.del().leaveDisambiguation(parent, child, (NameDisambiguator) nv, visited);
		return visited.del().leaveDisambiguation(parent, child, this, visited);
	}
	
	// FIXME: factor up
	public ModuleContext getModuleContext() throws ScribbleException
	{
		return this.mcontext;
	}

	/*public PayloadTypeOrParameterNode disambiguate(AmbiguousNameNode ambig) throws ScribbleException
	{
		CommonTree ct = ((AbstractNode) ambig).ct;
		String text = ((AmbiguousNameNode) ambig).toName().text;
		PayloadTypeOrParameterNode n;
		if (this.env.params.isParameterDeclared(new Parameter(text)))
		{
			n = new ParameterNode(ct, text);
		}
		else
		{
			PayloadType pt = new PayloadType(text);
			if (this.env.isPayloadTypeDeclared(pt))
			{
				n = new PayloadTypeNode(ct, text, this.types.get(pt));
			}
			else
			{
				throw new ScribbleException("Name cannot be disambiguated: " + ambig);
			}
		}
		return n;
	}
	
	// FIXME: factor out based on schema
	// check ext source definitions?
	public void addPayloadType(PayloadTypeDecl ptd)
	{
		String extName = ptd.extName.substring(1, ptd.extName.length() - 1);  // Hacky: remove quotes (also in parser)
		extName = "class " + extName;  // FIXME: factor out java name hack (should depend on schema)
		this.types.put(ptd.alias.toName(), extName);
	}*/
	
	/*public String getExternalPayloadType(PayloadType pt)
	{
		return this.types.get(pt);
	}*/
	
	/*@Override
	public Node visit(Node parent, Node child) throws ScribbleException
	{
		return n.disambiguate(this);
	}*/
}
