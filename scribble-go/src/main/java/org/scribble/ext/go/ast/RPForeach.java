package org.scribble.ext.go.ast;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.CompoundInteractionNode;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class RPForeach<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	public final List<RoleNode> subjs;
	public final List<RPForeachVar> params;
	public final List<RPIndexExpr> starts;
	public final List<RPIndexExpr> ends;
	public final ProtocolBlock<K> block;

	// Pre: subjs/params/starts/ends same length -- FIXME: factor out?
	protected RPForeach(CommonTree source, List<RoleNode> subjs,
			List<RPForeachVar> params, List<RPIndexExpr> starts, List<RPIndexExpr> ends, 
			ProtocolBlock<K> block)
	{
		super(source);
		this.subjs = Collections.unmodifiableList(subjs);
		this.params = Collections.unmodifiableList(params);
		this.starts = Collections.unmodifiableList(starts);
		this.ends = Collections.unmodifiableList(ends);
		this.block = block;
	}

	public abstract RPForeach<K> reconstruct(List<RoleNode> subjs,
			List<RPForeachVar> params, List<RPIndexExpr> starts, List<RPIndexExpr> ends, ProtocolBlock<K> block);
	
	@Override
	public abstract RPForeach<K> clone(AstFactory af);

	@Override
	public RPForeach<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<RoleNode> subjs = visitChildListWithClassEqualityCheck(this, this.subjs, nv);
		ProtocolBlock<K> block = visitChildWithClassEqualityCheck(this, this.block, nv);
		return reconstruct(subjs, this.params, this.starts, this.ends, block);
	}
	
	public abstract ProtocolBlock<K> getBlock();

	@Override
	public String toString()
	{
		return "foreach " + IntStream.of(0, this.subjs.size()-1).mapToObj(i ->
						this.subjs.get(i) + "[" + this.params.get(i) + ":" + this.starts.get(i) + "," + this.ends.get(i) + "]"
				).collect(Collectors.joining(", "))
				+ this.block;
	}
}
