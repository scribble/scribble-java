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
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.bettybook.math.Math.MathService.MathService;
import demo.bettybook.math.Math.MathService.channels.C.EndSocket;
import demo.bettybook.math.Math.MathService.channels.C.MathService_C_1;
import demo.bettybook.math.Math.MathService.roles.C;

public class MathC
{
	public MathC()
	{
	}

	public static void main(String[] args) throws Exception
	{
		Buf<Integer> b = new Buf<>(5);
		Buf<Integer> i1 = new Buf<>(0);
		Buf<Integer> i2 = new Buf<>(1);
		MathService sess = new MathService();
		try (MPSTEndpoint<MathService, C> se = new MPSTEndpoint<>(sess, C, new ObjectStreamFormatter()))
		{
			se.connect(S, SocketChannelEndpoint::new, "localhost", 8888);

			/*new MathService_C_1(se)
				//.send(S, Val, 2).send(S, Mult, 10) .receive(S, Product, b)
				.send(S, Val, 2).send(S, Neg).receive(S, Neg, b)
				.send(S, Val, b.val).send(S, Mult, 10) .receive(S, Product, b)
				.send(S, Bye);*/

//			facto(new MathService_C_1(se), b).send(S, Bye);
//			System.out.println("Res: " + b.val);
			
			fibo(new MathService_C_1(se), i1, i2, new Buf<>(10));
			System.out.println("Res: " + i1.val);
		}
	}
	
	// Pre: i >= 0
	// Post: i1.val is the i-th Fibonacci number
	private static EndSocket fibo(MathService_C_1 c1, Buf<Integer> i1, Buf<Integer> i2, Buf<Integer> i) throws Exception {
		return (i.val > 0)
			? fibo(
					c1.send(S, Val, i.val).send(S, Add, -1).receive(S, Sum, i)
					  .send(S, Val, i1.val).send(S, Add, i1.val=i2.val).receive(S, Sum, i2),
					i1, i2, i)
			: c1.send(S, Bye);
	}
	
	// Pre: b.val >= 1
	private static MathService_C_1 facto(MathService_C_1 s1, Buf<Integer> b) throws Exception
	{
		if (b.val > 1)
		{
			Buf<Integer> tmp = new Buf<>();
			s1 = facto(s1.send(S, Val, b.val).send(S, Add, -1).receive(S, Sum, tmp), tmp)
						.send(S, Val, b.val).send(S, Mult, tmp.val).receive(S, Product, b);
		}
		return s1;
	}
}
