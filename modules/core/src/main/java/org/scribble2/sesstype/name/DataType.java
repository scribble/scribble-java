package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.DataTypeKind;
import org.scribble2.sesstype.kind.Kind;


// Potentially qualified/canonical payload type name; not the AST primitive identifier
//public class DataType extends MemberName implements PayloadType
public class DataType extends Name<DataTypeKind> implements PayloadType<DataTypeKind>
{
	private static final long serialVersionUID = 1L;

	/*public DataType(ModuleName modname, String membname)
	{
		super(KindEnum.TYPE, modname, membname);
	}*/
	
	public DataType(String simplename)
	{
		//super(KindEnum.TYPE, simplename);
		super(DataTypeKind.KIND, simplename);
	}

	@Override
	public Kind getKind()
	{
		return DataTypeKind.KIND;
	}

	/*@Override
	public DataType getSimpleName()
	{
		return new DataType(getLastElement());
	}
	
	@Override
	public boolean isParameter()
	{
		return false;
	}*/
}
