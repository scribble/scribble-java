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
package org.scribble.del;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Choice;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.env.UnfoldingEnv;
import org.scribble.visit.wf.ExplicitCorrelationChecker;
import org.scribble.visit.wf.env.ExplicitCorrelationEnv;

public abstract class ChoiceDel extends CompoundInteractionNodeDel
{
	public ChoiceDel()
	{

	}

	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		UnfoldingEnv env = unf.peekEnv().enterContext();
		env = env.pushChoiceParent();  // Above is already a copy, but fine
		unf.pushEnv(env);
	}

	@Override
	public Choice<?> leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		Choice<?> cho = (Choice<?>) visited;
		List<UnfoldingEnv> benvs =
				cho.getBlocks().stream().map((b) -> (UnfoldingEnv) b.del().env()).collect(Collectors.toList());
		UnfoldingEnv merged = unf.popEnv().mergeContexts(benvs); 
		unf.pushEnv(merged);
		return (Choice<?>) super.leaveInlinedProtocolUnfolding(parent, child, unf, visited);  // Done merge of children here, super does merge into parent	
	}

	@Override
	public void enterExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker) throws ScribbleException
	{
		ExplicitCorrelationEnv env = checker.peekEnv().enterContext();
		checker.pushEnv(env);
	}

	@Override
	public Choice<?> leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		Choice<?> cho = (Choice<?>) visited;
		List<ExplicitCorrelationEnv> benvs =
				cho.getBlocks().stream().map((b) -> (ExplicitCorrelationEnv) b.del().env()).collect(Collectors.toList());
		ExplicitCorrelationEnv merged = checker.popEnv().mergeContexts(benvs); 
		checker.pushEnv(merged);
		return (Choice<?>) super.leaveExplicitCorrelationCheck(parent, child, checker, visited);  // Done merge of children here, super does merge into parent	
	}
}
