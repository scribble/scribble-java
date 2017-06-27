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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.Role;

public class NonRoleParamDecl<K extends NonRoleParamKind> extends HeaderParamDecl<K>
{
	public final K kind;

	public NonRoleParamDecl(CommonTree source, K kind, NonRoleParamNode<K> name)
	{
		super(source, name);
		this.kind = kind;
	}
	
	@Override
	public NonRoleParamDecl<K> clone(AstFactory af)
	{
		NonRoleParamNode<K> param = (NonRoleParamNode<K>) this.name.clone(af);
		return af.NonRoleParamDecl(this.source, this.kind, param);
	}
	
	@Override
	public NonRoleParamDecl<K> reconstruct(SimpleNameNode<K> name)
	{
		ScribDel del = del();
		NonRoleParamDecl<K> pd = new NonRoleParamDecl<>(this.source, this.kind, (NonRoleParamNode<K>) name);
		@SuppressWarnings("unchecked")
		NonRoleParamDecl<K> tmp = (NonRoleParamDecl<K>) pd.del(del);
		return tmp;
	}

	@Override
	protected NonRoleParamDecl<K> copy()
	{
		return new NonRoleParamDecl<>(this.source, this.kind, (NonRoleParamNode<K>) this.name);
	}

	@Override
	public NonRoleParamDecl<K> project(AstFactory af, Role self)
	{
		NonRoleParamNode<K> pn = af.NonRoleParamNode(this.name.source, this.kind, this.name.toString());
		return af.NonRoleParamDecl(this.source, this.kind, pn);
	}
	
	@Override
	public String getKeyword()
	{
		if (this.kind.equals(SigKind.KIND))
		{
			return Constants.SIG_KW;
		}
		else if (this.kind.equals(DataTypeKind.KIND))
		{
			return Constants.TYPE_KW;
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
	}
}
