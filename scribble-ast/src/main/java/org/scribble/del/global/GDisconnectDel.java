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

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GDisconnect;
import org.scribble.core.type.name.Role;
import org.scribble.del.ConnectionActionDel;
import org.scribble.util.ScribException;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.NameDisambiguator;

// TODO: make DisconnectDel (cf., G/LMessageTransferDel)
public class GDisconnectDel extends ConnectionActionDel
		implements GSimpleSessionNodeDel
{
	public GDisconnectDel()
	{
		
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		GDisconnect gc = (GDisconnect) visited;
		return gc;
	}
	
	@Override
	public org.scribble.core.type.session.global.GDisconnect translate(ScribNode n,
			GTypeTranslator t)
	{
		GDisconnect source = (GDisconnect) n;
		Role left = source.getLeftChild().toName();
		Role right = source.getRightChild().toName();
		return t.tf.global.GDisconnect(source, left, right);
	}
}
