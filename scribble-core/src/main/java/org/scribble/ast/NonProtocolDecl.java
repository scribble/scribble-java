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
import org.scribble.ast.name.NameNode;
import org.scribble.del.ScribDel;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.NonProtocolKind;
import org.scribble.visit.AstVisitor;

// Rename to something better
public abstract class NonProtocolDecl<K extends NonProtocolKind>
		extends NameDeclNode<K> implements ModuleMember
{
	public final String schema;
	public final String extName;
	public final String extSource;

	// ScribTreeAdaptor#create constructor
	public NonProtocolDecl(Token payload, String schema, String extName,
			String extSource)
	{
		super(payload);
		this.schema = schema;
		this.extName = extName;
		this.extSource = extSource;
	}

	// Tree#dupNode constructor
	protected NonProtocolDecl(NonProtocolDecl<K> node, String schema,
			String extName, String extSource)
	{
		super(node);
		this.schema = schema;
		this.extName = extName;
		this.extSource = extSource;
	}
	
	// CHECKME: maybe move to ModuleMember
	public boolean isDataTypeDecl()
	{
		return false;
	}

	public boolean isMessageSigNameDecl()
	{
		return false;
	}
	
	public abstract NonProtocolDecl<K> dupNode();
	
	public NonProtocolDecl<K> reconstruct(String schema, String extName,
			String source, // MemberNameNode<K> name);
			NameNode<K> name)
	{
		NonProtocolDecl<K> dtd = dupNode();
		ScribDel del = del();
		dtd.addChild(getNameNodeChild());
		dtd.setDel(del);
		return dtd;
	}

	@Override
	public NonProtocolDecl<K> visitChildren(AstVisitor nv)
			throws ScribbleException
	{
		//MemberNameNode<K> name = (MemberNameNode<K>) visitChildWithClassEqualityCheck(this, this.name, nv);
		NameNode<K> name = (NameNode<K>) visitChildWithClassEqualityCheck(this,
				getNameNodeChild(), nv);
		return reconstruct(this.schema, this.extName, this.extSource, name);
	}

	










	public NonProtocolDecl(CommonTree source, String schema, String extName,
			String extSource, // MemberNameNode<K> name);
			NameNode<K> name)  // Directly parsed decl names are simple names, but decl constructors take general name nodes which are member names, so base NameNode here -- cf. ProtocolHeader and G/LProtocolHeader
	{
		super(source, name);
		this.schema = schema;
		this.extName = extName;
		this.extSource = extSource;
	}

}
