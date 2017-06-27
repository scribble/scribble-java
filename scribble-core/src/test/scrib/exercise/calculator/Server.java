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
package exercise.calculator;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import exercise.calculator.EProtocol.Calc.Calc;
import exercise.calculator.EProtocol.Calc.channels.S.Calc_S_1;
import exercise.calculator.EProtocol.Calc.roles.S;


public class Server
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException, ClassNotFoundException
	{
		Calc calculator = new Calc();
		try (MPSTEndpoint<Calc, S> se = new MPSTEndpoint<>(calculator, Calc.S, new ObjectStreamFormatter()))
		{
			se.connect(Calc.S, SocketChannelEndpoint::new, "localhost", 8888);
			Calc_S_1 s1 = new Calc_S_1(se);
			
			// toto: Implement the rest ...
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
