package test.http.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.scribble2.net.ScribMessage;
import org.scribble2.net.ScribMessageFormatter;

// Client-side only
public class HttpMessageFormatter implements ScribMessageFormatter
{
	protected static Charset cs = Charset.forName("UTF8");
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
		byte[] bs = new byte[4];
		dis.readFully(bs);
		String front = new String(bs, HttpMessageFormatter.cs);
		// FIXME: factor out with HttpMessage op strings
		int code = isStatusCode(bs);
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
		else if (front.equals(HttpMessage.HTTP))
		{
			dis.read();
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
				if (line.startsWith(HttpMessage.CRLF))  // HACK: not sound -- actually, maybe due to sess types it is safe (same reason why interpreting any of these messages without context is sound) -- parsing doesn't have to follow the *full protocol* BNF any more to be sound
				{
					String body = line.substring(2) + HttpMessage.CRLF;
					StringBuffer sb = new StringBuffer();
					for (int i = body.length(); i < this.len; i++)
					{
						sb.append((char) dis.read());
					}
					return new Body(body + sb.toString());
				}
				throw new RuntimeException("Cannot parse message: " + line);
			}
		}
	}
	
	private int isStatusCode(byte[] bs)
	{
		String code = "";
		for (int i = 0; i < 4; i++)
		{
			char c = (char) bs[i];
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
