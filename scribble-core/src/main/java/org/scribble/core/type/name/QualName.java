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
package org.scribble.core.type.name;

import java.util.Arrays;

import org.scribble.core.type.kind.Kind;


// A (potenitally" qualified name -- a compound name
public abstract class QualName<K extends Kind> extends AbstractName<K>
{
	private static final long serialVersionUID = 1L;

	public QualName(K kind, String... elems)
	{
		super(kind, elems);
	}

	//@Override
	public boolean isPrefixed()
	{
		return this.elems.length > 1;
	}

	//@Override
	public String[] getPrefixElements()
	{
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}
	
	// Also done by Scope
	public abstract Name<? extends Kind> getPrefix();
	public abstract Name<K> getSimpleName();
}
