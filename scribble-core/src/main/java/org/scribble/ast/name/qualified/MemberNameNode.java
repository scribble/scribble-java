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
package org.scribble.ast.name.qualified;

import java.util.Arrays;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.PackageName;

public abstract class MemberNameNode<K extends Kind> extends QualifiedNameNode<K>
{
	public MemberNameNode(CommonTree source, String... ns)
	{
		super(source, ns);
	}
	
	protected ModuleName getModuleNamePrefix()
	{
		String[] prefix = getPrefixElements();
		ModuleName mn = new ModuleName(prefix[prefix.length - 1]);
		if (prefix.length == 1)
		{
			return mn;
		}
		return new ModuleName(new PackageName(Arrays.copyOf(prefix, prefix.length - 1)), mn);
	}
}
