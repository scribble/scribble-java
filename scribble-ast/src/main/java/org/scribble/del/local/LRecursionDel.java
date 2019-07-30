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

import org.scribble.del.RecursionDel;

public class LRecursionDel extends RecursionDel
		implements LCompoundSessionNodeDel
{
	/*@Override
	public ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent,
			ScribNode child, UnguardedChoiceDoProjectionChecker checker,
			ScribNode visited) throws ScribException
	{
		Recursion<?> rec = (Recursion<?>) visited;
		UnguardedChoiceDoEnv merged = checker.popEnv()
				.mergeContext((UnguardedChoiceDoEnv) rec.getBlockChild().del().env());
		checker.pushEnv(merged);
		return (Recursion<?>) super.leaveUnguardedChoiceDoProjectionCheck(parent,
				child, checker, rec);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent,
			ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.pushRec(((LRecursion) child).getRecVarChild.toName());
	}

	@Override
	public ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent,
			ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited)
	{
		fixer.popRec(((LRecursion) child).getRecVarChild().toName());
		return visited;
	}*/
}
