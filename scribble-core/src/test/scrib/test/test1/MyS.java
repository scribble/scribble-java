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
package test.test1;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import test.test1.Test1.Proto1.Proto1;
import test.test1.Test1.Proto1.channels.S.EndSocket;
import test.test1.Test1.Proto1.channels.S.Proto1_S_1;
import test.test1.Test1.Proto1.channels.S.Proto1_S_2_Handler;
import test.test1.Test1.Proto1.channels.S.Proto1_S_3;
import test.test1.Test1.Proto1.channels.S.ioifaces.Branch_S_C_2_Integer__C_4;
import test.test1.Test1.Proto1.channels.S.ioifaces.Handle_S_C_2_Integer__C_4;
import test.test1.Test1.Proto1.channels.S.ioifaces.Receive_S_C_1;
import test.test1.Test1.Proto1.channels.S.ioifaces.Select_S_C_3_Integer;
import test.test1.Test1.Proto1.channels.S.ioifaces.Succ_In_C_2_Integer;
import test.test1.Test1.Proto1.channels.S.ioifaces.Succ_In_C_4;
import test.test1.Test1.Proto1.ops._2;
import test.test1.Test1.Proto1.ops._4;
import test.test1.Test1.Proto1.roles.S;

public class MyS
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			//Buf<Integer> i1 = new Buf<>();
			//Buf<Integer> i2 = new Buf<>();

			while (true)
			{
				Proto1 foo = new Proto1();
				//SessionEndpoint<S> se = foo.project(Proto1.S, new ObjectStreamFormatter(), ss);
				try (MPSTEndpoint<Proto1, S> se = new MPSTEndpoint<>(foo, Proto1.S, new ObjectStreamFormatter()))
				{
					se.accept(ss, Proto1.C);

					new Proto1_S_1(se).async(Proto1.C, Proto1._1)
						//.branch(Proto1.C, new Handler());
						.handle(Proto1.C, new Handler2());
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

class Handler implements Proto1_S_2_Handler
{
	@Override
	public void receive(EndSocket schan, _4 op) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Done");
		schan.end();
	}

	@Override
	public void receive(Proto1_S_3 schan, _2 op, Buf<? super Integer> b) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Redo: " + b.val);
		try
		{
			schan.send(Proto1.C, Proto1._3, 456).async(Proto1.C, Proto1._1).branch(Proto1.C, this);
		}
		catch (ClassNotFoundException e)
		{
			throw new IOException(e);
		}
	}
}

class Handler2 implements Handle_S_C_2_Integer__C_4<Succ_In_C_2_Integer, Succ_In_C_4>
{
	@Override
	public void receive(Succ_In_C_2_Integer schan, _2 op, Buf<? super Integer> arg1) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("Redo: " + arg1.val);
		schan
			.to(Select_S_C_3_Integer.cast).send(Proto1.C, Proto1._3, 123)
			.to(Receive_S_C_1.cast).async(Proto1.C, Proto1._1)
			.to(Branch_S_C_2_Integer__C_4.cast).handle(Proto1.C, this);
	}

	@Override
	public void receive(Succ_In_C_4 schan, _4 op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		schan.to(EndSocket.cast).end();
		System.out.println("Done");
	}
}
