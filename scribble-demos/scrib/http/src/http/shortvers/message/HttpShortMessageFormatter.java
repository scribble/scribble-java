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
package http.shortvers.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.scribble.runtime.net.ScribMessage;
import org.scribble.runtime.net.ScribMessageFormatter;

import http.shortvers.message.client.Request;
import http.shortvers.message.server.Response;

public class HttpShortMessageFormatter implements ScribMessageFormatter
{
	public static final Charset cs = Charset.forName("UTF8");
	//private static CharsetDecoder cd = cs.newDecoder();
	
	public HttpShortMessageFormatter()
	{

	}

	@Override
	public byte[] toBytes(ScribMessage m) throws IOException
	{
		return ((HttpShortMessage) m).toBytes();
	}

	@Override
	public ScribMessage fromBytes(ByteBuffer bb) throws IOException, ClassNotFoundException
	{
		bb.flip();
		int rem = bb.remaining();
		if (rem < 2)
		{
			bb.compact();
			return null;
		}

		String curr = new String(Arrays.copyOf(bb.array(), bb.remaining()), HttpShortMessageFormatter.cs);
		String endOfHeaders = HttpShortMessage.CRLF + HttpShortMessage.CRLF;
		if (!curr.contains(endOfHeaders))
		{
			bb.compact();
			return null;
		}

		if (curr.contains(Response.CONTENT_LENGTH))
		{
			int eoh = curr.indexOf(endOfHeaders);
			if (eoh == -1)
			{
				bb.compact();
				return null;
			}
			String contentLenSplit = curr.substring(curr.indexOf(Response.CONTENT_LENGTH));
			int len = Integer.parseInt(contentLenSplit.substring(Response.CONTENT_LENGTH.length()+2, contentLenSplit.indexOf('\r')).trim());
			if (curr.length() < eoh+4)
			{
				bb.compact();
				return null;
			}
			String body = curr.substring(eoh+4);
			if (body.getBytes(HttpShortMessageFormatter.cs).length < len)
			{
				bb.compact();
				return null;
			}
			byte[] bs = new byte[bb.remaining()];  // FIXME: hardcoded Response parsing based on presence of Content-Length
			bb.get(bs);
			bb.compact();
			return parseResponse(new String(bs, HttpShortMessageFormatter.cs));
		}
		else
		{
			byte[] bs = new byte[bb.remaining()];
			bb.get(bs);
			bb.compact();
			return parseRequest(new String(bs, HttpShortMessageFormatter.cs));  // FIXME: assuming empty-body Request if no Content-Length
		}
	}

	// Assumes no body
	private static HttpShortMessage parseRequest(String msg)
	{
		String get = null;
		String http = null;
		String host = null;
		String userA = null;
		String accept = null;
		String acceptL = null;
		String acceptE = null;
		String dnt = null;
		String connection = null;
		String upgradeIR = null;

		for (boolean eoh = false; !eoh; )
		{
			//if (msg.startsWith(HttpMessage.CRLF + HttpMessage.CRLF))
			if (msg.startsWith(HttpShortMessage.CRLF))  // First CRLF already trimmed after last header
			{
				eoh = true;
				//msg = msg.substring(4);
				msg = msg.substring(2);
				break;
			}

			msg = msg.replace("\\A\\s+", "");
			int i = msg.indexOf(":"); 	
			if (i == -1)
			{
				throw new RuntimeException("Shouldn't get in here: " + msg);
			}
			if (msg.startsWith(HttpShortMessage.GET))  // FIXME
			{
				int j = msg.indexOf(' ');
				get = msg.substring(j+1, msg.indexOf(' ', j+1)).trim();
				j = msg.indexOf('\r');
				http = msg.substring(msg.indexOf('/')+1, j).trim();
				msg = msg.substring(j+2);
			}
			else
			{
				String header = msg.substring(0, i);
				int j = msg.indexOf("\r");
				switch (header)  // FIXME: duplicates not checked
				{
					case Request.HOST: host = msg.substring(i+1, j).trim(); break;
					case Request.USER_AGENT: userA = msg.substring(i+1, j).trim(); break;
					case Request.ACCEPT: accept = msg.substring(i+1, j).trim(); break;
					case Request.ACCEPT_LANGUAGE: acceptL = msg.substring(i+1, j).trim(); break;
					case Request.ACCEPT_ENCODING: acceptE = msg.substring(i+1, j).trim(); break;
					case Request.DO_NOT_TRACK: dnt = msg.substring(i+1, j).trim(); break;     
					case Request.CONNECTION: connection = msg.substring(i+1, j).trim(); break;
					case Request.UPGRADE_INSECURE_REQUESTS: upgradeIR = msg.substring(i+1, j).trim(); break;
					default: throw new RuntimeException("Cannot parse header field: " + msg.substring(0, j));
				}
				msg = msg.substring(j+2);
			}
		}
		if (!msg.isEmpty())
		{
			throw new RuntimeException("Shouldn't get in here: " + msg);
		}
		return new Request(get, http, host, userA, accept, acceptL, acceptE, dnt, connection, upgradeIR);
	}
	
