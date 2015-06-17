package org.scribble.ast.global;

import java.util.List;

import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.Parallel;
import org.scribble.ast.ProtocolBlock;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

//public class GlobalParallel extends Parallel<GlobalProtocolBlock> implements CompoundGlobalInteractionNode
public class GParallel extends Parallel<Global> implements GCompoundInteractionNode
{
	//public GlobalParallel(List<GlobalProtocolBlock> blocks)
	public GParallel(List<? extends ProtocolBlock<Global>> blocks)
	{
		//this(t, blocks, null, null);
		super(blocks);
	}
	
	@Override
	//protected GlobalParallel reconstruct(List<GlobalProtocolBlock> blocks)
	protected GParallel reconstruct(List<? extends ProtocolBlock<Global>> blocks)
	{
		ScribDel del = del();
		GParallel gp = new GParallel(blocks);
		gp = (GParallel) gp.del(del);
		return gp;
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new GParallel(this.blocks);
	}
	
	/*@Override
	public GlobalParallel leaveProjection(Projector proj) //throws ScribbleException
	{
		List<LocalProtocolBlock> blocks = new LinkedList<>();
			//this.blocks.stream().map((b) -> (LocalProtocolBlock) ((ProjectionEnv) b.getEnv()).getProjection()).collect(Collectors.toList());	
		for (GlobalProtocolBlock gpb : this.blocks)
		{
			LocalProtocolBlock lpb = (LocalProtocolBlock) ((ProjectionEnv) gpb.getEnv()).getProjection();
			if (!lpb.isEmpty())
			{
				blocks.add(lpb);
			}
		}
		LocalParallel projection = null;
		if (!blocks.isEmpty())
		{
			projection = new LocalParallel(null, blocks);
		}
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	/*@Override
	public GlobalParallel leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		Parallel<GlobalProtocolBlock> par = super.leaveContextBuilding(builder);
		/eturn new GlobalParallel(par.ct, par.blocks, par.getContext());
	}*/

	/*@Override
	public GlobalParallel leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Parallel<GlobalProtocolBlock> par = super.leaveWFChoiceCheck(checker);
		//return new GlobalParallel(par.ct, par.blocks, par.getContext(), par.getEnv());
		return reconstruct(par.ct, par.blocks, par.getContext(), par.getEnv());
	}*/
	
	/*@Override
	public GlobalParallel checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		Env env = wfc.getEnv();
		List<GlobalProtocolBlock> blocks = new LinkedList<>();

		MapToMapToSet<Role, Operator> ops = new MapToMapToSet<>();
		
		for (ProtocolBlock pb : this.blocks)
		{
			GlobalProtocolBlock block = (GlobalProtocolBlock) pb;
			wfc.setEnv(new Env(env));
			GlobalProtocolBlock visited = (GlobalProtocolBlock) wfc.visit(block);

			OperatorEnv tmp = visited.getEnv().ops;
			for (Role src : tmp.getSources())
			{
				for (Role dest : tmp.getDestinations(src))
				{
					Set<Operator> tmp2 = tmp.get(src, dest);
					if (ops.hasKeyPair(src, dest))
					{
						Set<Operator> tmp1 = new HashSet<>(ops.get(src, dest));
						tmp1.retainAll(tmp2);
						if (!tmp1.isEmpty())
						{
							throw new ScribbleException("Bad parallel operator(s) between " + src + " and " + dest  + ": "  + tmp1);
						}
					}
					ops.putAll(src, dest, tmp2);
				}
			}
			
			
			Env env1 = visited.getEnv();
			Set<Scope> scopes = env1.scopes.getScopes();
			if (!scopes.isEmpty())
			{
				throw new ScribbleException("Bad scope entry in parallel context: " + scopes);
			}


			blocks.add(visited);
		}
		wfc.setEnv(env);
		return new GlobalParallel(this.ct, blocks);
	}
	
	@Override
	public LocalParallel project(Projector proj) throws ScribbleException
	{
		Env env = proj.getEnv();
		List<LocalProtocolBlock> visited = new LinkedList<>();
		for (ProtocolBlock block : this.blocks) 
		{
			//LocalProtocolBlock b = (LocalProtocolBlock) proj.visit((GlobalProtocolBlock) block);
			
			proj.setEnv(new Env(env));
			LocalProtocolBlock b = (LocalProtocolBlock) proj.visit((GlobalProtocolBlock) block);
			
			if (b != null)
			{
				visited.add(b);
			}
		}
		proj.setEnv(env);
		if (visited.isEmpty())
		{
			return null;
		}
		return new LocalParallel(null, visited);
	}*/

	/*@Override
	public GlobalParallel visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Parallel<GlobalProtocolBlock> par = super.visitChildren(nv);
		//List<GlobalProtocolBlock> blocks = GlobalProtocolBlock.toGlobalProtocolBlockList.apply(par.blocks);
		//List<GlobalProtocolBlock> blocks = par.blocks.stream().map(GlobalProtocolBlock.toGlobalProtocolBlock).collect(Collectors.toList());
		//return new GlobalParallel(par.ct, par.blocks, par.getContext(), par.getEnv());
		return reconstruct(par.ct, par.blocks, par.getContext(), par.getEnv());
	}*/
}
