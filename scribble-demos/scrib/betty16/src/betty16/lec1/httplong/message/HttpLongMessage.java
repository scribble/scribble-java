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
package betty16.lec1.httplong.message;

import org.scribble.runtime.message.ScribMessage;
import org.scribble.core.type.name.Op;

import betty16.lec1.httplong.HttpLong.Http.Http;

// Unlike ScribMessage, HttpMessage is not actually "sent", but we use it as the base class since the socket API takes ScribMessages
public abstract class HttpLongMessage extends ScribMessage
{
	private static final long serialVersionUID = 1L;

	// " " after ops done by HeaderField
	public static final String GET = "GET";
	public static final String HTTP = "HTTP";
	public static final String HOST = "Host";

	public static final String USER_AGENT = "User-Agent";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String DO_NOT_TRACK = "DNT";     
	public static final String CONNECTION = "Connection";
	public static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
	public static final String COOKIE = "Cookie";

	public static final String DATE = "Date";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String _404 = "404";
	public static final String _200 = "200";
	public static final String ACCEPT_RANGES = "Accept-Ranges";
	public static final String LAST_MODIFIED = "Last-Modified";
	public static final String VARY = "Vary";
	public static final String SERVER = "Server";
	public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
	public static final String VIA = "Via";
	public static final String ETAG = "ETag";
	public static final String CONTENT_LENGTH = "Content-Length";
	
	protected static final String CRLF = "\r\n";

	public HttpLongMessage(Op op)
	{
		super(op);
	}

	public HttpLongMessage(Op op, String body)
	{
		super(op, body);
	}
	
	public String getBody()
	{
		return (this.payload.length == 0) ? "" : (String) this.payload[0];
	}

	public byte[] toBytes()
	{
		return (getOpString(this.op) + getBody() + HttpLongMessage.CRLF)
				.getBytes(HttpLongMessageFormatter.cs);
				// Can give "utf-8" as arg directly
	}
	
	@Override
	public String toString()
	{
		return new String(toBytes());
	}
	
	// " " after ops done by HeaderField
	protected static String getOpString(Op op)
	{
		if (op.equals(Http.CONTENTL))
		{
			return HttpLongMessage.CONTENT_LENGTH;
		}
		else if (op.equals(Http.ETAG))
		{
			return HttpLongMessage.ETAG;
		}
		else if (op.equals(Http.BODY))
		{
			return HttpLongMessage.CRLF;  // This CRLF "op" actually enacts the empty line for end-of-headers
		}
		else if (op.equals(Http.VIA))
		{
			return HttpLongMessage.VIA;
		}
		else if (op.equals(Http.SERVER))
		{
			return HttpLongMessage.SERVER;
		}
		else if (op.equals(Http.VARY))
		{
			return HttpLongMessage.VARY;
		}
		else if (op.equals(HttpLongMessage.CRLF))
		{
			return "";
		}
		else if (op.equals(Http.REQUESTL))  // FIXME: not just GET (POST..)
		{
			return HttpLongMessage.GET;
		}
		else if (op.equals(Http.LASTM))
		{
			return HttpLongMessage.LAST_MODIFIED;
		}
		else if (op.equals(Http.ACCEPTR))
		{
			return HttpLongMessage.ACCEPT_RANGES;
		}
		else if (op.equals(Http.HOST))
		{
			return HttpLongMessage.HOST;
		}
		else if (op.equals(Http.HTTPV))
		{
			return HttpLongMessage.HTTP;
		}
		else if (op.equals(Http._200))
		{
			return HttpLongMessage._200;
		}
		else if (op.equals(Http._404))
		{
			return HttpLongMessage._404;
		}
		else if (op.equals(Http.CONTENTT))
		{
			return HttpLongMessage.CONTENT_TYPE;
		}
		else if (op.equals(Http.DATE))
		{
			return HttpLongMessage.DATE;
		}
		else
		{
			throw new RuntimeException("TODO: " + op);
		}
	}
}
