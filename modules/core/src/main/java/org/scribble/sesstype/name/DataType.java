package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.DataTypeKind;


// Potentially qualified/canonical payload type name; not the AST primitive identifier
//public class DataType extends MemberName implements PayloadType
//public class DataType extends Name<DataTypeKind> implements PayloadType<DataTypeKind>
public class DataType extends MemberName<DataTypeKind> implements PayloadType<DataTypeKind>
{
	private static final long serialVersionUID = 1L;

	//public DataType(ModuleName modname, String membname)
	public DataType(ModuleName modname, DataType membname)
	{
		//super(KindEnum.TYPE, modname, membname);
		super(DataTypeKind.KIND, modname, membname);
	}
	
	public DataType(String simplename)
	{
		//super(KindEnum.TYPE, simplename);
		super(DataTypeKind.KIND, simplename);
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
	
	/*@Override
	public boolean isParameter()
	{
		return false;
	}*/
}
