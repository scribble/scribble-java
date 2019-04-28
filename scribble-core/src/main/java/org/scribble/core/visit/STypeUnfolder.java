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
package org.scribble.core.visit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.scribble.core.job.Core;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Recursion;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;

// Pre: use on inlined -- not supported for Do
public abstract class STypeUnfolder<K extends ProtoKind, B extends Seq<K, B>>
		extends STypeVisitorNoThrow<K, B>
{
	public final Core core;

	private final Map<RecVar, Seq<K, ?>> recs = new HashMap<>(); 
	
	public STypeUnfolder(Core core)
	{
		this.core = core;
	}

	@Override
	public final SType<K, B> visitDo(Do<K, B> n)
	{
		throw new RuntimeException(this.getClass() + " unsupported for Do: " + n);
	}

	@Override
	public SType<K, B> visitRecursion(Recursion<K, B> n)
	{
		if (!hasRec(n.recvar))  // N.B. doesn't work if recvars shadowed
		{
			pushRec(n.recvar, n.body);
			SType<K, B> unf = visitSeq(n.body);//n.body.visitWithNoEx(this);
			popRec(n.recvar);  
					// Needed for, e.g., repeat do's in separate choice cases -- cf. stack.pop in GDo::getInlined, must pop sig there for Seqs
			return unf;
		}
		return n;
	}

	@Override
	public B visitSeq(B n)
	{
		List<SType<K, B>> elems = new LinkedList<>();
		for (SType<K, B> e : n.elems)
		{
			SType<K, B> e1 = e.visitWithNoThrow(this);
			if (e1 instanceof Seq<?, ?>)
			{
				elems.addAll(((Seq<K, B>) e1).elems);
			}
			else
			{
				elems.add(e1);
			}
		}
		return n.reconstruct(n.getSource(), elems);
	}

	protected void pushRec(RecVar rv, Seq<K, ?> body)
	{
		if (this.recs.containsKey(rv))
		{
			throw new RuntimeException("Shouldn't get here: " + rv);
		}
		this.recs.put(rv, body);
	}

	protected boolean hasRec(RecVar rv)
	{
		return this.recs.containsKey(rv);
	}
	
	protected Seq<K, ?> getRec(RecVar rv)
	{
		return this.recs.get(rv);
	}
	
	protected void popRec(RecVar rv)
	{
		this.recs.remove(rv);
	}
}
