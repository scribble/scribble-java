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
package test.test4.sig;

import test.test4.Test4.Proto1.Proto1;

public class Bar extends Test4Message
{
	private static final long serialVersionUID = 1L;

	public Bar(int body)
	{
		super(Proto1.Bar, body);
	}

	@Override
	public Integer getBody()
	{
		return (Integer) this.payload[0];
	}
}
