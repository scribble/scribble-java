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
package org.scribble.ext.go.cli;

import java.util.Map;

import org.scribble.cli.CLArgFlag;
import org.scribble.cli.CommandLine;
import org.scribble.cli.CommandLineException;
import org.scribble.ext.go.codegen.statetype.go.GoEndpointApiGenerator;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class GoCommandLine extends CommandLine
{
	protected final Map<GoCLArgFlag, String[]> goArgs;  // Maps each flag to list of associated argument values
	
	public GoCommandLine(String... args) throws CommandLineException
	{
		/*super(args);
		this.goArgs = new GoCLArgParser(args).getGoArgs();*/

		// FIXME: refactor
		GoCLArgParser p = new GoCLArgParser(args);
		this.args = p.getArgs();
		this.goArgs = p.getGoArgs();
		if (!this.args.containsKey(CLArgFlag.MAIN_MOD) && !this.args.containsKey(CLArgFlag.INLINE_MAIN_MOD))
		{
			throw new CommandLineException("No main module has been specified\r\n");
		}
	}

	@Override
	protected void doNonAttemptableOutputTasks(Job job) throws ScribbleException, CommandLineException
	{		
		if (this.goArgs.containsKey(GoCLArgFlag.GO_API_GEN))
		{
			JobContext jcontext = job.getContext();
			String[] args = this.goArgs.get(GoCLArgFlag.GO_API_GEN);
			for (int i = 0; i < args.length; i += 2)
			{
				GProtocolName fullname = checkGlobalProtocolArg(jcontext, args[i]);
				Role role = checkRoleArg(jcontext, fullname, args[i+1]);
				Map<String, String> goClasses = new GoEndpointApiGenerator(job).generateGoApi(fullname, role);
				outputClasses(goClasses);
			}
		}
		else
		{
			super.doNonAttemptableOutputTasks(job);
		}
	}

	public static void main(String[] args) throws CommandLineException, ScribbleException
	{
		new GoCommandLine(args).run();
	}
}