	private static HttpShortMessage parseResponse(String msg)
	{
		String httpv = null;
		String ack = null;
		String date = null;
		String server = null;
		String strictTS = null;
		String lastMod = null;
		String eTag = null;
		String acceptR = null;
		String contentL = null;
		String vary = null;
		String contentT = null;
		String via = null;
		String body = null;

		for (boolean eoh = false; !eoh; )
		{
			//if (msg.startsWith(HttpMessage.CRLF + HttpMessage.CRLF))
			if (msg.startsWith(HttpShortMessage.CRLF))  // First CRLF already trimmed after last header
			{
				eoh = true;
				//msg = msg.substring(4);
				msg = msg.substring(2);
				break;
			}

			msg = msg.trim();
			int i = msg.indexOf(":"); 	
			if (i == -1)
			{
				throw new RuntimeException("Shouldn't get in here: " + msg);
			}
			if (msg.startsWith(HttpShortMessage.HTTP))  // FIXME
			{
				int j = msg.indexOf(' ');
				httpv = msg.substring(msg.indexOf('/')+1, j);
				
				int k = msg.indexOf('\r');
				ack = msg.substring(j+1, k);
				/*if (!ack.equals("200 OK"))
				{
					if (!ack.startsWith("404"))
					{
						throw new RuntimeException("[TODO]: " + msg);
					}
				}
				ack = "OK";  // Hardcoded*/
				msg = msg.substring(k+2);
			}
			else
			{
				String header = msg.substring(0, i);
				int j = msg.indexOf("\r");
				switch (header)
				{
					case Response.DATE: date = msg.substring(i+1, j).trim(); break;
					case Response.SERVER: server = msg.substring(i+1, j).trim(); break;
					case Response.STRICT_TRANSPORT_SECURITY: strictTS = msg.substring(i+1, j).trim(); break;
					case Response.LAST_MODIFIED: lastMod = msg.substring(i+1, j).trim(); break;
					case Response.ETAG: eTag = msg.substring(i+1, j).trim(); break;
					case Response.ACCEPT_RANGES: acceptR = msg.substring(i+1, j).trim(); break;
					case Response.CONTENT_LENGTH: contentL = msg.substring(i+1, j).trim(); break;
					case Response.VARY: vary = msg.substring(i+1, j).trim(); break;
					case Response.CONTENT_TYPE: contentT = msg.substring(i+1, j).trim(); break;
					case Response.VIA: via = msg.substring(i+1, j).trim(); break;
					default:
						//throw new RuntimeException("Cannot parse header field: " + msg.substring(0, msg.indexOf('\r')));
						System.err.println("[Warning] Attempting to skip over response field: " + header + "\n" + msg.substring(i+1, j).trim());
				}
				msg = msg.substring(j+2);
			}
		}
		body = msg;
		return new Response(httpv, ack, date, server, strictTS, lastMod, eTag, acceptR, contentL, vary, contentT, via, body);
	}

	// FIXME: delete
	@Deprecated @Override
	public void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException
	{
		throw new RuntimeException("Shouldn't get in here: " + m);
	}

	@Deprecated @Override
	public ScribMessage readMessage(DataInputStream dis) throws IOException
	{
		throw new RuntimeException("Shouldn't get in here: ");
	}
}
