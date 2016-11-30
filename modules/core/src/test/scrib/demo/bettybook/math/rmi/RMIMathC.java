package demo.bettybook.math.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIMathC
{
	public static void main(String[] args) throws Exception
	{
		if (System.getSecurityManager() == null)
		{
			System.setSecurityManager(new SecurityManager());
		}

		String name = "MathService";
		Registry registry = LocateRegistry.getRegistry(args[0]);
		RMIMath mathS = (RMIMath) registry.lookup(name);

		int i = 5;
		int res = i;
		while (i > 1)
		{
			mathS.val(i);
			i = mathS.add(-1);
			mathS.val(res);
			res = mathS.mult(i);
		}
		mathS.bye();

		System.out.println("Facto: " + res);
	}
}
