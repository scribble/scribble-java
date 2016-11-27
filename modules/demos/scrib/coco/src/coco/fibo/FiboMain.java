package coco.fibo;

import coco.fibo.Fibo.Fibonacci.Fibonacci;

public class FiboMain
{
	public static void main(String[] args) throws Exception
	{
		Fibonacci fib = new Fibonacci();
		//new MyB(fib).start();
		new FiboBHandler(fib).start();
		new FiboA(fib).start();
	}
}
