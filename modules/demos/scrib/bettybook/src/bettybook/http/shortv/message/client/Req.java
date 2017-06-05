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
package bettybook.http.shortv.message.client;

import bettybook.http.shortv.HttpShort.Http.Http;
import bettybook.http.shortv.message.HttpShortMessage;

public class Req extends HttpShortMessage {

	private static final long serialVersionUID = 1L;

	public static final String HOST = "Host";
	public static final String USER_AGENT = "User-Agent";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String DO_NOT_TRACK = "DNT";     
	public static final String CONNECTION = "Connection";
	public static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";

	public Req(String get, String http, String host, String userA, String accept, String acceptL, String acceptE, String dnt, String connection, String upgradeIR) {
		super(Http.Req, getHeadersAndBody(get, http, host, userA, accept, acceptL, acceptE, dnt, connection, upgradeIR));
	}
	
	public Req(String get, String http, String host) {
		this(get, http, host, null, null, null, null, null, null, null);
	}

	protected static String getHeadersAndBody(String get, String http, String host, String userA, String accept, String acceptL, String acceptE, String dnt, String connection, String upgradeIR) {
		return " "
				+ get + " " + HttpShortMessage.HTTP + "/" + http + HttpShortMessage.CRLF
				+ Req.HOST + ": " + host + HttpShortMessage.CRLF
				+ ((userA == null) ? "" : Req.USER_AGENT + ": " + userA + HttpShortMessage.CRLF)
				+ ((accept == null) ? "" : Req.ACCEPT + ": " + accept + HttpShortMessage.CRLF)
				+ ((acceptL == null) ? "" : Req.ACCEPT_LANGUAGE + ": " + acceptL + HttpShortMessage.CRLF)
				+ ((acceptE == null) ? "" : Req.ACCEPT_ENCODING + ": " + acceptE + HttpShortMessage.CRLF)
				+ ((dnt == null) ? "" : Req.DO_NOT_TRACK + ": " + dnt + HttpShortMessage.CRLF)
				+ ((connection == null) ? "" : Req.CONNECTION + ": " + connection + HttpShortMessage.CRLF)
				+ ((upgradeIR == null) ? "" : Req.UPGRADE_INSECURE_REQUESTS + ": " + upgradeIR + HttpShortMessage.CRLF)
				+ "" + HttpShortMessage.CRLF;  // Empty body
	}
}
