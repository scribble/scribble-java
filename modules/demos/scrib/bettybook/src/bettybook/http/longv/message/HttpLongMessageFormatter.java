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
package bettybook.http.longv.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.scribble.net.ScribMessage;
import org.scribble.net.ScribMessageFormatter;

import bettybook.http.longv.message.client.Accept;
import bettybook.http.longv.message.client.AcceptEncoding;
import bettybook.http.longv.message.client.AcceptLanguage;
import bettybook.http.longv.message.client.Connection;
import bettybook.http.longv.message.client.DoNotTrack;
import bettybook.http.longv.message.client.Host;
import bettybook.http.longv.message.client.RequestLine;
import bettybook.http.longv.message.client.UpgradeInsecureRequests;
import bettybook.http.longv.message.client.UserAgent;
import bettybook.http.longv.message.server.AcceptRanges;
import bettybook.http.longv.message.server.ContentLength;
import bettybook.http.longv.message.server.ContentType;
import bettybook.http.longv.message.server.Date;
import bettybook.http.longv.message.server.ETag;
import bettybook.http.longv.message.server.HttpVersion;
import bettybook.http.longv.message.server.LastModified;
import bettybook.http.longv.message.server.Server;
import bettybook.http.longv.message.server.StrictTransportSecurity;
import bettybook.http.longv.message.server.Vary;
import bettybook.http.longv.message.server.Via;
import bettybook.http.longv.message.server._200;
import bettybook.http.longv.message.server._404;

public class HttpLongMessageFormatter implements ScribMessageFormatter
{
	public static final Charset cs = Charset.forName("UTF8");
	//private static CharsetDecoder cd = cs.newDecoder();
	
	private int len = -1;
	
	public HttpLongMessageFormatter()
	{

	}

