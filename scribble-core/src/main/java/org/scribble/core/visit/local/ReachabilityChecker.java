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
package org.scribble.core.visit.local;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeVisitor;
import org.scribble.util.ScribException;

// Pre: use on inlined or later (unsupported for Do, also Protocol)
public class ReachabilityChecker extends STypeVisitor<Local, LSeq>
{
	private ReachabilityEnv env;  // Immutable

	protected ReachabilityChecker()
	{
		this.env = new ReachabilityEnv(false, Collections.emptySet());
	}
	
	@Override
	public SType<Local, LSeq> visitChoice(Choice<Local, LSeq> n)
			throws ScribException
	{
		ReachabilityEnv entry = getEnv();
		List<ReachabilityEnv> blocks = new LinkedList<>();
		ReachabilityChecker nested = new ReachabilityChecker();
		for (LSeq block : n.blocks)
		{
			nested.setEnv(entry);
			nested.visitSeq(block);//block.visitWith(nested);
			blocks.add(nested.getEnv());
		}
		boolean postcont = blocks.stream().allMatch(x -> x.postcont);  // i.e., no exits
		Set<RecVar> recvars = blocks.stream().flatMap(x -> x.recvars.stream())
				.collect(Collectors.toSet());
		setEnv(new ReachabilityEnv(postcont, recvars));
		return n;
	}

	public SType<Local, LSeq> visitContinue(Continue<Local, LSeq> n) throws ScribException
	{
		ReachabilityEnv env = getEnv();
		Set<RecVar> tmp = new HashSet<>(env.recvars);
		tmp.add(n.recvar);
		setEnv(new ReachabilityEnv(true, tmp));
		return n;
	}

	@Override
	public final SType<Local, LSeq> visitDo(Do<Local, LSeq> n) throws ScribException
	{
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}

	@Override
	public SType<Local, LSeq> visitRecursion(Recursion<Local, LSeq> n)
			throws ScribException
	{
		n.body.visitWith(this);
		ReachabilityEnv env = getEnv();
		if (env.recvars.contains(n.recvar))
		{
			Set<RecVar> tmp = new HashSet<>(env.recvars);
			tmp.remove(n.recvar);
			setEnv(new ReachabilityEnv(env.postcont, tmp));
		}
		return n;
	}

	@Override
	public LSeq visitSeq(LSeq n)
			throws ScribException
	{
		LType prev = null; 
		LType next = null;
		for (Iterator<LType> i = n.getElements().iterator(); i.hasNext(); )
		{
			prev = next;
			next = i.next();
			ReachabilityEnv env = getEnv();
			if (!env.isSeqable())
			{
				throw new ScribException(
						"Illegal sequence: " + (prev == null ? "" : prev + "\n") + next);
			}
			next.visitWith(this);
		}
		return n;
	}
	
	protected ReachabilityEnv getEnv()
	{
		return this.env;
	}
	
	protected void setEnv(ReachabilityEnv env)
	{
		this.env = env;
	}
}


// Immutable
class ReachabilityEnv
{
  // For checking bad sequencing of unreachable code: false after a continue; true if choice has an exit (false inherited for all other constructs)
	public final boolean postcont;

	// For checking "reachable code" also satisfies tail recursion (e.g., after choice-with-exit)
	public final Set<RecVar> recvars;  

	public ReachabilityEnv(boolean postcont, Set<RecVar> recvars)
	{
		this.postcont = postcont;
		this.recvars = Collections.unmodifiableSet(recvars);
	}

	public boolean isSeqable()
	{
		return !this.postcont && this.recvars.isEmpty();
	}
}