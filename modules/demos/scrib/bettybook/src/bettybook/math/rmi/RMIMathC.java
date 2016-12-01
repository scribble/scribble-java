//$ java -cp modules/core/target/classes/ -Djava.security.policy=file:/C:/cygwin/home/Raymond/code/scribble/github-rhu1/scribble-java/modules/demos/scrib/bettybook/src/bettybook/math/rmi/server.policy bettybook.math.rmi.RMIMathC

package bettybook.math.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIMathC
{
	public static void main(String[] args) throws Exception
	{
		//System.setProperty("java.security.policy", "file:/C:/cygwin/home/Raymond/code/scribble/github-rhu1/scribble-java/modules/demos/scrib/bettybook/src/bettybook/math/rmi/server.policy");
		//if (System.getSecurityManager() == null) { System.setSecurityManager(new SecurityManager()); }

		Registry registry = LocateRegistry.getRegistry(8888);
		RMIMath mathS = (RMIMath) registry.lookup("MathService");

		int i = 5;
		int res = i;
		while (i > 1)
		{
			mathS.Val(i);
			i = mathS.Add(-1);
			mathS.Val(res);
			res = mathS.Mult(i);
		}
		mathS.Bye();

		System.out.println("Facto: " + res);
	}
}
