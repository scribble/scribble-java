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
package org.scribble.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ScribUtil
{
	public static <T> T handleLambdaScribbleException(Callable<T> c)
	{
		try
		{
			return c.call();
		}
		catch (Exception se)
		{
			throw new RuntimeScribException(se);  
					// Maybe this hack is not worth it?  Better to throw directly as ScribbleException from a regular foreach
		}
	}

	// Returns [ stdout, stderr ]
	public static String[] runProcess(String... cmdAndArgs)
			throws ScribException
	{
		try
		{
			ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
			Process p = pb.start();
			p.waitFor();
	
			InputStream is = p.getInputStream(), eis = p.getErrorStream();
			InputStreamReader isr = new InputStreamReader(is);
			InputStreamReader eisr = new InputStreamReader(eis);
			BufferedReader br = new BufferedReader(isr);
			BufferedReader ebr = new BufferedReader(eisr);
			String stdout = "", stderr = "", line;
			while ((line = br.readLine()) != null)
			{
				stdout += line + "\n";
			}
			while ((line = ebr.readLine()) != null)
			{
				stderr += line + "\n";
			}
			return new String[] { stdout, stderr };
		}
		catch (IOException | InterruptedException e)
		{
			throw new ScribException(e);
		}
	}

	// Warning: doesn't check if file exists
	public static void writeToFile(String path, String text)
			throws ScribException
	{
		File file = new File(path);
		File parent = file.getParentFile();
		if (parent != null)
		{
			parent.mkdirs();
		}
		//try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8")))  // Doesn't create missing directories
		try (FileWriter writer = new FileWriter(file))
		{
			writer.write(text);
		}
		catch (IOException e)
		{
			throw new ScribException(e);
		}
	}
}
