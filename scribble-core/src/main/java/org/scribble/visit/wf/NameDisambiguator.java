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
package org.scribble.visit.wf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.del.global.GDelegationElemDel;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ModuleContextVisitor;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
// Also canonicalises recvars
public class NameDisambiguator extends ModuleContextVisitor
{
  // For implicit scope generation: reset per ProtocolDecl
	//private int counter = 1;

	private Set<Role> roles = new HashSet<>();
	private Set<String> annotPayloads = new HashSet<>();
	private Map<String, NonRoleParamKind> params = new HashMap<>();
	//private Set<RecVar> recvars = new HashSet<>();
	//private Map<RecVar, Deque<RecVar>> recvars = new HashMap<>();
	private Map<RecVar, Integer> recvars = new HashMap<>();  // Nesting count integer now unused (recvar renaming refactored to inlining -- don't want to mangle source AST)
	
	//private ProtocolDecl<?> root;  // FIXME: factor out  // Now unused (recvar renaming refactored to inlining -- don't want to mangle source AST)
	
	public NameDisambiguator(Job job)
	{
		super(job);
	}

	/*public ScopeNode getFreshScope()
	{
		return new ScopeNode(null, Scope.IMPLICIT_SCOPE_PREFIX + "." + counter++);
	}*/

	// Most subclasses will override visitForSubprotocols (e.g. ReachabilityChecker, FsmConstructor), but sometimes still want to change whole visit pattern (e.g. Projector)
	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		/*if (child instanceof ProtocolDecl<?>)  // FIXME: factor out
		{
			this.root = (ProtocolDecl<?>) child;
		}*/
		enter(parent, child);
		ScribNode visited = visitForDisamb(parent, child);
		return leave(parent, child, visited);
	}

	protected ScribNode visitForDisamb(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof GDelegationElem)
		{
			//return visitOverrideForDelegationElem(parent, (Do<?>) child);
			return ((GDelegationElemDel) child.del()).visitForNameDisambiguation(this, (GDelegationElem) child);
		}
		else
		{
			return child.visitChildren(this); 
		}
	}

	@Override
	//public NameDisambiguator enter(ModelNode parent, ModelNode child) throws ScribbleException
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterDisambiguation(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveDisambiguation(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}
	
	public void clear()
	{
		//this.counter = 1;
		this.roles.clear();
		this.params.clear();
		this.recvars.clear();  // Should be unnecessary
		this.annotPayloads.clear(); 
		
		//this.pds.clear();  // No: called by ProtocolDecl leaveDisambiguation (i.e. before the above leave override) -- should be unnecessary anyway
	}
	
	public void addRole(Role role)
	{
		this.roles.add(role);
	}
	
	public boolean isBoundRole(Role role)
	{
		return this.roles.contains(role);
	}

	public void addParameter(Name<? extends NonRoleParamKind> param, NonRoleParamKind kind)
	{
		this.params.put(param.toString(), kind);
	}
	
	// name is a simple name (compound names are not ambiguous)
	public boolean isBoundParameter(Name<? extends NonRoleArgKind> name)  // ArgKind allows AmbigNames
	{
		return this.params.containsKey(name.toString());
	}

	public NonRoleParamKind getParameterKind(Name<? extends NonRoleArgKind> name)  // ArgKind allows AmbigNames
	{
		return this.params.get(name.toString());
	}

	//public void addRecVar(RecVar rv)
	public void pushRecVar(RecVar rv)
	{
		//this.recvars.add(rv);
		/*Deque<RecVar> deque = this.recvars.get(rv);
		if (deque.isEmpty())
		{
			deque = new LinkedList<RecVar>();
			this.recvars.put(rv, deque);
		}
		deque.push(..canonicalised name..);*/
		if (!this.recvars.containsKey(rv))
		{
			this.recvars.put(rv, 0);
		}
		else
		{
			this.recvars.put(rv, this.recvars.get(rv) + 1);
		}
	}
	
	public boolean isBoundRecVar(RecVar rv)
	{
		//return this.recvars.contains(rv);
		return this.recvars.containsKey(rv);
	}
	
	public boolean isVarnameInScope(String name)
	{
		return this.annotPayloads.contains(name); 
	}
	
	public void addAnnotPaylaod(String name)
	{
		this.annotPayloads.add(name); 
	}
	
	//public void removeRecVar(RecVar rv)
	public void popRecVar(RecVar rv)
	{
		//this.recvars.remove(rv);
		/*Deque<RecVar> deque = this.recvars.get(rv);
		deque.pop();
		if (deque.isEmpty())
		{
			this.recvars.remove(rv);
		}*/
		Integer i = this.recvars.get(rv);
		if (i == 0)
		{
			this.recvars.remove(rv);  // Cf. isBoundRecVar, uses containsKey
		}
		else
		{
			this.recvars.put(rv, i - 1);
		}
	}

	
	
	/*public String getCanonicalRecVarName(RecVar rv)
	{
		return getCanonicalRecVarName(this.getModuleContext().root, this.root.header.getDeclName(), rv.toString() + "_" + this.recvars.get(rv));
	}
	
	// Cf. ProtocolDefInliner.newRecVarId
	public static String getCanonicalRecVarName(ModuleName fullmodname, ProtocolName<?> simpprotoname, String rv)
	{
		//return rv.toString();
		return ("__" + fullmodname + "_" + simpprotoname + "_" + rv).replace('.', '_');
	}*/
}
