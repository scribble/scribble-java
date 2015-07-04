package org.scribble.main;

	// FIXME: subprotocol decl header projection to check used roles (integrate with existing 2nd projection pass)

	// need public deep clone methods to support syntax manipulations e.g. unfolding (if using pointer equality for ast nodes in the same syntactic position, not just same text value)
	// FIXME: currently using public-ified reconstruct of interactionseq and recursion to do cloning for unfolding
	// FIXMEL: though not a deep clone, e.g. interaction seq reuses original block -- need to make sure dels/envs being treated properly

	// FIXME: factor out global/local inlining/unfolding better
	// factor out some global/local del routines from compound/simple classes using default interface methods

	// Inconsistencies? some visitOverride methods override base visit (i.e. including enter/exit) while others just override the visitChildren part
	// also: sometimes super.visit is used, other times child.accept(this), etc

	// FIXME: projection as subprotocol visitor (or maybe by some other hack, e.g. check each gdo for special cases) to handle repeat role args e.g. do(A, B, A)

	// move roledecllist etc projection to dels

	// FIXME: wf-c should in some cases attempt an unfolding on reaching a continue if not satisfied yet -- or do by "recording" cache for recs, as for subprotocolsigs
	// similar: Changed fsmbuilder from offsetsubprot visitor to inlined visitor to reduce state label acculumation to rec only -- but this introduces a problem in wfc-checking for "unguarded" recursive-do-as-continue in choice blocks -- current offset visitor is also hacked to follow up just "1 level"

	// could paramterise recvar to be global/local

	// self comm (wf, projection)
	// multicast (enforce sending same value -- can "compile" to assertions for separate ops)
	// AntlrNonRoleParamDeclList -- generic typing error

	// TODO: deadlock analysis: for parallel, and even just choice if one process will play multiple roles (e.g. choice at A { A->B; A->C } or { A->C; A->B })
	// FIXME: api generation for parallel/interruptible -- branch needs to report on op and role (depending on input queue semantics)

	// FIXME: do-call argument kinding (sig/type args/params), arity, etc
	// bound qualified sig/type names (disambiguation check of qualified names, not just ambiguous)
	// duplicate protocol/role decls etc
	// name collisions? e.g. message sig names and ops (M1 and M1() -- maybe ok)

	//.. do projection should filter unused subprotocol role params -- but scoped subprotocols may need extra name mangling
	//.. both projector and graphbuilder are env visitors but not subprotocol visitors now, so swap visitor hierarchy?
	// dels should be kinded as well?

	// visitchildren shouldn't use check class on visited nodes so strictly, e.g. name disambiguation changes ambignodes to other nodes

	//.. factor out main module resource loading in front end from main context -- front end should take main argument, check existence, and pass MainContext the abstract resource identifier to load the main
	//.. ^^ alternatively keep ResourceLocator specific to file systems -- "DirectoryResourceLocator" just uses the import paths
	//.. check use of generics
	//.. consider refactoring all uses of AbstractProtocolDecl to be able to get global/local directly (would need global/local as a generic parameter) -- e.g. Do.getTargetProtocolDecl
	//.. fix parameterdecllist generics (not fixed to one kind)

	//.. do call type checking as well as basic name binding
	//.. guarded recursive subprotocols -- guarded recursion vars not needed? handled by projection
	//.. make headerparamdecl into paramdecl directly, i.e. and then role is a specialised param kind
	//.. maybe make an UnkindedName superclass of Name, use for e.g. parameters or ambiguous

		//.. fix projection env to take projection output type as Parameter
		//.. fix global/local do delegate context build loop -- use lambda
		//.. get simple/compound name node and name classes into shape
		//.. fix del parameterized return type (take class as arg) -- maybe also copy
		//.. fix modelfactory simple name node parameterization (take class instead of enum)
		//.. remove scoped subprotocols for now

// - perhaps refactor to have choice/recursion/etc as packages with global/local/del/etc in each

// - visitor pattern, delegates, envs (root, creating and assigning, merging, super calls), subprotocol visiting
// - streamline visitor pattern calls (e.g. accept)
// - streamline vistitor/del env references -- and del enter/leave env setter on visitors

//...HERE: fix ReachabilityEnv merge; do enter/leave reachability check for recursion/continue/parallel/etc; check reachability pass visits all projected modules
//... check delegates for local nodes; check reachability visiting for (local) interaction sequence (and delegate)

// Done
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

// Not done
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
