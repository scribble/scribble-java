package demo.bettybook.math;

import static demo.bettybook.math.Math.MathService.MathService.Add;
import static demo.bettybook.math.Math.MathService.MathService.Bye;
import static demo.bettybook.math.Math.MathService.MathService.C;
import static demo.bettybook.math.Math.MathService.MathService.Mult;
import static demo.bettybook.math.Math.MathService.MathService.Product;
import static demo.bettybook.math.Math.MathService.MathService.S;
import static demo.bettybook.math.Math.MathService.MathService.Sum;
import static demo.bettybook.math.Math.MathService.MathService.Val;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import demo.bettybook.math.Math.MathService.MathService;
import demo.bettybook.math.Math.MathService.channels.S.MathService_S_1;
import demo.bettybook.math.Math.MathService.channels.S.MathService_S_1_Cases;
import demo.bettybook.math.Math.MathService.channels.S.MathService_S_2;
import demo.bettybook.math.Math.MathService.channels.S.MathService_S_2_Cases;
import demo.bettybook.math.Math.MathService.roles.S;

public class MathS1
{
	public MathS1() throws Exception
	{
		run();
	}

	private void run() throws Exception
	{
		Buf<Integer> b1 = new Buf<>();
		Buf<Integer> b2 = new Buf<>();
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				MathService sess = new MathService();
				try (MPSTEndpoint<MathService, S> se = new MPSTEndpoint<>(sess, S, new ObjectStreamFormatter()))
				{
					se.accept(ss, C);

					MathService_S_1 s1 = new MathService_S_1(se);
					Loop: while (true)
					{
						MathService_S_1_Cases c1 = s1.branch(C);
						switch (c1.op)
						{
							case Bye: c1.receive(Bye); break Loop;
							case Val:
							{
								MathService_S_2 s2 = c1.receive(Val, b1);
								MathService_S_2_Cases c2 = s2.branch(C);
								switch (c2.op)
								{
									case Add:  s1 = c2.receive(Add, b2).send(C, Sum, b1.val + b2.val); break;
									case Mult: s1 = c2.receive(Mult, b2).send(C, Product, b1.val * b2.val); break;
								}
								break;
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		new MathS1();
	}
}
