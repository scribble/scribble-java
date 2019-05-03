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
package org.scribble.del.local;

public class LRecvDel extends LMsgTransferDel
{
	/*@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent,
			ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LRecv) child).src.toName());
	}
	
	@Override
	public void enterUnguardedChoiceDoProjectionCheck(ScribNode parent,
			ScribNode child, UnguardedChoiceDoProjectionChecker checker)
			throws ScribException
	{
		super.enterUnguardedChoiceDoProjectionCheck(parent, child, checker);
		LRecv lr = (LRecv) child;
		UnguardedChoiceDoEnv env = checker.popEnv();
		env = env.setChoiceSubject(lr.getSourceChild().toName());
		checker.pushEnv(env);
	}*/
}
