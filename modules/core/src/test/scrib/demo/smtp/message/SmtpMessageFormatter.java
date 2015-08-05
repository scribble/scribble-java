package demo.smtp.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.scribble.net.ScribMessage;
import org.scribble.net.ScribMessageFormatter;

import demo.smtp.message.server._220;
import demo.smtp.message.server._235;
import demo.smtp.message.server._250;
import demo.smtp.message.server._250_;
import demo.smtp.message.server._354;
import demo.smtp.message.server._501;
import demo.smtp.message.server._535;

// Currently client-oriented?
public class SmtpMessageFormatter implements ScribMessageFormatter
{
	public static Charset cs = Charset.forName("UTF8");
	//private static CharsetDecoder cd = cs.newDecoder();

	public SmtpMessageFormatter()
	{

	}

	@Override
	public void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException
	{
		dos.write(((SmtpMessage) m).toBytes());
		dos.flush();
	}

	@Override
	public SmtpMessage readMessage(DataInputStream dis) throws IOException
	{
		byte[] bs = new byte[2];
		dis.readFully(bs);
		String front = new String(bs, SmtpMessageFormatter.cs);
		if (front.equals(SmtpMessage.CRLF))
		{
			throw new RuntimeException("TODO: ");
		}
		bs = new byte[2];
		dis.readFully(bs);
		front += new String(bs, SmtpMessageFormatter.cs);
		int code = isStatusCode(front);
		if (code > -1)
		{
			String body = readLine(dis).trim();  // Whitespace already built into the message classes
			switch (code)
			{
				case 220: return new _220(body);
				case 250:
				{
					if (front.charAt(3) == '-')
					{
						return new _250_(body);
					}
					return new _250(body);
				}
				case 235: return new _235(body);
				case 535: return new _535(body);
				case 501: return new _501(body);
				case 354: return new _354(body);
				default:  throw new RuntimeException("Unknown status code: " + code);
			}
		}
		/*else if (front.startsWith(HttpMessage.GET))
		{
		}*/
		else
		{
			String line = front + readLine(dis);
			throw new RuntimeException("Cannot parse message: " + line);
		}
	}
	
	// Duplicated from HttpMessageFormatter  // FIXME: factor out
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
				if (c != ' ' && c != '-')  // HACK: hypen
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

	/* // Pre: flipped ready for reading
	@Override
	public SmtpMessage readMessage(DataInputStream dis) throws IOException
	{
		String header = getHeader(dis);
		switch (header)
		{
			// FIXME: factor out text constants with Session constants?
			case "220 ":
			{
				return new _220(getBody(dis));
			}
			case "235 ":
			{
				return new _235(getBody(dis));
			}
			case "250-":
			{
				return new _250_(getBody(dis));
			}
			case "250 ":
			{
				return new _250(getBody(dis));
			}
			case "354 ":
			{
				return new _354(getBody(dis));
			}
			case "501 ":
			{
				return new _501(getBody(dis));
			}
			case "535 ":
			{
				return new _535(getBody(dis));
			}
			default:
			{
				throw new RuntimeException("Unknown header: " + header);
			}
		}
	}
	
	// *4* char headers (3 digits, dash or space)
	private String getHeader(DataInputStream dis) throws IOException
	{
		readMinimumBytes(dis, 4);

		int limit = bb.limit();
		bb.limit(4);
		String header = cd.decode(bb).toString();  // updates bb position
		bb.limit(limit);
		bb.compact();
		bb.flip();
		return header;
	}
	
	// Reads up to \r\n inclusive; doesn't return the \r\n
	private String getBody(DataInputStream dis) throws IOException
	{
		int min = 2;
		readMinimumBytes(dis, min);  // Min should be more?
		while (true)
		{
			String all = cd.decode(bb).toString();  // updates bb position
			if (all.contains("\n"))  // FIXME: didn't check exactly \r\n
			{
				int i = all.indexOf("\n");
				String body = all.substring(0, i - 1);
				bb.position(i + 1);  // \r\n  -- ASCII 1-to-1 byte-char index
				bb.compact();
				bb.flip();
				return body;
			}
			bb.rewind();
			readMinimumBytes(dis, ++min);  // FIXME: buffered readLine would be more efficient
		}
	}

	// FIXME: min should mean min fresh (i.e. not including bb cached -- there currently is no cache: is.available doesn't work for SSLSocket, always returns 0)
	private void readMinimumBytes(DataInputStream dis, int min) throws IOException
	{
		for (int remaining = bb.remaining(); remaining < min; remaining = bb.remaining())
		{
			bb.compact();
			bb.put((byte) dis.read());  // FIXME: check for buffer overflow
			bb.flip();
		}
	}*/
}
