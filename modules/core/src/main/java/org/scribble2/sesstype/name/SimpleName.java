package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.Kind;


// Make a subclass of CompoundName? Better for e.g. simple member name nodes?
@Deprecated
public class SimpleName extends Name
{
	protected SimpleName(Kind kind, String[] elems)
	{
		super(kind, elems);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	//protected static final SimpleName EMPTY_NAME = new SimpleName();
	
	//public final String text;

	/*protected SimpleName(KindEnum kind)
	{
		super(kind);
		//this.text = "";
	}

	//public SimpleName(String text)
	public SimpleName(KindEnum kind, String text)
	{
		super(kind, text);
		//this.text = text;
	}*/
}
