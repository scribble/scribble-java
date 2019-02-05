package org.scribble.lang;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.del.global.GDel;
import org.scribble.lang.global.GType;

public class SessTypeTranslator
{
	public SessTypeTranslator()
	{

	}

	public GType translate(GProtocolDecl gpd)
	{
		GDel del = (GDel) gpd.del();

		return 
	}
}