	@Override
	public byte[] toBytes(ScribMessage m) throws IOException
	{
		return ((HttpLongMessage) m).toBytes();
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

		int pos = bb.position();
		String front = new String(new byte[] { bb.get(pos), bb.get(pos + 1) }, HttpLongMessageFormatter.cs);
		if (front.equals(HttpLongMessage.CRLF))  // not sound? -- actually, due to sess types it is safe (same reason why interpreting any of these messages without context is sound) -- parsing doesn't have to follow the *full protocol* BNF any more to be sound
		{
			if (this.len == -1)
			{
				return new Body("");
			}
			if (rem < this.len + 2)
			{
				bb.compact();
				return null;
			}
			//String body = readLine(dis) + HttpMessage.CRLF;  // HACK: assumes at least 1 CRLF
			StringBuffer sb = new StringBuffer();
			//for (int i = body.length(); i < this.len; i++)
			for (int i = 0; i < this.len; i++)
			{
				sb.append((char) bb.get());
			}
			this.len = -1;
			//return new Body(body + sb.toString());
			return new Body(sb.toString());
		}

		if (rem < pos + 4)
		{
			bb.compact();
			return null;
		}
		//byte[] bs = new byte[2];
		//dis.readFully(bs);
		front += new String(new byte[] { bb.get(pos + 2), bb.get(pos + 3) }, HttpLongMessageFormatter.cs);
		// FIXME: factor out with HttpMessage op strings
		int code = isStatusCode(front);
		if (code > -1)
		{
			String reason = readLine(bb, pos + 4).trim();  // Whitespace already built into the message classes
			bb.compact();
			if (reason == null)
			{
				return null;
			}
			switch (code)
			{
				case 200: return new _200(reason);
				case 404: return new _404(reason);
				default:  throw new RuntimeException("Unknown status code: " + code);
			}
		}
		else if (front.startsWith(HttpLongMessage.GET))
		{
			String reql = readLine(bb, pos + 4).trim();
			bb.compact();
			if (reql == null)
			{
				return null;
			}
			String target = reql.substring(0, reql.indexOf(' '));
			String vers = reql.substring(reql.indexOf(' ') + 1).trim();
			vers = vers.substring(vers.indexOf('/') + 1);
			return new RequestLine(target, vers);
		}
		else if (front.equals(HttpLongMessage.HTTP))
		{
			//dis.read();  // '/'
			if (rem < pos + 5)
			{
				bb.compact();
				return null;
			}
			String word = readWord(bb, pos + 5);
			bb.compact();
			if (word == null)
			{
				return null;
			}
			return new HttpVersion(word);
		}
		else
		{
			String line = readLine(bb, pos + 4);
			bb.compact();
			if (line == null)
			{
				return null;
			}
			line = front + line;
			int colon = line.indexOf(':');
			if (colon > -1)
			{
				String name = line.substring(0, colon);
				String value = line.substring(colon + 1).trim();  // Whitespace already built into the message classes
				switch (name)
				{
					case HttpLongMessage.HOST: return new Host("value");
					case HttpLongMessage.USER_AGENT: return new UserAgent(value);
					case HttpLongMessage.ACCEPT: return new Accept(value);
					case HttpLongMessage.ACCEPT_LANGUAGE: return new AcceptLanguage(value);
					case HttpLongMessage.ACCEPT_ENCODING: return new AcceptEncoding(value);
					case HttpLongMessage.DO_NOT_TRACK: return new DoNotTrack(Integer.parseInt(value));     
					case HttpLongMessage.CONNECTION: return new Connection(value);
					case HttpLongMessage.UPGRADE_INSECURE_REQUESTS: return new UpgradeInsecureRequests(Integer.parseInt(value));
					
					case HttpLongMessage.DATE: return new Date(value);
					case HttpLongMessage.SERVER: return new Server(value);
					case HttpLongMessage.STRICT_TRANSPORT_SECURITY: return new StrictTransportSecurity(value);
					case HttpLongMessage.LAST_MODIFIED: return new LastModified(value);
					case HttpLongMessage.ETAG: return new ETag(value);
					case HttpLongMessage.ACCEPT_RANGES: return new AcceptRanges(value);
					case HttpLongMessage.CONTENT_LENGTH:
					{
						len = Integer.parseInt(value.trim());
						return new ContentLength(len);
					}
					case HttpLongMessage.VARY: return new Vary(value);
					case HttpLongMessage.CONTENT_TYPE: return new ContentType(value);
					case HttpLongMessage.VIA: return new Via(value);
					default: throw new RuntimeException("Cannot parse header field: " + line);
				}
			}
			else
			{
				throw new RuntimeException("Cannot parse message: " + line);
			}
		}
	}
	
