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
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.simple.ExtIdNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.core.type.kind.NonProtocolKind;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// Rename to something better
public abstract class NonProtoDecl<K extends NonProtocolKind>
		extends NameDeclNode<K> implements ModuleMember
{
	// NameDeclNode.NAMENODE_CHILD_INDEX = 0;
	public static final int SCHEMA_NODE_CHILD_INDEX = 1;
	public static final int EXTNAME_NODE_CHILD_INDEX = 2;
	public static final int EXTSOURCE_NODE_CHILD_INDEX = 3;

	// ScribTreeAdaptor#create constructor
	public NonProtoDecl(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected NonProtoDecl(NonProtoDecl<K> node)
	{
		super(node);
	}

	// Redundant override, just for documentation
	@Override
	public abstract NameNode<K> getNameNodeChild();

	public IdNode getSchemaChild()
	{
		return (IdNode) getChild(SCHEMA_NODE_CHILD_INDEX);
	}

	public ExtIdNode getExtNameChild()
	{
		return (ExtIdNode) getChild(EXTSOURCE_NODE_CHILD_INDEX);
	}

	public ExtIdNode getExtSourceChild()
	{
		return (ExtIdNode) getChild(EXTSOURCE_NODE_CHILD_INDEX);
	}

	public String getSchema()
	{
		return getSchemaChild().getText();
	}

	public String getExtName()
	{
		return getExtNameChild().getText();
	}

	public String getExtSource()
	{
		return getExtSourceChild().getText();
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
	
	@Override
	public abstract NonProtoDecl<K> dupNode();
	
	public NonProtoDecl<K> reconstruct(NameNode<K> name, IdNode schema,
			IdNode extName, IdNode extSource)
	{
		NonProtoDecl<K> n = dupNode();
		n.addChild(name);
		n.addChild(schema);
		n.addChild(extName);
		n.addChild(extSource);
		n.setDel(del());  // No copy
		return n;
	}

	@Override
	public NonProtoDecl<K> visitChildren(AstVisitor nv)
			throws ScribException
	{
		NameNode<K> name = (NameNode<K>) visitChildWithClassEqualityCheck(this,
				getNameNodeChild(), nv);
		IdNode schema = //(AmbigNameNode) visitChildWithClassEqualityCheck(this, getSchemaNodeChild(), nv);
				getSchemaChild();  // AmbigNameNode currently have no del, so not visited -- CHECKME: now IdNode?
		IdNode extName = //(AmbigNameNode) visitChildWithClassEqualityCheck(this, getExtNameNodeChild(), nv);
				getExtNameChild();
		IdNode extSource = //(AmbigNameNode) visitChildWithClassEqualityCheck(this, getExtSourceNodeChild(), nv);
				getExtNameChild();
		return reconstruct(name, schema, extName, extSource);
	}
}
