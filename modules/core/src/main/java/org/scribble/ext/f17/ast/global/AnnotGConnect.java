package org.scribble.ext.f17.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageNode;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.f17.ast.AnnotNode;
import org.scribble.ext.f17.ast.ScribAnnot;

public class AnnotGConnect extends GConnect implements AnnotNode
{
	// Duplicated from AnnotGMessageTransfer
	public final ScribAnnot annot;  // null if none  // FIXME: refactor properly

	public AnnotGConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	{
		this(source, src, msg, dest, null);
	}

	public AnnotGConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, ScribAnnot annot)
	{
		super(source, src, msg, dest);
		this.annot = annot;
	}

	@Override
	public boolean isAnnotated()
	{
		return this.annot != null;
	}

	@Override
	public ScribAnnot getAnnotation()
	{
		return this.annot;
	}

	@Override
	protected AnnotGConnect copy()
	{
		return new AnnotGConnect(this.source, this.src, this.msg, this.dest, this.annot);
	}
	
	@Override
	public AnnotGConnect clone()
	{
		RoleNode src = this.src.clone();
		MessageNode msg = this.msg.clone();
		RoleNode dest = this.dest.clone();
		return AstFactoryImpl.FACTORY.GConnect(this.source, src, msg, dest, this.annot);  // Not cloning String annot
	}

	@Override
	public AnnotGConnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	{
		ScribDel del = del();
		AnnotGConnect gmt = new AnnotGConnect(this.source, src, msg, dest, this.annot);
		gmt = (AnnotGConnect) gmt.del(del);
		return gmt;
	}

	@Override
	public String toString()
	{
		return super.toString() + (isAnnotated() ? this.annot : "");
	}
}
