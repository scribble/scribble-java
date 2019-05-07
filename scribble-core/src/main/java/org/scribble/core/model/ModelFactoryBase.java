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
package org.scribble.core.model;

// Base class for all *E/SModelFactoryImpl*
// N.B. this class does *not* itself implement the top-level ModelFactory wrapper (nor vice versa)
public abstract class ModelFactoryBase
{
	protected ModelFactory mf = null;

	public ModelFactoryBase(ModelFactory mf)
	{
		this.mf = mf;
	}
}
