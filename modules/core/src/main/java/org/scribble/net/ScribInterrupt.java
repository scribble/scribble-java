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
package org.scribble.net;

import org.scribble.sesstype.name.Op;

public class ScribInterrupt extends ScribMessage
{
	private static final long serialVersionUID = 1L;
	
	public static final Op SCRIB_INTERR = new Op("__interr");

	public ScribInterrupt(Throwable t)
	{
		super(SCRIB_INTERR, new Object[] { t });
	}

	@Override
	public int hashCode()
	{
		int hash = 79;
		hash = 31 * hash + super.hashCode();
		return hash;
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof ScribInterrupt;
	}
}
