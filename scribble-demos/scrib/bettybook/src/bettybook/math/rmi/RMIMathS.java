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
		Registry registry = LocateRegistry.createRegistry(8888);

		RMIMath stub = (RMIMath) UnicastRemoteObject.exportObject(new RMIMathS(), 0);
		registry.rebind("MathService", stub);
		
		System.out.println("RMI Math Server running.");
	}
}





		//System.setProperty("java.security.policy","file:/C:/cygwin64/home/rhu/code/eclipse/scribble/github.com/rhu1/scribble-java/scribble-demos/scrib/bettybook/src/bettybook/math/rmi/server.policy");
		//if (System.getSecurityManager() == null) { System.setSecurityManager(new SecurityManager()); }

		/*-Djava.rmi.server.codebase=file:/C:\cygwin64\home\rhu\code\eclipse\scribble\github.com\rhu1\scribble-java\scribble-demos\bettybook\target\classes/
		Registry registry = LocateRegistry.getRegistry(8888);*/
