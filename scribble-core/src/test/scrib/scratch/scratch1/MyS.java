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
package scratch.scratch1;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import scratch.scratch1.Scratch1.Proto1.Proto1;
import scratch.scratch1.Scratch1.Proto1.channels.S.EndSocket;
import scratch.scratch1.Scratch1.Proto1.channels.S.Proto1_S_1;
import scratch.scratch1.Scratch1.Proto1.channels.S.ioifaces.Branch_S_C_2_Int__C_4a;
import scratch.scratch1.Scratch1.Proto1.channels.S.ioifaces.Handle_S_C_2_Int__C_4a;
import scratch.scratch1.Scratch1.Proto1.channels.S.ioifaces.Receive_S_C_1;
import scratch.scratch1.Scratch1.Proto1.channels.S.ioifaces.Receive_S_C_4b;
import scratch.scratch1.Scratch1.Proto1.channels.S.ioifaces.Select_S_C_3_Int;
import scratch.scratch1.Scratch1.Proto1.channels.S.ioifaces.Succ_In_C_2_Int;
import scratch.scratch1.Scratch1.Proto1.channels.S.ioifaces.Succ_In_C_4a;
import scratch.scratch1.Scratch1.Proto1.ops._2;
import scratch.scratch1.Scratch1.Proto1.ops._4a;
import scratch.scratch1.Scratch1.Proto1.roles.S;

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
						//.branch(Proto1.C, new Handler3<>());
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

/*class Handler implements Proto1_S_2_Handler
{
	@Override
	public void receive(EndSocket schan, _4 op) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Done1-4");
		schan.end();
	}

	@Override
	public void receive(Proto1_S_3 schan, _2 op, Buf<? super Integer> b) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Redo: " + b.val);
		try
		{
			schan.send(Proto1.C, Proto1._3, 456).async(Proto1.C, Proto1._1)
				.branch(Proto1.C, this);
				//.handle(Proto1.C, this);  // "this" handler is not generic enough
		}
		catch (ClassNotFoundException e)
		{
			throw new IOException(e);
		}
	}

	//@Override  // Re-generated State I/O handler won't have the deprecated callback
	public void receive(EndSocket schan, _5 op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("Done1-5");
	}
}
//*/

//class Handler2<Succ extends Succ_In_C_2_Int> implements Handle_S_C_2_Int__C_4<Succ>
class Handler2 implements Handle_S_C_2_Int__C_4a<Succ_In_C_2_Int, Succ_In_C_4a>
{
	@Override
	//public void receive(Proto1_S_3 schan, _2 op, Buf<? super Integer> b) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	//public void receive(Select_S_C_3_Int<Succ> schan, _2 op, Buf<? super Integer> b) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	//public void receive(Succ_In_C_2_Int schan, _2 op, Buf<? super Integer> b) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	public void receive(Succ_In_C_2_Int schan, _2 op, Buf<Integer> b) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("Redo: " + b.val);
		schan.to(Select_S_C_3_Int.cast).send(Proto1.C, Proto1._3, 356).to(Receive_S_C_1.cast).async(Proto1.C, Proto1._1).to(Branch_S_C_2_Int__C_4a.cast).handle(Proto1.C, this);
	}

	@Override
	public void receive(Succ_In_C_4a schan, _4a op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		schan.to(Receive_S_C_4b.cast).receive(Proto1.C, Proto1._4b).to(EndSocket.cast).end();
		System.out.println("Done2-4");
	}

	/*@Override
	public void receive(Succ_In_C_5 schan, _5 op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("Done2-5");
	}*/
}

/*class Handler3<Succ1 extends Succ_In_C_2_Int, Succ2 extends Succ_In_C_4, Succ3 extends Succ_In_C_5> implements Handle_S_C_2_Int__C_4__C_5<Succ1, Succ2, Succ3>
{
	@Override
	public void receive(Succ1 schan, _2 op, Buf<? super Integer> b) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("Redo: " + b.val);
		((Branch_S_C_2_Int__C_4__C_5<Succ1, Succ2, Succ3>) schan.to(Select_S_C_3_Int.cast).send(Proto1.C, Proto1._3, 356).to(Receive_S_C_1.cast).async(Proto1.C, Proto1._1)).branch(Proto1.C, this);
		//schan.to(Select_S_C_3_Int.cast).send(Proto1.C, Proto1._3, 356).to(Receive_S_C_1.cast).async(Proto1.C, Proto1._1).to(Branch_S_C_2_Int__C_4.cast).branch(Proto1.C, this);
		//((Branch_S_C_2_Int__C_4<Succ1, Succ2>) schan.to(Select_S_C_3_Int.cast).send(Proto1.C, Proto1._3, 356).to(Receive_S_C_1.cast).async(Proto1.C, Proto1._1)).branch(Proto1.C, this);
		//schan.to(Select_S_C_3_Int.cast).send(Proto1.C, Proto1._3, 356).to(Receive_S_C_1.cast).async(Proto1.C, Proto1._1).to(Branch_S_C_2_Int__C_4.cast).branch(Proto1.C, this);
	}

	@Override
	public void receive(Succ2 schan, _4 op) throws ScribbleRuntimeException, IOException
	{
		schan.to(EndSocket.cast).end();
		System.out.println("Done3-4");
	}

	@Override
	public void receive(Succ3 schan, _5 op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("Done3-5");
	}
}
//*/
