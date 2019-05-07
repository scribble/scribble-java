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

import org.scribble.del.ChoiceDel;

public class LChoiceDel extends ChoiceDel implements LCompoundSessionNodeDel
{
	/*@Override
	public ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent,
			ScribNode child, UnguardedChoiceDoProjectionChecker checker,
			ScribNode visited) throws ScribException
	{
		Choice<?> cho = (Choice<?>) visited;
		List<UnguardedChoiceDoEnv> benvs = cho.getBlocks().stream()
				.map((b) -> (UnguardedChoiceDoEnv) b.del().env())
				.collect(Collectors.toList());
		UnguardedChoiceDoEnv merged = checker.popEnv().mergeContexts(benvs);
		checker.pushEnv(merged);
		return (Choice<?>) super.leaveUnguardedChoiceDoProjectionCheck(parent,
				child, checker, cho);
				// Done merge of children here, super does merge into parent
	}

	@Override
	public ScribNode leaveProjectedChoiceDoPruning(ScribNode parent,
			ScribNode child, ProjectedChoiceDoPruner pruner, ScribNode visited)
			throws ScribException
	{
		LChoice lc = (LChoice) visited;
		List<LProtocolBlock> blocks = lc.getBlocks().stream()
				.filter(b -> !b.isEmpty()).collect(Collectors.toList());
		if (blocks.isEmpty())
		{
			return null;
		}
		return lc.reconstruct(lc.subj, blocks);
	}
	
	@Override
	public ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent,
			ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited)
			throws ScribException
	{
		LChoice lc = (LChoice) visited;
		List<LProtocolBlock> blocks = lc.getBlocks();
		
		Set<Role> subjs = blocks.stream()
				.map((b) -> b.getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer))
				//.filter((r) -> !r.toString().equals(DummyProjectionRoleNode.DUMMY_PROJECTION_ROLE))
				.collect(Collectors.toSet());
		if (subjs.size() == 0)
		{
			//throw new RuntimeScribException("TODO: unable to infer projection subject: " + parent);
			throw new RuntimeException("Shouldn't get in here: " + subjs);  // FIXME: should be OK now by model-based WF
		}
		else
		{
			subjs = subjs.stream()
					.map((r) -> fixer.isRecVarRole(r)
							? fixer.getChoiceSubject(new RecVar(r.toString())) : r) // Never needed?
					.collect(Collectors.toSet());
		}
		
		// HACK?  (for non- role-balanced choice cases)
		subjs = subjs.stream().filter((s) -> s != null).collect(Collectors.toSet());
		
		if (subjs.size() > 1)  // Unnecessary: due to WF check in GChoiceDel.leaveInlinedPathCollection -- would be better as a check on locals than in projection anyway
		{
			String self = fixer.getModuleContext().root.getSimpleName().toString();  // HACK
			self = self.substring(self.lastIndexOf('_')+1, self.length());  // FIXME: not sound (if role names include "_")
			throw new ScribException(lc.getSource(), "Cannot project onto " + self + " due to inconsistent local choice subjects: " + subjs);  // self not recorded -- can derive from LProtocolDecl RoleDeclList
			//throw new RuntimeException("Shouldn't get in here: " + subjs);
		}
		RoleNode subj = (RoleNode) fixer.job.af.SimpleNameNode(null, RoleKind.KIND,  // FIXME? null source OK?
				//blocks.get(0).getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer).toString());
				subjs.iterator().next().toString());
		fixer.setChoiceSubject(subj.toName());
		LChoice projection = fixer.job.af.LChoice(lc.getSource(), subj, blocks);
		return projection;
	}*/
}
