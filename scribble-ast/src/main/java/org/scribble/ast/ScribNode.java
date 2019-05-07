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

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.scribble.del.ScribDel;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.SimpleAstVisitor;
import org.scribble.visit.SimpleAstVisitorNoThrows;

/**
 * This is the generic object from which all Scribble AST objects
 * are derived.
 */
public interface ScribNode extends Tree
{
	CommonTree getSource();   
			// Can explicitly track an "original" source, cf. always using the "current" node as its own source
			// Can be better for some error messages, e.g., during/after some AST transfomations

	@Override
	ScribNode getParent();

	//@Override -- no: super return not generic
	List<? extends ScribNode> getChildren();

	ScribNode clone();

	ScribDel del();
	//ScribNode del(ScribDel del);

	ScribNode accept(AstVisitor nv) throws ScribException;  // The "top-level" method, e.g., module.accept(v)  (cf. Job::runVisitorOnModule)
	ScribNode visitChildren(AstVisitor nv) throws ScribException;
	
	// For "simpler" visiting patterns than above
	default <T> T visitWith(SimpleAstVisitor<T> v) throws ScribException  // "Top-level" visitor entry method
	{
		return v.visit(this);  // N.B. ScribNode has getParent
	}

	default <T> T visitWith(SimpleAstVisitorNoThrows<T> v)  // "Top-level" visitor entry method, c.f. STypeAggNoThrow
	{
		return v.visit(this);  // N.B. ScribNode has getParent
	}
}