	private static String readLine(ByteBuffer bb, int i) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		for (int limit = bb.limit(); i <= limit; )
		{
			char c = (char) bb.get(i++);  // readChar is not the same
			sb.append(c);
			if (c == '\r')
			{
				if (i > limit)
				{
					return null;
				}
				c = (char) bb.get(i++);
				sb.append(c);
				if (c == '\n')
				{
					bb.position(i);
					return sb.substring(0, sb.length() - 2).toString();	
				}
			}
		}
		return null;
	}
	
	private static String readWord(ByteBuffer bb, int i) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		for (int limit = bb.limit(); i <= limit; )
		{
			char c = (char) bb.get(i++);
			if (c == ' ')
			{
				bb.position(i);
				return sb.toString();	
			}
			sb.append(c);
		}
		return null;
	}
	
	//private int isStatusCode(byte[] bs)
	private int isStatusCode(String front)
	{
		String code = "";
		for (int i = 0; i < 4; i++)
		{
			//char c = (char) bs[i];
			char c = front.charAt(i);
			if (i < 3)
			{
				if (c < '0' || c > '9')
				{
					return -1;
				}
				code += c;
			}
			else
			{
				if (c != ' ')
				{
					return -1;
				}
			}
		}
		return Integer.parseInt(code);
	}



	// FIXME: delete
	@Deprecated @Override
	public void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException
	{
		dos.write(((HttpLongMessage) m).toBytes());
		dos.flush();
	}

	@Deprecated @Override
	public ScribMessage readMessage(DataInputStream dis) throws IOException
	{
		byte[] bs = new byte[2];
		dis.readFully(bs);
		String front = new String(bs, HttpLongMessageFormatter.cs);
		if (front.equals(HttpLongMessage.CRLF))  // not sound? -- actually, due to sess types it is safe (same reason why interpreting any of these messages without context is sound) -- parsing doesn't have to follow the *full protocol* BNF any more to be sound
		{
			if (this.len == -1)
			{
				return new Body("");
			}
			//String body = readLine(dis) + HttpMessage.CRLF;  // HACK: assumes at least 1 CRLF
			StringBuffer sb = new StringBuffer();
			//for (int i = body.length(); i < this.len; i++)
			for (int i = 0; i < this.len; i++)
			{
				sb.append((char) dis.read());
			}
			this.len = -1;
			//return new Body(body + sb.toString());
			return new Body(sb.toString());
		}

		bs = new byte[2];
		dis.readFully(bs);
		front += new String(bs, HttpLongMessageFormatter.cs);
		// FIXME: factor out with HttpMessage op strings
		int code = isStatusCode(front);
		if (code > -1)
		{
			String reason = readLine(dis).trim();  // Whitespace already built into the message classes
			switch (code)
			{
				case 200: return new _200(reason);
				case 404: return new _404(reason);
				default:  throw new RuntimeException("Unknown status code: " + code);
			}
		}
		else if (front.startsWith(HttpLongMessage.GET))
		{
			String reql = readLine(dis).trim();
			String target = reql.substring(0, reql.indexOf(' '));
			String vers = reql.substring(reql.indexOf(' ') + 1).trim();
			vers = vers.substring(vers.indexOf('/') + 1);
			return new RequestLine(target, vers);
		}
		else if (front.equals(HttpLongMessage.HTTP))
		{
			dis.read();  // '/'
			String word = readWord(dis);
			return new HttpVersion(word);
		}
		else
		{
			String line = front + readLine(dis);
			int colon = line.indexOf(':');
			if (colon > -1)
			{
				String name = line.substring(0, colon);
				String value = line.substring(colon + 1).trim();  // Whitespace already built into the message classes
				switch (name)
				{
					case HttpLongMessage.HOST: return new Host("value");
					case HttpLongMessage.USER_AGENT: return new UserAgent(value);
					case HttpLongMessage.ACCEPT: return new Accept(value);
					case HttpLongMessage.ACCEPT_LANGUAGE: return new AcceptLanguage(value);
					case HttpLongMessage.ACCEPT_ENCODING: return new AcceptEncoding(value);
					case HttpLongMessage.DO_NOT_TRACK: return new DoNotTrack(Integer.parseInt(value));     
					case HttpLongMessage.CONNECTION: return new Connection(value);
					
					case HttpLongMessage.DATE: return new Date(value);
					case HttpLongMessage.SERVER: return new Server(value);
					case HttpLongMessage.LAST_MODIFIED: return new LastModified(value);
					case HttpLongMessage.ETAG: return new ETag(value);
					case HttpLongMessage.ACCEPT_RANGES: return new AcceptRanges(value);
					case HttpLongMessage.CONTENT_LENGTH:
					{
						len = Integer.parseInt(value.trim());
						return new ContentLength(len);
					}
					case HttpLongMessage.VARY: return new Vary(value);
					case HttpLongMessage.CONTENT_TYPE: return new ContentType(value);
					case HttpLongMessage.VIA: return new Via(value);
					default: throw new RuntimeException("Cannot parse header field: " + line);
				}
			}
			else
			{
				throw new RuntimeException("Cannot parse message: " + line);
			}
		}
	}
	
	private static String readLine(DataInputStream dis) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		for (; true; )
		{
			char c = (char) dis.read();  // readChar is not the same
			sb.append(c);
			if (c == '\r')
			{
				c = (char) dis.read();
				sb.append(c);
				if (c == '\n')
				{
					return sb.substring(0, sb.length() - 2).toString();	
				}
			}
		}
	}
	
	private static String readWord(DataInputStream dis) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		for (; true; )
		{
			char c = (char) dis.read();
			if (c == ' ')
			{
				return sb.toString();	
			}
			sb.append(c);
		}
	}
}
