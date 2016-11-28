package demo.bettybook.math;

import static demo.bettybook.math.Math.MathService.MathService.C;

import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SessionEndpoint;

import demo.bettybook.math.Math.MathService.MathService;
import demo.bettybook.math.Math.MathService.roles.C;

public class MathC
{
	public MathC()
	{
	}

	public static void main(String[] args) throws Exception
	{
		MathService sess = new MathService();
		try (SessionEndpoint<MathService, C> se = new MPSTEndpoint<>(sess, C, new ObjectStreamFormatter()))
		{
		}
	}
}
