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
package org.scribble.del.global;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GProtoBlock;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.del.ChoiceDel;
import org.scribble.visit.GTypeTranslator;

public class GChoiceDel extends ChoiceDel implements GCompoundSessionNodeDel
{
	
	@Override
	public org.scribble.core.type.session.global.GChoice translate(ScribNode n,
			GTypeTranslator t)
	{
		GChoice source = (GChoice) n;
		Role subj = source.getSubjectChild().toName();
		List<GSeq> blocks = new LinkedList<>();
		for (GProtoBlock b : source.getBlockChildren())
		{
			blocks.add((GSeq) b.visitWith(t));
		}
		return t.tf.global.GChoice(source, subj, blocks);
	}
}
