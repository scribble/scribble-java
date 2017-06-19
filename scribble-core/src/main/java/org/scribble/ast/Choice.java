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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class Choice<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	public final RoleNode subj;
	private final List<? extends ProtocolBlock<K>> blocks;  
			// Factor up? And specialise to singleton for Recursion/Interruptible? Maybe too artificial -- could separate unaryblocked and multiblocked compound ops?

	protected Choice(CommonTree source, RoleNode subj, List<? extends ProtocolBlock<K>> blocks)
	{
		super(source);
		this.subj = subj;
		this.blocks = new LinkedList<>(blocks);
	}
	
	public abstract Choice<K> reconstruct(RoleNode subj, List<? extends ProtocolBlock<K>> blocks);
	
	@Override
	public Choice<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode subj = (RoleNode) visitChild(this.subj, nv);
		List<? extends ProtocolBlock<K>> blocks = visitChildListWithClassEqualityCheck(this, this.blocks, nv);
		return reconstruct(subj, blocks);
	}
	
	public List<? extends ProtocolBlock<K>> getBlocks()
	{
		return Collections.unmodifiableList(this.blocks);
	}
	
	@Override
	public String toString()
	{
		String sep = " " + Constants.OR_KW + " ";
		return Constants.CHOICE_KW + " " + Constants.AT_KW + " " + this.subj + " "
				+ this.blocks.stream().map((b) -> b.toString()).collect(Collectors.joining(sep));
	}
	
	/*@Override
	public Map<Role, MessageId> getEnablingMessages()
	{
		
	}*/
}
