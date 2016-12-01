//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/demos/target/classes';'modules/core/src/test/scrib demo.bettybook.math.rmi

package demo.bettybook.math.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIMathS implements RMIMath
{
	private int x;

	public void Val(Integer x) throws RemoteException
	{
		this.x = x;
	}

	public void Bye() throws RemoteException
	{

	}

	public Integer Add(Integer y) throws RemoteException
	{
		return this.x + y;
	}

	public Integer Mult(Integer y) throws RemoteException
	{
		return this.x * y;
	}

	public static void main(String[] args) throws Exception
	{
		System.setProperty("java.security.policy","file:/C:/cygwin/home/Raymond/code/scribble/github-rhu1/scribble-java/modules/core/src/test/scrib/demo/bettybook/math/rmi/server.policy");

		Registry registry = LocateRegistry.createRegistry(8888);
		//System.setProperty("java.security.policy","file:./server.policy");  // N.B. not file:/./server.policy

		if (System.getSecurityManager() == null)
		{
			System.setSecurityManager(new SecurityManager());
		}

		String name = "MathService";
		RMIMath engine = new RMIMathS();
		RMIMath stub = (RMIMath) UnicastRemoteObject.exportObject(engine, 0);
		//Registry registry = LocateRegistry.getRegistry(8888);
		registry.rebind(name, stub);
		
		System.out.println("RMI server running.");
	}
}
