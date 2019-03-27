package org.scribble.lang;

import java.util.HashMap;
import java.util.Map;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;

// Currently once usage only, no popRec -- CHECKME: fix?
public class STypeUnfolder<K extends ProtocolKind>
{
	private final Map<RecVar, Seq<K>> recs = new HashMap<>(); 

	public STypeUnfolder()
	{

	}

	public void pushRec(RecVar rv, Seq<K> body)
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
	
	public Seq<K> getRec(RecVar rv)
	{
		return this.recs.get(rv);
	}
	
	/*public B popRec(RecVar rv)
	{
		return this.recs.remove(rv);
	}*/
}
