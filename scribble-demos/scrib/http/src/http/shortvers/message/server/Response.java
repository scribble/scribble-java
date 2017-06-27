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
package http.shortvers.message.server;

import http.shortvers.HttpShort.Http.Http;
import http.shortvers.message.HttpShortMessage;

public class Response extends HttpShortMessage
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
	public Response(String httpv, String ack, String date, String server, String strictTS, String lastMod,
			String eTag, String acceptR, String contentL, String vary, String contentT, String via, String body)
	{
		super(Http.RESPONSE, getHeadersAndBody(
				httpv, ack, date, server, strictTS, lastMod, eTag, acceptR, contentL, vary, contentT, via, body));
	}

	/*public Response(String httpv, String ack, String contentL, String body)
	{
		super(Http.RESPONSE, getHeadersAndBody(httpv, ack, contentL, body));
	}*/

	public Response(String httpv, String body)
	{
		this(httpv, Response._200 + " OK", null, null, null, null, null, null, Integer.toString(body.length()), null, null, null, body);
	}

	// ack includes "op", e.g. 200, 404, ...
	protected static String getHeadersAndBody(String httpv, String ack, String date, String server, String strictTS, String lastMod,
			String eTag, String acceptR, String contentL, String vary, String contentT, String via, String body)
	{
		if (!ack.startsWith(Response._200) && !ack.startsWith(Response._404))
		{
			throw new RuntimeException("[TODO]: " + ack);
		}
		return "/" + httpv + " "
				+ ack + HttpShortMessage.CRLF  // Hardcoded
				+ ((date == null) ? "" : Response.DATE + ": " + date + HttpShortMessage.CRLF)
				+ ((server == null) ? "" : Response.SERVER + ": " + server + HttpShortMessage.CRLF)
				+ ((strictTS == null) ? "" : Response.STRICT_TRANSPORT_SECURITY + ": " + strictTS + HttpShortMessage.CRLF)
				+ ((lastMod == null) ? "" : Response.LAST_MODIFIED + ": " + lastMod + HttpShortMessage.CRLF)
				+ ((eTag == null) ? "" : Response.ETAG + ": " + eTag + HttpShortMessage.CRLF)
				+ ((acceptR == null) ? "" : Response.ACCEPT_RANGES + ": " + acceptR + HttpShortMessage.CRLF)
				+ Response.CONTENT_LENGTH + ": " + contentL + HttpShortMessage.CRLF
				+ ((vary == null) ? "" : Response.VARY + ": " + vary + HttpShortMessage.CRLF)
				+ ((contentT == null) ? "" : Response.CONTENT_TYPE + ": " + contentT + HttpShortMessage.CRLF)
				+ ((via == null) ? "" : Response.VIA + ": " + via + HttpShortMessage.CRLF)
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
