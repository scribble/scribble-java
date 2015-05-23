package org.scribble2.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ScribMessageFormatter
{
	//byte[] toBytes(ScribMessage m) throws IOException;
	void writeMessage(DataOutputStream dos, ScribMessage m) throws IOException;
	ScribMessage readMessage(DataInputStream dis) throws IOException;
}
