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
package org.scribble.runtime.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ObjectStreamFormatter implements ScribMessageFormatter
{
	public ObjectStreamFormatter()
	{

	}

	@Override
	//public byte[] toBytes(ScribMessage m) throws IOException
	public void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException
	{
		throw new RuntimeException("Deprecated");
		/*byte[] bs = serialize(m);
		dos.writeInt(bs.length);
		dos.write(bs);
		dos.flush();*/
	}

	@Override
	public ScribMessage readMessage(DataInputStream dis) throws IOException
	{
		throw new RuntimeException("Deprecated");
		/*try
		{
			int len = dis.readInt();
			byte[] bs = new byte[len];
			dis.readFully(bs);
			return deserialize(bs);
		}
		catch (ClassNotFoundException cnfe)
		{
			throw new IOException(cnfe);
		}*/
	}

	@Override
	public byte[] toBytes(ScribMessage m) throws IOException
	{
		byte[] body = serialize(m);
		byte[] header = ByteBuffer.allocate(4).putInt(body.length).array();  // FIXME: buffer alloc
		byte[] bs = new byte[header.length + body.length];
		System.arraycopy(header, 0, bs, 0, header.length);
		System.arraycopy(body, 0, bs, 4, body.length);
		return bs;
	}

	@Override
	public ScribMessage fromBytes(ByteBuffer bb) throws IOException, ClassNotFoundException
	{
		// Pre: bb:put
		bb.flip();
		if (bb.remaining() <= 4)
		{
			bb.compact();
			return null;
		}
		byte[] header = Arrays.copyOf(bb.array(), 4);
		int size = ByteBuffer.wrap(header).getInt();
		if (bb.remaining() < (4 + size))
		{
			bb.compact();
			return null;
		}
		bb.position(bb.position() + 4);  // position is always 0 here?
		byte[] bs = new byte[size];
		bb.get(bs);
		ScribMessage m = deserialize(bs);
		bb.compact();  // Post: bb:put
		return m;
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
