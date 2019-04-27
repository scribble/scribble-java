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

// Base class for all E/SModelFactoryImpl
// N.B. this class does *not* itself implement top-level ModelFactory
public abstract class ModelFactoryImpl
{
	protected ModelFactory mf = null;

	// Only for use from ModelFactory constructor
	// Pre: mf.local/global == this
	protected void setParent(ModelFactory mf)
	{
		if (this.mf != null)
		{
			throw new RuntimeException("Incorrect usage: ");
		}
		this.mf = mf;
	}
}
