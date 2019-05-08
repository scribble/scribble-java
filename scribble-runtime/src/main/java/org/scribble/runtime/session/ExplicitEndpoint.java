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
package org.scribble.runtime.session;

import java.io.IOException;

import org.scribble.core.type.name.Role;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ScribMessageFormatter;

public class ExplicitEndpoint<S extends Session, R extends Role> extends SessionEndpoint<S, R>
{
	public ExplicitEndpoint(S sess, R self, ScribMessageFormatter smf) throws IOException, ScribRuntimeException
	{
		super(sess, self, smf);
	}
	
	// FIXME HACK: "init" really only for MPSTEndpoint?  Then refactor into there only
	@Override
	public void init() throws ScribRuntimeException
	{
		if (this.init)
		{
			throw new ScribRuntimeException("Session endpoint already initialised.");
		}
	}
}
