package org.scribble.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ObjectStreamFormatter implements ScribMessageFormatter
{
	public ObjectStreamFormatter()
	{

	}

	@Override
	//public byte[] toBytes(ScribMessage m) throws IOException
	public void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException
	{
		byte[] bs = serialize(m);
		dos.writeInt(bs.length);
		dos.write(bs);
		dos.flush();
	}

	@Override
	public ScribMessage readMessage(DataInputStream dis) throws IOException
	{
		try
		{
			int len = dis.readInt();
			byte[] bs = new byte[len];
			dis.readFully(bs);
			return deserialize(bs);
		}
		catch (ClassNotFoundException cnfe)
		{
			throw new IOException(cnfe);
		}
	}

	private static byte[] serialize(Object o) throws IOException
	{
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream())
		{
			try (ObjectOutput out = new ObjectOutputStream(bos))
			{
				out.writeObject(o);
				return bos.toByteArray();
			}
		}
	}

	private static ScribMessage deserialize(byte[] bs) throws IOException, ClassNotFoundException
	{
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bs))
		{
			try (ObjectInput in = new ObjectInputStream(bis))
			{
				return (ScribMessage) in.readObject(); 
			}	
		}
	}
}
