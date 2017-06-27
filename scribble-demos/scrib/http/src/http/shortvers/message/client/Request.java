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
package http.shortvers.message.client;

import http.shortvers.HttpShort.Http.Http;
import http.shortvers.message.HttpShortMessage;

public class Request extends HttpShortMessage
{
	private static final long serialVersionUID = 1L;

	//public static final String GET = "GET";
	//public static final String HTTP = "HTTP";
	public static final String HOST = "Host";

	public static final String USER_AGENT = "User-Agent";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String DO_NOT_TRACK = "DNT";     
	public static final String CONNECTION = "Connection";
	public static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";

	public Request(String get, String http, String host, String userA, String accept, String acceptL, String acceptE, String dnt, String connection, String upgradeIR)
	{
		super(Http.REQUEST, getHeadersAndBody(get, http, host, userA, accept, acceptL, acceptE, dnt, connection, upgradeIR));
	}
	
	/*GET /~rhu/ HTTP/1.1
	host: www.doc.ic.ac.uk*/
	public Request(String get, String http, String host)
	{
		this(get, http, host, null, null, null, null, null, null, null);
	}

	// Empty body 
	protected static String getHeadersAndBody(String get, String http, String host, String userA, String accept, String acceptL, String acceptE, String dnt, String connection, String upgradeIR) {
		return " "
				+ get + " " + HttpShortMessage.HTTP + "/" + http + HttpShortMessage.CRLF
				+ Request.HOST + ": " + host + HttpShortMessage.CRLF
				+ ((userA == null) ? "" : Request.USER_AGENT + ": " + userA + HttpShortMessage.CRLF)
				+ ((accept == null) ? "" : Request.ACCEPT + ": " + accept + HttpShortMessage.CRLF)
				+ ((acceptL == null) ? "" : Request.ACCEPT_LANGUAGE + ": " + acceptL + HttpShortMessage.CRLF)
				+ ((acceptE == null) ? "" : Request.ACCEPT_ENCODING + ": " + acceptE + HttpShortMessage.CRLF)
				+ ((dnt == null) ? "" : Request.DO_NOT_TRACK + ": " + dnt + HttpShortMessage.CRLF)
				+ ((connection == null) ? "" : Request.CONNECTION + ": " + connection + HttpShortMessage.CRLF)
				+ ((upgradeIR == null) ? "" : Request.UPGRADE_INSECURE_REQUESTS + ": " + upgradeIR + HttpShortMessage.CRLF)
				+ "" + HttpShortMessage.CRLF;  // Empty body
	}
}
