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
