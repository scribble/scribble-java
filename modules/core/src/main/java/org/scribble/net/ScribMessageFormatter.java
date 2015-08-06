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
	ScribMessage fromBytes(ByteBuffer bb) throws IOException, ClassNotFoundException;
}
