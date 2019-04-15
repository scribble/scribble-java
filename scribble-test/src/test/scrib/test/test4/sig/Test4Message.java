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
package test.test4.sig;

import org.scribble.runtime.message.ScribMessage;
import org.scribble.type.name.Op;

public abstract class Test4Message extends ScribMessage
{
	private static final long serialVersionUID = 1L;
	
	/*public Test4Message(Op op)
	{
		super(op);
	}*/

	public Test4Message(Op op, Object body)
	{
		super(op, body);
	}
	
	public abstract Object getBody();

	public byte[] toBytes()
	{
		// op is 3-char header (Foo/Bar)
		byte[] bs = (this.op.toString() + getBody() + "\n").getBytes(Test4Formatter.cs);
		return bs;
	}
	
	@Override
	public String toString()
	{
		return new String(toBytes());
	}
}
