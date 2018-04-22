package org.scribble.ext.go.core.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.MemberNameNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.type.kind.DataTypeKind;

// HACK -- extending P@r[e] syntax is not clear yet
public class RPCoreDelegDecl extends DataTypeDecl
{
	public RPCoreDelegDecl(CommonTree source, String schema, String extName, String extSource, DataTypeNode name)
	{
		super(source, schema, extName, extSource, name);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new RPCoreDelegDecl(this.source, this.schema, this.extName, this.extSource, getNameNode());
	}
	
	@Override
	public RPCoreDelegDecl clone(AstFactory af)
	{
		DataTypeNode name = (DataTypeNode) this.name.clone(af);
		return ((RPAstFactory) af).ParamCoreDelegDecl(this.source, this.schema, this.extName, this.extSource, name);
	}

	@Override
	public RPCoreDelegDecl reconstruct(String schema, String extName, String extSource, MemberNameNode<DataTypeKind> name)
	{
		ScribDel del = del();
		RPCoreDelegDecl dtd = new RPCoreDelegDecl(this.source, schema, extName, extSource, (DataTypeNode) name);
		dtd = (RPCoreDelegDecl) dtd.del(del);
		return dtd;
	}

	@Override
	public String toString()
	{
		return "deleg" + " <" + this.schema + "> " + this.extName  // FIXME: factor out constant
				+ " " + Constants.FROM_KW + " " + this.extSource + " "
				+ Constants.AS_KW + " " + this.name + ";";
	}
}
