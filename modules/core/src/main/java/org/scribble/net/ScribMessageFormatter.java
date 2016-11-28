package org.scribble.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public interface ScribMessageFormatter
{
	//byte[] toBytes(ScribMessage m) throws IOException;
	@Deprecated
	void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException;
	@Deprecated
	ScribMessage readMessage(DataInputStream dis) throws IOException;
	
	byte[] toBytes(ScribMessage m) throws IOException;

  // Pre and post: bb:put (maybe get would be more intuitive, but Buffers work better with put as default)
	// Returns null if not enough data (FIXME?)
	ScribMessage fromBytes(ByteBuffer bb) throws IOException, ClassNotFoundException;
}
