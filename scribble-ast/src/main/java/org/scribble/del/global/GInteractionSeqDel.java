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
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GSessionNode;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.core.type.session.global.GType;
import org.scribble.del.InteractionSeqDel;
import org.scribble.visit.GTypeTranslator;

public class GInteractionSeqDel extends InteractionSeqDel implements GDel
{
	
	@Override
	public GSeq translate(ScribNode n, GTypeTranslator t)
	{
		GInteractionSeq source = (GInteractionSeq) n;
		List<GType> elems = new LinkedList<>();
		for (GSessionNode c : source.getInteractionChildren())
		{
			elems.add((GType) c.visitWith(t));  // throws ScribbleException
		}
		return t.tf.global.GSeq(source, elems);
	}
}
