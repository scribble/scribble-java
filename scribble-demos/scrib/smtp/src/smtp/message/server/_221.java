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
package smtp.message.server;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

public class _221 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _221()
	{
		super(Smtp._220);
	}

	public _221(String body)
	{
		super(Smtp._220, body);
	}
	
	/*@Override
	public Operator getOperator()
	{
		return Smtp._220;
	}*/
}
