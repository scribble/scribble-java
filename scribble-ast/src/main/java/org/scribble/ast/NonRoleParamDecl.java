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

import org.antlr.runtime.Token;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.MemberName;

// CHECKME: drop generic parameter and kind?
public abstract class NonRoleParamDecl<K extends NonRoleParamKind>
		extends ParamDecl<K>
{
	public final K kind;  // CHECKME: factor up to super?
	
	// ScribTreeAdaptor#create constructor
	public NonRoleParamDecl(Token t, K kind)
	{
		super(t);
		this.kind = kind;
	}

	// Tree#dupNode constructor
	public NonRoleParamDecl(NonRoleParamDecl<K> node)
	{
		super(node);
		this.kind = node.kind;
	}

	@Override
	public abstract MemberName<K> getDeclName();  // DataType/MessageSigName are MemberNames
}
