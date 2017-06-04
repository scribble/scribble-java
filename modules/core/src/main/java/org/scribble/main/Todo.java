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
package org.scribble.main;


// FIXME: project modifiers?


// Generally: need framework to correlate source AST nodes to canonical/inlined/unfolded representations, so that analyses can do done on representations and corresponding transformations applied on the AST
// e.g. keep a point to the corresponding original AST node in the cloned AST being visited  
// via del may be good -- ASTs are always cloned, but same del can be inherited


//.. consider a "less expressive" version of subprotocol declaration that does not allow same rec labels for "non-compatible" states after inlining/unfolding (not sure how to compute though)
//       -- i.e. limit subprotocols to corresponds to standard rec-continue structure
//			 -- this is a limitation of relying on protocol name + role arg config as ("unique") state identifiers
//       -- No? recursive subprotocols should inherently be same as standard rec-continue? -- maybe non-unfiorm rec naming scheme is fine (use inner most?), and just graph building is wrong?
//     -- FIXED: was a graph building problem


//.. Global model WF
//
//.. analyse inlined unfolded for max buffer size -- no: one-slot buffer seems sufficient, given located choice
//.. add term branches for infinite loops (termination hack), for checking mixed finite and infinite role behaviours (see Test.scr **TERM)
//..       don't need termination hack, just need to cut off state building on bad states -- no: it's needed (buffered message may be blocked now but valid later), or a terminal set analysis
//..       choice subject enabling needed for non-mixed states (inherently relevant to deadlock) -- although for one-slot asynchrony to work, it's just the A->B constructor: there's always syntactically at least one receiver, and any potential deadlock between parallel senders due to bounded buffer will be caught as the counterpart paralel receiver deadlock
//.. check role-based starvation/liveness (loop hack) -- no: terminal set analysis
//.. refactor orphans as generalised message liveness?  though orphans are more natural as a termination/deadlock property?
//.. reconsider liveness loop hack (subsumes safety?)
//..
//.. "introduces" should just work (dynamically add/remove buffer structures) -- no: that's connectedness, introduces on top? (should still be a finite state space extension)
//..
//.. multicast: generalise model actions -- but how about asynchrony? introduce output buffers? -- output/input buffer intuition for "single-step" asynchrony
//..
//.. model checked assertion annotations -- and API gen
//
//.. global model actions
//.. error state traces (and error process system)
//.. check junit harness and unit tests, add new tests
//.. CL ouput even if ScribbleException
//.. option to return all errors or just the first
//
//.. integrate reachability check and graph building? or move reachability earlier? EFSM building now done before reachability pass
//
//.. move connectedness checks into model checking, rather than syntactic


// FIXME: ambiguous choice subject (i.e. > 1 candidate) is checked subsequently by ProjectedChoiceSubjectFixer -- should be better integrated (e.g. reuse ChoiceUnguardedSubprotocolChecker, rather than adhoc LInteractionNode.inferLocalChoiceSubject) -- NOTE: but cannot do all pruning and fixing in one pass, as fixing the subject roles here will interfere with the pruning algorithm (currently it looks for dummy role choices)
//.. "semantic" test suite: e.g. looking for duplicate tests by global model
//.. subcategorise tests by number of roles


