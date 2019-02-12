package org.scribble.lang;

import java.util.HashMap;
import java.util.Map;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;

public class SessTypeUnfolder<K extends ProtocolKind, B extends Seq<K>>
{
	private final Map<RecVar, B> recs = new HashMap<>(); 

	public SessTypeUnfolder()
	{

	}

	public void pushRec(RecVar rv, B body)
	{
		if (this.recs.containsKey(rv))
		{
			throw new RuntimeException("Shouldn't get here: " + rv);
		}
		this.recs.put(rv, body);
	}

	public boolean hasRec(RecVar rv)
	{
		return this.recs.containsKey(rv);
	}
	
	public B getRec(RecVar rv)
	{
		return this.recs.get(rv);
	}
	
	/*public B popRec(RecVar rv)
	{
		return this.recs.remove(rv);
	}*/
}
