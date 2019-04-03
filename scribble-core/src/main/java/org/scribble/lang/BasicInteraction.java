package org.scribble.lang;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.ProtocolName;
import org.scribble.type.name.RecVar;

public abstract class BasicInteraction<K extends ProtocolKind>
		extends STypeBase<K> implements SType<K>
{
	public BasicInteraction(org.scribble.ast.BasicInteraction<K> source)
	{
		super(source);
	}

	@Override
	public Set<RecVar> getRecVars()
	{
		return Collections.emptySet();
	}
	
	@Override
	public BasicInteraction<K> pruneRecs()
	{
		return this;
	}

	@Override
	public BasicInteraction<K> getInlined(STypeInliner i)
	{
		return this;
	}

	@Override
	public List<ProtocolName<K>> getProtoDependencies()
	{
		return Collections.emptyList();
	}
	
	@Override
	public org.scribble.ast.BasicInteraction<K> getSource()
	{
		return (org.scribble.ast.BasicInteraction<K>) super.getSource();
	}
}
