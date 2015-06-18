/*
 * Copyright 2009 www.scribble.org
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.ast;

import org.scribble.ast.visit.AstVisitor;
import org.scribble.ast.visit.Substitutor;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 */
public interface ScribNode
{
	ScribNode accept(AstVisitor nv) throws ScribbleException;
	ScribNode visitChildren(AstVisitor nv) throws ScribbleException;
	//ModelNode visitChildrenInSubprotocols(SubprotocolVisitor nv) throws ScribbleException;

	ScribDel del();
	ScribNode del(ScribDel del);
	//<T extends ModelNodeBase> T del(T t, ModelDelegate del);
	
	ScribNode substituteNames(Substitutor subs);
}