/*
.. disallow self comm
.. check role decl used
.. test mvn install
.. fix script
.. individual test ignores (cf. whole directory) -- good for package testing

.. unguarded choice-rec in conjunction with unguarded continue needs to be ruled out in WF or fixed in FSM generation
*/

	// FIXME: refactor EnvVisitors merge/compose routines -- check enter/leave merging isn't duplicating parent info (e.g. path visiting, recursion has to clear Env so child paths don't start with parent prefixes)

	// default G/LNode getKind methods won't work properly until Eclipse updated with fix, cf. G/LInteractionSeq

	// FIXME: protocoldecl header to check used roles (so projection roledecl filtering doesn't give empty roledecllist -- relax to check only that at least 1 or 2 roles are used?) -- should be checked wrt. role occurrences obtained from subprotocol visiting
	// FIXME: parameter checking along with roles?
	// FIXME: check do-call argument kinding (sig/type args/params), arity, etc
	// bound qualified sig/type names (disambiguation check of qualified names, not just ambiguous)
	// duplicate protocol/role decls etc
	//.. do call type checking as well as basic name binding
	// name collisions? e.g. message sig names and ops (M1 and M1() -- maybe ok) -- is rec var shadowing ok? (currently inconsistent to disallow due to shadowed recs in subprotocols and NameDisambiguator is not an inlined or subprotocol visitor)

	//.. guarded recursive subprotocols -- guarded recursion vars not needed? handled by projection -- subprotocols need unused roledecl check
	//.. generalise dependencies for duplicate-role projection
	//.. do projection should filter unused subprotocol role params -- also non role params? -- but scoped subprotocols may need extra name mangling

	// pointer equality for testing if AST subtrees already visited in e.g. InlinedWFChoiceChecker (and thus UnfoldingVisitor) maybe not good
	// move roledecllist etc projection to dels
	// could parameterise recvar to be global/local

	// self comm (wf, projection)
	// multicast (enforce sending same value -- can "compile" to assertions for separate ops)
	// TODO: deadlock analysis: for parallel, and even just choice if one process will play multiple roles (e.g. choice at A { A->B; A->C } or { A->C; A->B }) -- separate par from unordered
	// FIXME: api generation for parallel/interruptible -- branch needs to report on op and role (depending on input queue semantics)
	// api gen: explicit end for implicit skipping tail inputs but without failing the sender due to closing the transport -- actually, disable async for term-leading states? -- or perhaps implicit in using-close
	//.. FIXME: singleton constants in subpackages, constant references from session class -- work out scrib package/module correspondence with java package/classes

	//.. fix projection env to take projection output type as Parameter
	// dels should be kinded as well? -- maybe by node type? or too restrictive

	// some import/package name issues aren't tested in test suite because never in root package (issues when full and simple name coincide) 
	// roldedecl used validation only checked for global; projection runs roledecl fixing after role collection (although check should be redundant after projection roledecl fixing?) -- maybe separate roledecl validation to follow after context building

	// Inconsistencies? some visitOverride methods override base visit (i.e. including enter/exit) while others just override the visitChildren part
	// also: sometimes super.visit is used, other times child.accept(this), etc

	//.. factor out main module resource loading in front end from main context -- front end should take main argument, check existence, and pass MainContext the abstract resource identifier to load the main
	//.. ^^ alternatively keep ResourceLocator specific to file systems -- "DirectoryResourceLocator" just uses the import paths

