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
package bettybook.http.shortv.message;

import org.scribble.runtime.net.ScribMessage;
import org.scribble.sesstype.name.Op;

import bettybook.http.shortv.HttpShort.Http.Http;

public abstract class HttpShortMessage extends ScribMessage
{
	private static final long serialVersionUID = 1L;

	public static final String GET = "GET";
	public static final String HTTP = "HTTP";

	public static final String CRLF = "\r\n";

	public HttpShortMessage(Op op, String m)
	{
		super(op, m);
	}
	
	public String getHeadersAndBody()
	{
		return (this.payload.length == 0) ? "" : (String) this.payload[0];
	}

	public byte[] toBytes()
	{
		// FIXME: factor better with Request/Response (e.g. " " after op, terminal CRLF, etc)
		return (getOpString(this.op) + getHeadersAndBody()).getBytes(HttpShortMessageFormatter.cs);  // Can give "utf-8" as arg directly
	}
	
	@Override
	public String toString()
	{
		return new String(toBytes());
	}
	
	protected static String getOpString(Op op)
	{
		if (op.equals(Http.Req))
		{
			return HttpShortMessage.GET;
		}
		else if (op.equals(Http.Resp))
		{
			return HttpShortMessage.HTTP;
		}
		else
		{
			throw new RuntimeException("TODO: " + op);
		}
	}
}
