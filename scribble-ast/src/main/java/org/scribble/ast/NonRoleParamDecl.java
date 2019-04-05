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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.MemberName;

// CHECKME: can drop generic parameter and kind?
public abstract class NonRoleParamDecl<K extends NonRoleParamKind>
		extends HeaderParamDecl<K>
{
	public final K kind;  // CHECKME: factor up to super?

	//..HERE make this abstract with concrete type/sig subclasses -- and fix parser accordingly
	
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
	
	/*@Override
	public NonRoleParamDecl<K> dupNode()
	{
		return new NonRoleParamDecl<>(this, this.kind);
	}*/
	
	/*@Override
	public NonRoleParamNode<K> getNameNodeChild()
	{
		NameNode<?> raw = getRawNameNodeChild();
		if (this.kind.equals(SigKind.KIND) || this.kind.equals(DataTypeKind.KIND)
				|| this.kind.equals(Local.KIND))
		{
			// Unchecked cast because this class not further subclassed for kind, nor the NameNodes -- unlike the Kind objects themselves
			@SuppressWarnings("unchecked")
			NonRoleParamNode<K> cast = (NonRoleParamNode<K>) raw;
			return cast;
		}
		throw new RuntimeException("Shouldn't get in here: " + this.kind);
	}*/

	/*@Override
	public NonRoleParamDecl<K> project(AstFactory af, Role self)
	{
		NonRoleParamNode<K> child = getNameNodeChild();
		NonRoleParamNode<K> pn = af.NonRoleParamNode(child.source, this.kind,
				child.toString());
		return af.NonRoleParamDecl(this.source, this.kind, pn);
	}*/

	@Override
	public abstract MemberName<K> getDeclName();  // DataType/MessageSigName are MemberNames
	/*{
		return getNameNodeChild().toName();
	}*/
	
	/*@Override
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
		else  // CHECKME: Local?
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
	}*/
	
	
	
	
	
	
	
	
	

	public NonRoleParamDecl(CommonTree source, K kind, NonRoleParamNode<K> name)
	{
		super(source, name);
		this.kind = kind;
	}
	
	/*@Override
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
	}*/
}
