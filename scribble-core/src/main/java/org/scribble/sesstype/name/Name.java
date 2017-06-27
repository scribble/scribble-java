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
package org.scribble.sesstype.name;

import java.io.Serializable;
import java.util.Arrays;

import org.scribble.sesstype.kind.Kind;


public interface Name<K extends Kind> extends Serializable
{
	public K getKind();

	public int getElementCount();
	public boolean isEmpty();
	public boolean isPrefixed();

	public String[] getElements();
	public String[] getPrefixElements();
	public String getLastElement();

	static String[] compileElements(String[] cn, String n)
	{
		if (cn.length == 0)
		{
			return new String[] { n };
		}
		String[] elems = Arrays.copyOf(cn, cn.length + 1);
		elems[elems.length - 1] = n;
		return elems;
	}
}
