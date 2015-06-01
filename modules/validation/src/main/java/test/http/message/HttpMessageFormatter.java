package test.http.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.scribble2.net.ScribMessage;
import org.scribble2.net.ScribMessageFormatter;

import test.http.message.client.Accept;
import test.http.message.client.AcceptEncoding;
import test.http.message.client.AcceptLanguage;
import test.http.message.client.Connection;
import test.http.message.client.DoNotTrack;
import test.http.message.client.Host;
import test.http.message.client.RequestLine;
import test.http.message.client.UserAgent;
import test.http.message.server.AcceptRanges;
import test.http.message.server.ContentLength;
import test.http.message.server.ContentType;
import test.http.message.server.Date;
import test.http.message.server.ETag;
import test.http.message.server.HttpVersion;
import test.http.message.server.LastModified;
import test.http.message.server.Server;
import test.http.message.server.Vary;
import test.http.message.server.Via;
import test.http.message.server._200;
import test.http.message.server._404;

// Client-side only
public class HttpMessageFormatter implements ScribMessageFormatter
{
	public static final Charset cs = Charset.forName("UTF8");
	//private static CharsetDecoder cd = cs.newDecoder();
	
	private int len = -1;
	
	public HttpMessageFormatter()
	{

	}

	@Override
	public void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException
	{
		dos.write(((HttpMessage) m).toBytes());
		dos.flush();
	}

	@Override
	public ScribMessage readMessage(DataInputStream dis) throws IOException
	{
		byte[] bs = new byte[2];
		dis.readFully(bs);
		String front = new String(bs, HttpMessageFormatter.cs);
		if (front.equals(HttpMessage.CRLF))  // HACK: not sound -- actually, maybe due to sess types it is safe (same reason why interpreting any of these messages without context is sound) -- parsing doesn't have to follow the *full protocol* BNF any more to be sound
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
		front += new String(bs, HttpMessageFormatter.cs);
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
		else if (front.startsWith(HttpMessage.GET))
		{
			String reql = readLine(dis).trim();
			String target = reql.substring(0, reql.indexOf(' '));
			String vers = reql.substring(reql.indexOf(' ') + 1).trim();
			vers = vers.substring(vers.indexOf('/') + 1);
			return new RequestLine(target, vers);
		}
		else if (front.equals(HttpMessage.HTTP))
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
					case HttpMessage.HOST: return new Host("value");
					case HttpMessage.USER_AGENT: return new UserAgent(value);
					case HttpMessage.ACCEPT: return new Accept(value);
					case HttpMessage.ACCEPT_LANGUAGE: return new AcceptLanguage(value);
					case HttpMessage.ACCEPT_ENCODING: return new AcceptEncoding(value);
					case HttpMessage.DO_NOT_TRACK: return new DoNotTrack(Integer.parseInt(value));     
					case HttpMessage.CONNECTION: return new Connection(value);
					
					case HttpMessage.DATE: return new Date(value);
					case HttpMessage.SERVER: return new Server(value);
					case HttpMessage.LAST_MODIFIED: return new LastModified(value);
					case HttpMessage.ETAG: return new ETag(value);
					case HttpMessage.ACCEPT_RANGES: return new AcceptRanges(value);
					case HttpMessage.CONTENT_LENGTH:
					{
						len = Integer.parseInt(value.trim());
						return new ContentLength(len);
					}
					case HttpMessage.VARY: return new Vary(value);
					case HttpMessage.CONTENT_TYPE: return new ContentType(value);
					case HttpMessage.VIA: return new Via(value);
					default: throw new RuntimeException("Cannot parse header field: " + line);
				}
			}
			else
			{
				throw new RuntimeException("Cannot parse message: " + line);
			}
		}
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
