package demo.http.shortvers.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.scribble.net.ScribMessage;
import org.scribble.net.ScribMessageFormatter;

import demo.http.shortvers.message.client.Request;
import demo.http.shortvers.message.server.Response;

public class HttpMessageFormatter implements ScribMessageFormatter
{
	public static final Charset cs = Charset.forName("UTF8");
	//private static CharsetDecoder cd = cs.newDecoder();
	
	public HttpMessageFormatter()
	{

	}

	@Override
	public byte[] toBytes(ScribMessage m) throws IOException
	{
		return ((HttpMessage) m).toBytes();
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

		//int pos = bb.position();
		//String front = new String(new byte[] { bb.get(pos), bb.get(pos + 1) }, HttpMessageFormatter.cs);
		String curr = new String(bb.array(), HttpMessageFormatter.cs);

		String endOfHeaders = HttpMessage.CRLF + HttpMessage.CRLF;
		if (curr.contains(endOfHeaders))
		{
			if (curr.contains(Response.CONTENT_LENGTH))
			{
				int eoh = curr.indexOf(endOfHeaders);
				if (eoh == -1)
				{
					return null;
				}
				String contentLenSplit = curr.substring(curr.indexOf(Response.CONTENT_LENGTH));
				int len = Integer.parseInt(contentLenSplit.substring(Response.CONTENT_LENGTH.length()+2, contentLenSplit.indexOf('\r')).trim());
				String body = curr.substring(eoh+4);  // FIXME: could be index out of bounds
				if (body.length() < len + 2)
				{
					//bb.compact();
					return null;
				}
				byte[] bs = new byte[bb.remaining()];  // FIXME: hardcoded Response parsing based on presence of Content-Length
				bb.get(bs);
				bb.compact();
				return parseResponse(new String(bs, HttpMessageFormatter.cs));
			}
			else
			{
				//if (!curr.endsWith(endOfHeaders))
				/*if (!curr.endsWith("\r\n"))  // FIXME: terminal null char?
				{
					// FIXME: unsound
					System.out.println("111:\n" + curr.replace("\r", "\\r").replace("\n", "\\n\r\n").replace(" ", "_"));
					//System.out.println("111: " + (int)curr.charAt(curr.length()-1));
					throw new IOException("No Content-Length specified (Transfer-Encoding not supported).");
				}*/
				byte[] bs = new byte[bb.remaining()];
				bb.get(bs);
				bb.compact();
				return parseRequest(new String(bs, HttpMessageFormatter.cs));  // FIXME: assuming empty-body Request if no Content-Length
			}
		}
		return null;
	}

	// Assumes no body
	private static HttpMessage parseRequest(String msg)
	{
		String get = "";
		String http = "";
		String host = "";
		String userA = "";
		String accept = "";
		String acceptL = "";
		String acceptE = "";
		String dnt = "";
		String connection = "";

		for (boolean eoh = false; !eoh; )
		{
			//if (msg.startsWith(HttpMessage.CRLF + HttpMessage.CRLF))
			if (msg.startsWith(HttpMessage.CRLF))  // First CRLF already trimmed after last header
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
			if (msg.startsWith(HttpMessage.GET))  // FIXME
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
				switch (header)
				{
					case Request.HOST: host = msg.substring(0, j); break;
					case Request.USER_AGENT: userA = msg.substring(0, j); break;
					case Request.ACCEPT: accept = msg.substring(0, j); break;
					case Request.ACCEPT_LANGUAGE: acceptL = msg.substring(0, j); break;
					case Request.ACCEPT_ENCODING: acceptE = msg.substring(0, j); break;
					case Request.DO_NOT_TRACK: dnt = msg.substring(0, j); break;     
					case Request.CONNECTION: connection = msg.substring(0, j); break;
					default: throw new RuntimeException("Cannot parse header field: " + msg.substring(0, j));
				}
				msg = msg.substring(j+2);
			}
		}
		if (!msg.isEmpty())
		{
			throw new RuntimeException("Shouldn't get in here: " + msg);
		}
		return new Request(get, http, host, userA, accept, acceptL, acceptE, dnt, connection);
	}
	
	private static HttpMessage parseResponse(String msg)
	{
		String httpv = "";
		String date = "";
		String server = "";
		String strictTS = "";
		String lastMod = "";
		String eTag = "";
		String acceptR = "";
		String contentL = "";
		String vary = "";
		String contentT = "";
		String via = "";
		String body = "";

		for (boolean eoh = false; !eoh; )
		{
			//if (msg.startsWith(HttpMessage.CRLF + HttpMessage.CRLF))
			if (msg.startsWith(HttpMessage.CRLF))  // First CRLF already trimmed after last header
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
			if (msg.startsWith(HttpMessage.HTTP))  // FIXME
			{
				int j = msg.indexOf('\r');
				httpv = msg.substring(msg.indexOf('/')+1, j);
				msg = msg.substring(j+2);
			}
			else
			{
				String header = msg.substring(0, i);
				int j = msg.indexOf("\r");
				switch (header)
				{
					case Response.DATE: date = msg.substring(0, j); break;
					case Response.SERVER: server = msg.substring(0, j); break;
					case Response.STRICT_TRANSPORT_SECURITY: strictTS = msg.substring(0, j); break;
					case Response.LAST_MODIFIED: lastMod = msg.substring(0, j); break;
					case Response.ETAG: eTag = msg.substring(0, j); break;
					case Response.ACCEPT_RANGES: acceptR = msg.substring(0, j); break;
					case Response.CONTENT_LENGTH: contentL = msg.substring(0, j); break;
					case Response.VARY: vary = msg.substring(0, j); break;
					case Response.CONTENT_TYPE: contentT = msg.substring(0, j); break;
					case Response.VIA: via = msg.substring(0, j); break;
					default: throw new RuntimeException("Cannot parse header field: " + msg.substring(0, msg.indexOf('\r')));
				}
				msg = msg.substring(j+2);
			}
		}
		body = msg;
		return new Response(httpv, date, server, strictTS, lastMod, eTag, acceptR, contentL, vary, contentT, via, body);
	}

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
