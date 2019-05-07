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

public class LSendDel extends LMsgTransferDel
{
	/*// Could make a LMsgTransferDel to factor this out with LReceiveDel
	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent,
			ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LSend) child).src.toName());
	}
	
	@Override
	public LMsgTransfer leaveExplicitCorrelationCheck(ScribNode parent,
			ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited)
			throws ScribException
	{
		LMsgTransfer lmt = (LMsgTransfer) visited;
		checker.pushEnv(checker.popEnv().disableAccept());
		return lmt;
	}*/
}
