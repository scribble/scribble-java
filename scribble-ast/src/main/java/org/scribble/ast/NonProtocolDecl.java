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
import org.scribble.ast.name.simple.IdNode;
import org.scribble.core.type.kind.NonProtocolKind;
import org.scribble.del.ScribDel;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// Rename to something better
public abstract class NonProtocolDecl<K extends NonProtocolKind>
		extends NameDeclNode<K> implements ModuleMember
{
	// ScribTreeAdaptor#create constructor
	public NonProtocolDecl(Token payload)
	{
		super(payload);
		this.schema = null;
		this.extName = null;
		this.extSource = null;
	}

	// Tree#dupNode constructor
	protected NonProtocolDecl(NonProtocolDecl<K> node)
	{
		super(node);
		this.schema = null;
		this.extName = null;
		this.extSource = null;
	}
	
	public abstract NonProtocolDecl<K> dupNode();

	public IdNode getSchemaNodeChild()
	{
		return (IdNode) getChild(1);
	}

	public IdNode getExtNameNodeChild()
	{
		return (IdNode) getChild(2);
	}

	public IdNode getExtSourceNodeChild()
	{
		return (IdNode) getChild(3);
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
	
	public NonProtocolDecl<K> reconstruct(
			NameNode<K> name, 
			//String schema, String extName, String source,
			IdNode schema, IdNode extName, IdNode extSource) // FIXME:
	{
		NonProtocolDecl<K> npd = dupNode();
		ScribDel del = del();
		npd.addChild(name);
		npd.addChild(schema);
		npd.addChild(extName);
		npd.addChild(extSource);
		npd.setDel(del);  // No copy
		return npd;
	}

	@Override
	public NonProtocolDecl<K> visitChildren(AstVisitor nv)
			throws ScribException
	{
		NameNode<K> name = (NameNode<K>) visitChildWithClassEqualityCheck(this,
				getNameNodeChild(), nv);
		IdNode schema = //(AmbigNameNode) visitChildWithClassEqualityCheck(this, getSchemaNodeChild(), nv);
				getSchemaNodeChild();  // AmbigNameNode currently have no del, so not visited
		IdNode extName = //(AmbigNameNode) visitChildWithClassEqualityCheck(this, getExtNameNodeChild(), nv);
				getExtNameNodeChild();
		IdNode extSource = //(AmbigNameNode) visitChildWithClassEqualityCheck(this, getExtSourceNodeChild(), nv);
				getExtNameNodeChild();
		return reconstruct(name, schema, extName, extSource);
	}

	







	
	
	


	public final String schema;
	public final String extName;
	public final String extSource;

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
