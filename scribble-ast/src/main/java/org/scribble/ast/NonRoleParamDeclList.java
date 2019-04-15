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
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.MemberName;
import org.scribble.del.ScribDel;

// Can contain "mixed" type/sig kinds
// Typing a bit awkward that this list has to use NonRoleParamKind as the "concrete" kind, while the NonRoleParamDecl elements use the actual concrete kind
// But OK because the NonRoleParamDecl nodes are immutable (the generic kind value is never rewritten after instantiation, only read)
public class NonRoleParamDeclList extends ParamDeclList<NonRoleParamKind>
{
	// ScribTreeAdaptor#create constructor
	public NonRoleParamDeclList(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public NonRoleParamDeclList(NonRoleParamDeclList node)
	{
		super(node);
	}

	@Override
	public List<NonRoleParamDecl<NonRoleParamKind>> getDeclChildren()
	{
		@SuppressWarnings("unchecked")
		List<NonRoleParamDecl<NonRoleParamKind>> cast =
				getChildren().stream()
						.map(x -> (NonRoleParamDecl<NonRoleParamKind>) x)  // Cast specifically to NonRoleParamKind (not "?") for hetero list
						.collect(Collectors.toList());
		return cast;
	}

	public List<MemberName<? extends NonRoleParamKind>> getParameters()
	{
		return getDeclChildren().stream().map(decl -> decl.getDeclName())
				.collect(Collectors.toList());
	}

	@Override
	public NonRoleParamDeclList dupNode()
	{
		return new NonRoleParamDeclList(this);
	}

	@Override
	public NonRoleParamDeclList reconstruct(
			List<? extends ParamDecl<NonRoleParamKind>> decls)
	{
		NonRoleParamDeclList sig = dupNode();
		sig.addChildren(decls);
		ScribDel del = del();
		sig.setDel(del);  // No copy
		return sig;
	}

	@Override
	public String toString()
	{
		return (isEmpty())
				? ""
				: "<" + super.toString() + ">";
	}
}
