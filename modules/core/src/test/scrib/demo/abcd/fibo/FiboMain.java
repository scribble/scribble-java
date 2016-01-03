package demo.abcd.fibo;

import demo.abcd.fibo.Fibo.Fibonacci.Fibonacci;

public class FiboMain
{
	public static void main(String[] args) throws Exception
	{
		Fibonacci fib = new Fibonacci();
		new MyB(fib).start();
		//new MyBHandler(fib).start();
		new MyA(fib).start();
	}
}
