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
package org.scribble.main.resource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;


// Module supplied to tool directly as a String arg.
public class InlineResource extends AbstractResource
{
	//private final String res;
	private java.io.InputStream _inputStream = null;

	/**
	 * The constructor.
	 * 
	 * @param path The optional resource path
	 * @param is The input stream
	 */
	public InlineResource(String res)
	{
		super(Resource.INLINE_LOCATION);
		//this.res = res;
		this._inputStream = new ByteArrayInputStream(res.getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public boolean isInlineResource()
	{
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.io.InputStream getInputStream()
	{
		return (this._inputStream);
	}
}