// Done
//.. gchoice use Collections.disjoint (not retain)
//.. add headerparamdecllist and rolearglist disamb tests
//.. public reconstructs; copy del assignment; remove unnecessary wildcard bounds (and sort out mixed collections under wildcards? -- where it runtime type checking gets involved) -- done: but chosen no to copy del assignment
//.. refactor inlined/unfolding visitor and del names
//.. make headerparamdecl into paramdecl directly, i.e. and then role is a specialised param kind -- headerparamdecl is super of role and nonrole decls
//.. get simple/compound name node and name classes into shape -- done: factored out base classes uniform with ast hierarchy, and using default methods for shared simple/compound behaviours 
//.. fix del parameterized return type (take class as arg) -- maybe also copy -- done: generic node casts after visiting, generic env push/pop, protected shallow copy pattern
//.. remove scoped subprotocols for now
//.. consider refactoring all uses of AbstractProtocolDecl to be able to get global/local directly (would need global/local as a generic parameter) -- e.g. Do.getTargetProtocolDecl -- partly: using global/local kinds, don't need extra interface along with abstract protocoldecl class
// - streamline visitor pattern calls (e.g. accept) -- done: using total/partial (enter/leave) visit override patterns to customise e.g. subprotocol and projection visiting
// - visitor pattern, delegates, envs (root, creating and assigning, merging, super calls), subprotocol visiting -- done: sorted visitor hierarchy, added inlined/unfolded visiting, swapped env/subprotocol visitor positions, factored out offsetsubprotocolvisitor
// - streamline vistitor/del env references -- and del enter/leave env setter on visitors -- done: sorted out del immutability except for envs, visitor env generic parameter, and env generic parameter
//.. fix ReachabilityEnv merge; do enter/leave reachability check for recursion/continue/parallel/etc; check reachability pass visits all projected modules -- reachability checking now done on unfolded inlined protocols
//.. fix modelfactory simple name node parameterization (take class instead of enum)
//.. fix global/local do delegate context build loop -- use lambda
//.. AntlrNonRoleParamDeclList -- generic typing error -- generally fix nonroleparam decl/list/... -- fixed: cast justified by immutable ast nodes -- related generic typing (mixed colection types under a wildcard)
//.. fix parameterdecllist generics (not fixed to one kind) -- done: using base non-role parameter kind as the "concrete kind" (see above)
//.. both projector and graphbuilder are env visitors but not subprotocol visitors now, so swap visitor hierarchy?
//.. added unfolded visitor -- for: wf-c should in some cases attempt an unfolding on reaching a continue if not satisfied yet -- or do by "recording" cache for recs, as for subprotocolsigs
//.. added unfolded visitor -- for: changed fsmbuilder from offsetsubprot visitor to inlined visitor to reduce state label accumulation to rec only -- but this introduces a problem in wfc-checking for "unguarded" recursive-do-as-continue in choice blocks -- current offset visitor is also hacked to follow up just "1 level"
//.. projection as subprotocol visitor (or maybe by some other hack, e.g. check each gdo for special cases) to handle repeat role args e.g. do(A, B, A) -- done: by factoring out role occurrences into context info and adding another projection pass
//.. need public deep clone methods to support syntax manipulations e.g. unfolding (if using pointer equality for ast nodes in the same syntactic position, not just same text value)
//.. public reconstructs -- was previously using public-ified reconstruct of just interactionseq and recursion to do cloning for unfolding -- not a deep clone though, e.g. interaction seq reuses original block -- need to make sure dels/envs being treated properly
//.. factor out global/local inlining/unfolding better
//.. factor out some global/local del routines from compound/simple classes using default interface methods
//.. relax strictclasscheck visits -- partly: in just the places where nodes are currently expected to change (ambiguousnamenode, interactionseq, continue for unfolding) -- visitchildren shouldn't use check class on visited nodes so strictly, e.g. name disambiguation changes ambignodes to other nodes
//.. make all protected ast node fields private? make all field accesses by getters? -- partly: made all the mutable collection fields private
//.. tidy up inlinedwfchoice check in gchoicedel
//.. fix ldodel projection role fix gproto name hack -- Lprojectiondecl subclass records source gprotocol (and self role)
//.. role filtering for protocoldecl/do-projection in 2nd projection pass
//.. refactor global/local simple/compound dels
//.. refactor jobs (inlining/unfolding)
//.. offset/subprotocol visitor factoring
// - separate protocol names into global/local -- use generic parameter for name kinds rather than subclasses
// - wf-choice: a role should be enabled by the same role in all blocks
// - get rid of argument instantiation -- renamed, but otherwise structurally the same (unlike name/param decls, arg nodes are not kinded)
// - refactor simple/compound names to just names; and simple name nodes to be subtypes of compound -- simple/compound distinction only relevant to name nodes (i.e. syntax); type names are all uniform (compound)
// - generalise dependency building to support local protocols -- though only global dependencies used so far, for projection
// - make module/protocol delegate state (module context, protocol dependencies) setting uniform -- related to (non-)immutablity of delegates (where to store "context" state)
// - remove job/module contexts from Envs (refer from visitor -- can be updated during visitor pass and reassigned to root module on leave)
// - enter doesn't need to return visitor, not using visitor immutability? (or visitor replacement flexibility)
// - use Path API (though path separators not taken from nio api)
// - import path should be a CL parameter, not MainContext

// ? - fix projected choice subj fixing for (single?) continue-only block
// ? - generalise recursion projection filtering
// ? - check projection/merge of role-subset recursion paths, and projection of non-relevant choice blocks


// Not done
//.. maybe make an UnkindedName superclass of Name, use for e.g. parameters or ambiguous -- parameters and ambiguous have their own kinds
// - perhaps refactor to have choice/recursion/etc as packages with global/local/del/etc in each -- no: going with kind generic parameter factoring instead
// - ArgumentNode is not kinded -- argument interface is about not knowing what kind of argument it is; e.g. AmbiguousNameNode has both DataType and Sig kind interfaces
// - FIXME: factor out a project method (like a reconstruct) to GlobalModelNode (and use the below for recording/assembling the projections) -- no, leave in delegate
// - change InteractionNode interface to a base class -- no, better for interaction nodes to extend simple/compound as base
// - make a createDelegate method in ModelNode -- no, leave association of delegates to model nodes in factory -- then replacing a delegate requires changing the factory only
// - substitute to delegate? -- no, better to have as a simple node operation that uses the protected reconstruct pattern directly (a del operation is more indirect with no advantages)
// - fix instanceof in projector and reachability checker -- only partly: moved main code to delegates but the "root" instanceof needs to stay inside the visitors to "override" the base subprotocol visitInSubprotocols pattern
// - override del in each ModelNode to cast -- no: leave as base del for most flexibility in case of replacement
// - Job takes MainContext as argument -- no: recursive maven dependencies between cli-core-parser

public class Todo
{
	public Todo()
	{

	}
}
