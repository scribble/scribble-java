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
package org.scribble.type.name;

import org.scribble.type.kind.Kind;



public abstract class QualifiedName<K extends Kind> extends AbstractName<K>
{
	private static final long serialVersionUID = 1L;

	public QualifiedName(K kind, String... elems)
	{
		super(kind, elems);
	}

	@Override
	public boolean isEmpty()
	{
		return super.isEmpty();
	}

	@Override
	public boolean isPrefixed()
	{
		return super.isPrefixed();
	}
	
	// Also done by Scope
	public abstract Name<? extends Kind> getPrefix();
	public abstract Name<K> getSimpleName();
}
