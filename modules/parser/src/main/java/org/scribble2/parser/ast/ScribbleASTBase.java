package org.scribble2.parser.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public abstract class ScribbleASTBase extends CommonTree implements ScribbleAST
{
	public ScribbleASTBase(Token t)
	{
		super(t);
	}

	/*// Requires visited node to be of the same class as the original node
	// Not suitable for general Visitor pattern: as well as the strict class check (so no substitutability), overriding is not convenient
	// However, this is convenient for visiting generic nodes (cast would be unchecked) -- so those nodes (e.g. ProtocolBlock -- done via visitAll for Choice/Parallel) must keep the same class
	// So this method is basically for the generic AST nodes (ProtocolDecl/Def/Block, InteractionSequence)
	protected static <T extends Node> T visitChildWithClassCheck(Node parent, T child, NodeVisitor nv) throws ScribbleException
	{
		Node visited = ((AbstractNode) parent).visitChild(child, nv);
		if (visited.getClass() != child.getClass())  // Visitor is not allowed to replace the node by a different node type
		{
			throw new RuntimeException("Visitor generic visit error: " + child.getClass() + ", " + visited.getClass());
		}
		@SuppressWarnings("unchecked")
		T t = (T) visited;
		return t;
	}
	
	// Requires all visited nodes to be of the same class as the original nodes
	//public <T extends InteractionNode> List<T> visitAll(List<T> nodes) throws ScribbleException
	protected static <T extends Node> List<T> visitChildListWithClassCheck(Node parent, List<T> children, NodeVisitor nv) throws ScribbleException
	{
		List<T> visited = new LinkedList<>();
		for (T n : children)
		{
			visited.add(visitChildWithClassCheck(parent, n, nv));
		}
		return visited;
	}*/
}
