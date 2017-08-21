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

import org.scribble.sesstype.kind.DataTypeKind;


// Potentially qualified/canonical payload type name; not the AST primitive identifier
public class DataType extends MemberName<DataTypeKind> implements PayloadElemType<DataTypeKind>
{
	private static final long serialVersionUID = 1L;

	public DataType(ModuleName modname, DataType membname)
	{
		super(DataTypeKind.KIND, modname, membname);
	}
	
	public DataType(String simplename)
	{
		super(DataTypeKind.KIND, simplename);
	}

	public boolean isDataType()
	{
		return true;
	}

	@Override
	public DataTypeKind getKind()
	{
		return DataTypeKind.KIND;
	}

	@Override
	public DataType getSimpleName()
	{
		return new DataType(getLastElement());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DataType))
		{
			return false;
		}
		DataType n = (DataType) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof DataType;
	}

	@Override
	public int hashCode()
	{
		int hash = 2767;
		hash = 31 * super.hashCode();
		return hash;
	}
}
