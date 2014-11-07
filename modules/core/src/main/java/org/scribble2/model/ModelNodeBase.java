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
import org.scribble2.util.ScribbleException;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 */
public abstract class ModelNodeBase implements ModelNode
{
	private ModelDelegate del;

	/*protected ModelNodeBase(ModelDelegate del)
	{
		this.del = del;
	}*/
	
	@Override
	public ModelNode visit(ModelVisitor nv) throws ScribbleException
	{
		//return this.del.visit(this, nv);
		return visitChild(this, nv);
	}
	
	//@Override
	protected ModelNode visitChild(ModelNode child, ModelVisitor nv) throws ScribbleException
	{
		//return this.del.visit(this, child, nv);
		return nv.visit(this, child);
	}

	@Override
	public ModelNode visitChildren(ModelVisitor nv) throws ScribbleException
	{
		return this;
	}

	/*private final CommonTree ct;
	
	public ModelNodeBase(CommonTree ct)
	{
		this.ct = ct;
	}*/
	
	@Override
	public ModelDelegate del()
	{
		return this.del;
	}
	
	@Override
	public ModelNodeBase del(ModelDelegate del)
	{
		ModelNodeBase copy = copy();
		copy.del = del;
		return copy;
	}
	
	// Internal shallow copy for immutables
	//@Override
	protected abstract ModelNodeBase copy();
}
