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

import org.scribble.core.type.kind.DataKind;


// Potentially qualified/canonical payload type name; not the AST primitive identifier
// FIXME: record "external type name" from DataTypeDecl (for API gen)?  cf. OutputSockGen#addSendOpParams
public class DataName extends MemberName<DataKind>
		implements PayElemType<DataKind>
{
	private static final long serialVersionUID = 1L;

	public DataName(ModuleName modname, DataName membname)
	{
		super(DataKind.KIND, modname, membname);
	}
	
	public DataName(String simplename)
	{
		super(DataKind.KIND, simplename);
	}

	public boolean isDataName()
	{
		return true;
	}

	@Override
	public DataKind getKind()
	{
		return DataKind.KIND;
	}

	@Override
	public DataName getSimpleName()
	{
		return new DataName(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DataName))
		{
			return false;
		}
		DataName n = (DataName) o;
		return n.canEquals(this) && super.equals(o);
	}
	
	public boolean canEquals(Object o)
	{
		return o instanceof DataName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2767;
		hash = 31 * super.hashCode();
		return hash;
	}
}
