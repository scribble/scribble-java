//$ java -cp modules/core/target/classes/ -Djava.security.policy=file:/C:/cygwin/home/Raymond/code/scribble/github-rhu1/scribble-java/modules/demos/scrib/bettybook/src/bettybook/math/rmi/server.policy bettybook.math.rmi.RMIMathS
//$ java -cp modules/core/target/classes/ -Djava.rmi.server.codebase=file:/C:/cygwin/home/Raymond/code/scribble/github-rhu1/scribble-java/modules/demos/scrib/bettybook/target/classes/ -Djava.security.policy=file:/C:/cygwin/home/Raymond/code/scribble/github-rhu1/scribble-java/modules/core/src/test/scrib/demo/bettybook/math/rmi/server.policy bettybook.math.rmi.RMIMathS -- codebase arg not working

package bettybook.math.rmi;

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
		//System.setProperty("java.security.policy","file:/C:/cygwin/home/Raymond/code/scribble/github-rhu1/scribble-java/modules/demos/scrib/bettybook/src/bettybook/math/rmi/server.policy");
		//if (System.getSecurityManager() == null) { System.setSecurityManager(new SecurityManager()); }

		/*-Djava.rmi.server.codebase=file:/C:\cygwin\home\Raymond\code\scribble\github-rhu1\scribble-java\modules\demos\bettybook\target\classes/
		Registry registry = LocateRegistry.getRegistry(8888);*/
		Registry registry = LocateRegistry.createRegistry(8888);

		RMIMath stub = (RMIMath) UnicastRemoteObject.exportObject(new RMIMathS(), 0);
		registry.rebind("MathService", stub);
		
		System.out.println("RMI Math Server running.");
	}
}
