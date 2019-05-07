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

import org.scribble.del.DoDel;

public class LDoDel extends DoDel implements LSimpleSessionNodeDel
{
	/*// Pre: this pass is only run on projections (LProjectionDeclDel has source global protocol info)
	@Override
	public ScribNode leaveProjectedRoleDeclFixing(ScribNode parent,
			ScribNode child, ProjectedRoleDeclFixer fixer, ScribNode visited)
			throws ScribException
	{
		JobContext jc = fixer.job.getContext();
		LDo ld = (LDo) visited;
		LProtoDecl lpd = ld.getTargetProtoDecl(jc, fixer.getModuleContext());
		
		// do role args are currently as inherited from the global type -- so need to derive role map against the global protocol header
		// Doing it off the global roledecls allows this to be done in one pass, but would probably be easier to split into two (e.g. 1st cache the proposed changes, 2nd write all changes -- the problem with a single pass is e.g. looking up the localdecl info while localdecls are being rewritten during the pass)
		// Could possibly factor out rolemap making with SubprotocolVisitor a bit, but there it maps to RoleNode and works off a root map
		GProtoName source = ((LProjectionDeclDel) lpd.del()).getSourceProto();
		GProtoDecl gpd = (GProtoDecl) jc.getModule(source.getPrefix()).getProtoDecl(source.getSimpleName());
		Iterator<RoleArg> roleargs = ld.roles.getDoArgs().iterator();
		Map<Role, Role> rolemap = gpd.header.roledecls.getRoles().stream().collect(
				Collectors.toMap(r -> r, r -> roleargs.next().val.toName()));
		Set<Role> occs = ((LProtoDeclDel) lpd.del()).getProtoDeclContext().getRoleOccurrences().stream().map(r ->
				rolemap.get(r)).collect(Collectors.toSet());

		List<RoleArg> ras = ld.roles.getDoArgs().stream().filter(ra -> occs.contains(ra.val.toName())).collect(Collectors.toList());
		RoleArgList roles = ld.roles.reconstruct(ras);
		return super.leaveProjectedRoleDeclFixing(parent, child, fixer, ld.reconstruct(roles, ld.args, ld.getProtoNameNode()));
	}
	
	@Override
	public ScribNode leaveUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker, ScribNode visited) throws ScribException
	{
		/* //if (checker.isCycle())
		if (checker.isRootedCycle())  // ChoiceUnguardedSubprotocolChecker is a (regular) SubprotocolVisitor which pushes a subprotosig on root decl entry (ProjectedSubprotocolPruner.visit)
			                            // Check for "rooted" cycle to ensure it's specifically the cycle from the root proto decl (back) to the target do
																	// FIXME: but cycle to specific "target do" is not ensured: could be another instance of a do with the same subprotosig... an inherent issue of the current subprotocolvisitor framework
			
			// FIXME: this algorithm works for some use cases for is still wrong (prunes some that it shouldn't -- e.g. mutually pruneable choice-unguarded do's)
			// *** what we really need is to check for 0 inferred choice subjects up to recursion back (if any) to to the parent choice -- problem is current framework doesn't make identifying (e.g. ==) the original choice easy ***
			// the issue is arising since WF was relaxed to allow unbalanced choice case roles: with balanced, subject inference is always fine as long as roles are used? (and prev assumed no choice-unguarded do's?)
		{
			//System.out.println("ABC: " + checker.peekEnv().subjs + ", " + checker.SHOULD_PRUNE);
			
			//if (checker.peekEnv().shouldPrune())
			if (checker.peekEnv().subjs.isEmpty())
			{
				/*ChoiceUnguardedSubprotocolEnv env = checker.popEnv();
				checker.pushEnv(env.disablePrune());* /
				//checker.enablePrune();
			}
		}* /
		return super.leaveUnguardedChoiceDoProjectionCheck(parent, child, checker, visited);


		// for each do: check shouldPrune condition by following the control flow: if terminates or cycles with no actions then prune
		// Let the main pruning visitor be a regular visitor, and use the subprotocol visitor to follow the calls for pruning analysis
		
		//FIXME: maybe similar to project roledecl fixing?  use role occurrences saved in protocoldecl?
		//		role occurrences collected by RoleCollector which is indeed subprotocol visitor
		//		problem is RoleCollector currently comes after subject fixing... but maybe it doesn't need to collect subject roles in the end?  due to WF enabling checks?
		
		//..not role collection, that's a "may" usage of roles
		//..should be: start from a candidate unguarded-do inside a choice: want to know if this choice case should be removed
		//......follow protocol flow through do until either end or return to this choice, looking for actions
		//		... but look only on direct path or across all branches?
						
		//...or else it should be: start from the target protocoldecl and go through to the candidate do (cf grecursion.prune)
	}*/
}
