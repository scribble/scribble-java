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
package org.scribble.core.type.session;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.MessageId;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.RecVar;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.Substitutions;
import org.scribble.core.visit.STypeInliner;
import org.scribble.core.visit.STypeUnfolder;
import org.scribble.core.visit.STypeVisitor;

public abstract class Recursion<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeBase<K, B>
{
	public final RecVar recvar;
	public final B body;

	public Recursion(//org.scribble.ast.Recursion<K> source, 
			CommonTree source,  // Due to inlining, protodecl -> rec
			RecVar recvar, B body)
	{
		super(source);
		this.recvar = recvar;
		this.body = body;
	}
	
	@Override
	public <T> Stream<T> collect(Function<SType<K, B>, Stream<T>> f)
	{
		return Stream.concat(f.apply(this), this.body.collect(f));
	}

	@Override
	public SType<K, B> visitWith(STypeVisitor<K, B> v)
	{
		return v.visitRecursion(this);
	}

	public abstract Recursion<K, B> reconstruct(
			CommonTree source, RecVar recvar, B body);
	
	@Override
	public Set<Role> getRoles()
	{
		return this.body.getRoles();
	}

	@Override
	public Set<MessageId<?>> getMessageIds()
	{
		return this.body.getMessageIds();
	}
	
	@Override
	public Recursion<K, B> substitute(Substitutions subs)
	{
		return reconstruct(getSource(), this.recvar, this.body.substitute(subs));
	}

	@Override
	public Set<RecVar> getRecVars()
	{
		return this.body.getRecVars();
	}

	@Override
	public SType<K, B> pruneRecs()
	{
		// Assumes no shadowing (e.g., use after SType#getInlined recvar disamb)
		return this.body.getRecVars().contains(this.recvar)
				? this
				: this.body;  // i.e., return a Seq, to be "inlined" by Seq.pruneRecs -- N.B. must handle empty Seq case
	}

	@Override
	public Recursion<K, B> getInlined(STypeInliner v)
	{
		CommonTree source = getSource();  // CHECKME: or empty source?
		RecVar rv = v.enterRec(//stack.peek(), 
				this.recvar);  // FIXME: make GTypeInliner, and record recvars to check freshness (e.g., rec X in two choice cases)
		B body = this.body.getInlined(v);//, stack);
		Recursion<K, B> res = reconstruct(source, rv, body);
		v.exitRec(this.recvar);
		return res;
	}

	@Override
	public SType<K, B> unfoldAllOnce(STypeUnfolder<K> u)
	{
		if (!u.hasRec(this.recvar))  // N.B. doesn't work if recvars shadowed
		{
			u.pushRec(this.recvar, this.body);
			SType<K, B> unf = this.body.unfoldAllOnce(u);
			u.popRec(this.recvar);  
					// Needed for, e.g., repeat do's in separate choice cases -- cf. stack.pop in GDo::getInlined, must pop sig there for Seqs
			return unf;
		}
		return this;
	}

	/* // CHECKME: try adding B to Seq
	Override
	public Recursion<K, B> getInlined(STypeInliner v)
	{
		CommonTree source = getSource();  // CHECKME: or empty source?
		RecVar rv = i.enterRec(//stack.peek(), 
				this.recvar);  // FIXME: make GTypeInliner, and record recvars to check freshness (e.g., rec X in two choice cases)
		B body = this.body.getInlined(v);
		Recursion<K, B> res = reconstruct(source, rv, body);
		i.exitRec(this.recvar);
		return res;
	}

	@Override
	public GType unfoldAllOnce(STypeUnfolder<Global> u)
	{
		if (!u.hasRec(this.recvar))  // N.B. doesn't work if recvars shadowed
		{
			u.pushRec(this.recvar, this.body);
			GType unf = (GType) this.body.unfoldAllOnce(u);
			u.popRec(this.recvar);  
					// Needed for, e.g., repeat do's in separate choice cases -- cf. stack.pop in GDo::getInlined, must pop sig there for Seqs
			return unf;
		}
		return this;
	}*/

	@Override
	public List<ProtocolName<K>> getProtoDependencies()
	{
		return this.body.getProtoDependencies();
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		return this.body.getNonProtoDependencies();
	}

	@Override
	public String toString()
	{
		return "rec " + this.recvar + " {\n" + this.body + "\n}";
	}

	@Override
	public int hashCode()
	{
		int hash = 1487;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.recvar.hashCode();
		hash = 31 * hash + this.body.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Recursion))
		{
			return false;
		}
		Recursion<?, ?> them = (Recursion<?, ?>) o;
		return super.equals(this)  // Does canEquals
				&& this.recvar.equals(them.recvar) && this.body.equals(them.body);
	}
}
