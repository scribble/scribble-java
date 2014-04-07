/*
 * Copyright 2009-11 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.cli;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.cli.CommandLine;

public class CommandLineTest {

	@Test
	@org.junit.Ignore
	public void test() {
		
		java.net.URL url=ClassLoader.getSystemResource("scribble/examples/GMessage.scr");
		
		if (url == null) {
			fail("Failed to find reference protocol");
		}
		
		java.io.File f=new java.io.File(url.getFile());
		
		String path=f.getParentFile().getParentFile().getParentFile().getPath();
		
		String[] args={ "-path", path, "scribble.examples.Imports" };
		
		CommandLine cli=new CommandLine();
		
		if (!cli.execute(args)) {
			fail("Failed to execute");
		}
	}

}
