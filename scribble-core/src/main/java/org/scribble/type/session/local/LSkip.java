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
package org.scribble.type.session.local;

import java.util.List;
import java.util.Set;

import org.scribble.job.ScribbleException;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.type.kind.Local;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;
import org.scribble.type.name.Substitutions;
import org.scribble.type.session.STypeBase;
import org.scribble.visit.STypeInliner;
import org.scribble.visit.STypeUnfolder;
import org.scribble.visit.local.ReachabilityEnv;

// Used only *during* projection -- filtered out by GSeq::projection
public class LSkip extends STypeBase<Local, LSeq> implements LType
{
	public static final LSkip SKIP = new LSkip();
	
	private LSkip()
	{
		super(null);
	}
	
	@Override
	public Set<Role> getRoles()
	{
		throw new RuntimeException("Unsupported for Skip: " + this);
	}

	@Override
	public Set<MessageId<?>> getMessageIds()
	{
		throw new RuntimeException("Unsupported for Skip: " + this);
	}

	@Override
	public Set<RecVar> getRecVars()
	{
		throw new RuntimeException("Unsupported for Skip: " + this);
	}

	@Override
	public LSkip pruneRecs()
	{
		throw new RuntimeException("Unsupported for Skip: " + this);
	}
		
	@Override
	public List<ProtocolName<Local>> getProtoDependencies()
	{
		throw new RuntimeException("Unsupported for Skip: " + this);
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		throw new RuntimeException("Unsupported for Skip: " + this);
	}

	@Override
	public boolean isSingleConts(Set<RecVar> rvs)
	{
		throw new RuntimeException("Unsupported for Skip:\n" + this);
	}

	@Override
	public LSkip substitute(Substitutions subs)
	{
		return this;
	}

	@Override
	public LSkip getInlined(STypeInliner i)
	{
		return this;
	}

	@Override
	public LType unfoldAllOnce(STypeUnfolder<Local> u)
	{
		throw new RuntimeException("Unsupported for Skip: " + this);
	}

	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		throw new RuntimeException("Unsupported for: " + this);
	}
	
	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		throw new RuntimeException("Unsupported for: " + this);
	}

	@Override
	public String toString()
	{
		return "[skip];";
	}

	@Override
	public int hashCode()
	{
		int hash = 2833;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LSkip))
		{
			return false;
		}
		return ((LSkip) o).canEquals(this);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LSkip;
	}
}

