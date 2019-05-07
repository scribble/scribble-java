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
package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public abstract class Choice<K extends ProtoKind>
		extends CompoundInteraction<K>
{
	public static final int SUBJ_CHILD_INDEX = 0;
	public static final int BLOCK_CHILDREN_START_INDEX = 1;

	// ScribTreeAdaptor#create constructor
	public Choice(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected Choice(Choice<K> node)
	{
		super(node);
	}
	
	public RoleNode getSubjectChild()
	{
		return (RoleNode) getChild(SUBJ_CHILD_INDEX);
	}

	// Override in concrete sub for cast
	public abstract List<? extends ProtoBlock<K>> getBlockChildren();
	
	// "add", not "set"
	public void addScribChildren(RoleNode subj,
			List<? extends ProtoBlock<K>> blocks)
	{
		// Cf. above getters and Scribble.g children order
		addChild(subj);
		addChildren(blocks);
	}
	
	@Override
	public abstract Choice<K> dupNode();
	
	public Choice<K> reconstruct(RoleNode subj,
			List<? extends ProtoBlock<K>> blocks)
	{
		Choice<K> dup = dupNode();
		dup.addScribChildren(subj, blocks);
		dup.setDel(del());  // No copy
		return dup;
	}
	
	@Override
	public Choice<K> visitChildren(AstVisitor nv) throws ScribException
	{
		RoleNode subj = (RoleNode) visitChild(getSubjectChild(), nv);
		List<? extends ProtoBlock<K>> blocks = 
				visitChildListWithClassEqualityCheck(this, getBlockChildren(), nv);
		return reconstruct(subj, blocks);
	}
	
	@Override
	public String toString()
	{
		String sep = " " + Constants.OR_KW + " ";
		return Constants.CHOICE_KW + " "
				+ Constants.AT_KW + " " + getSubjectChild() + " "
				+ getBlockChildren().stream().map(b -> b.toString())
						.collect(Collectors.joining(sep));
	}
}
