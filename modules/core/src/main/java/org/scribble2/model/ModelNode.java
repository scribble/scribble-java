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
package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.model.visit.SubprotocolVisitor;
import org.scribble2.model.visit.Substitutor;
import org.scribble2.util.ScribbleException;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 */
public interface ModelNode// extends Copy
{
	ModelNode visit(ModelVisitor nv) throws ScribbleException;
	ModelNode visitChildren(ModelVisitor nv) throws ScribbleException;
	ModelNode visitChildrenInSubprotocols(SubprotocolVisitor nv) throws ScribbleException;

	ModelDelegate del();
	ModelNodeBase del(ModelDelegate del);
	
	ModelNode substituteNames(Substitutor subs);
}
