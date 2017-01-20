package org.scribble.ext.f17.sesstype.name;

import org.scribble.ext.f17.ast.ScribAnnot;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.kind.PayloadTypeKind;
import org.scribble.sesstype.name.PayloadType;


//public class AnnotDataType<K> extends DataType implements AnnotType
public class AnnotPayloadType<K extends PayloadTypeKind> implements PayloadType<K>, AnnotType
{
	//private static final long serialVersionUID = 1L;
	
	public final PayloadType<K> pay;
	public final PayloadVar var;  // Cf. AnnotUnaryPayloadElem
	public final ScribAnnot annot;

	public AnnotPayloadType(PayloadType<K> pay, PayloadVar var, ScribAnnot annot)
	{
		this.pay = pay;
		this.var = var;
		this.annot = annot;
	}
	
	@Override
	public String toString()
	{
		return this.var + ":" + this.pay + ((this.annot != null) ? this.annot.toString() : "");
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AnnotPayloadType))
		{
			return false;
		}

		AnnotPayloadType<?> them = (AnnotPayloadType<?>) o;
		return them.canEqual(this) && this.pay.equals(them.pay) && this.var.equals(them.var);
			// FIXME: annot considered part of "typing" -- e.g., in action equality/duality
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof AnnotPayloadType;
	}

	@Override
	public int hashCode()
	{
		int hash = 3163;
		hash = 31 * super.hashCode();
		hash = 31 * this.pay.hashCode();
		hash = 31 * this.var.hashCode();
		return hash;
	}

	@Override
	public NonRoleArgKind getKind()
	{
		return this.pay.getKind();
	}

	/*private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeObject(this.proto);
		out.writeObject(this.role);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		this.proto = (GProtocolName) in.readObject();
		this.role = (Role) in.readObject();
	}*/
}
