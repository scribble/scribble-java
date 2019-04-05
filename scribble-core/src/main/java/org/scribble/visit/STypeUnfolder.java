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
package org.scribble.visit;

import java.util.HashMap;
import java.util.Map;

import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.RecVar;
import org.scribble.type.session.Seq;

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
	
	public void popRec(RecVar rv)
	{
		this.recs.remove(rv);
	}
}
