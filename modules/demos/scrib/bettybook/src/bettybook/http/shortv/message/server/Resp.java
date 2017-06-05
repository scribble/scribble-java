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
package bettybook.http.shortv.message.server;

import bettybook.http.shortv.HttpShort.Http.Http;
import bettybook.http.shortv.message.HttpShortMessage;

public class Resp extends HttpShortMessage
{
	private static final long serialVersionUID = 1L;

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

	/*HTTP/1.1 200 OK
	Date: Mon, 13 Jun 2016 19:42:34 GMT
	Server: Apache
	Strict-Transport-Security: max-age=31536000; preload; includeSubDomains
	Strict-Transport-Security: max-age=31536000; preload; includeSubDomains  // BUG? (Apache configuration)
	Last-Modified: Thu, 14 Apr 2016 12:46:24 GMT
	ETag: "74a-53071482f6e0f"
	Accept-Ranges: bytes
	Content-Length: 1866
	Vary: Accept-Encoding
	Content-Type: text/html
	Via: 1.1 www.doc.ic.ac.uk*/
	public Resp(String httpv, String ack, String date, String server, String strictTS, String lastMod,
			String eTag, String acceptR, String contentL, String vary, String contentT, String via, String body)
	{
		super(Http.Resp, getHeadersAndBody(
				httpv, ack, date, server, strictTS, lastMod, eTag, acceptR, contentL, vary, contentT, via, body));
	}

	/*public Response(String httpv, String ack, String contentL, String body)
	{
		super(Http.RESPONSE, getHeadersAndBody(httpv, ack, contentL, body));
	}*/

	public Resp(String httpv, String body)
	{
		this(httpv, Resp._200 + " OK", null, null, null, null, null, null, Integer.toString(body.length()), null, null, null, body);
	}

	// ack includes "op", e.g. 200, 404, ...
	protected static String getHeadersAndBody(String httpv, String ack, String date, String server, String strictTS, String lastMod,
			String eTag, String acceptR, String contentL, String vary, String contentT, String via, String body)
	{
		if (!ack.startsWith(Resp._200) && !ack.startsWith(Resp._404))
		{
			throw new RuntimeException("[TODO]: " + ack);
		}
		return "/" + httpv + " "
				+ ack + HttpShortMessage.CRLF  // Hardcoded
				+ ((date == null) ? "" : Resp.DATE + ": " + date + HttpShortMessage.CRLF)
				+ ((server == null) ? "" : Resp.SERVER + ": " + server + HttpShortMessage.CRLF)
				+ ((strictTS == null) ? "" : Resp.STRICT_TRANSPORT_SECURITY + ": " + strictTS + HttpShortMessage.CRLF)
				+ ((lastMod == null) ? "" : Resp.LAST_MODIFIED + ": " + lastMod + HttpShortMessage.CRLF)
				+ ((eTag == null) ? "" : Resp.ETAG + ": " + eTag + HttpShortMessage.CRLF)
				+ ((acceptR == null) ? "" : Resp.ACCEPT_RANGES + ": " + acceptR + HttpShortMessage.CRLF)
				+ Resp.CONTENT_LENGTH + ": " + contentL + HttpShortMessage.CRLF
				+ ((vary == null) ? "" : Resp.VARY + ": " + vary + HttpShortMessage.CRLF)
				+ ((contentT == null) ? "" : Resp.CONTENT_TYPE + ": " + contentT + HttpShortMessage.CRLF)
				+ ((via == null) ? "" : Resp.VIA + ": " + via + HttpShortMessage.CRLF)
				+ HttpShortMessage.CRLF  // Empty line for end of headers
				+ body + HttpShortMessage.CRLF;
	}

	/*// 404
	protected static String getHeadersAndBody(String httpv, String ack, String contentL, String body)
	{
		return "/" + httpv + " "
				+ _404 + ack + HttpMessage.CRLF  // Hardcoded
				+ Response.CONTENT_LENGTH + ": " + contentL + HttpMessage.CRLF
				+ HttpMessage.CRLF  // Empty line for end of headers
				+ body + HttpMessage.CRLF;
	}*/
}
