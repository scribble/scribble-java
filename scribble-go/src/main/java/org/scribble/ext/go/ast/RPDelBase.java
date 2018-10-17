package org.scribble.ext.go.ast;

import org.scribble.ast.ScribNode;
import org.scribble.del.ScribDel;
import org.scribble.visit.EnvVisitor;
import org.scribble.visit.env.Env;

// Mutable for pass-specific Envs (by visitors)
// Only used by RPDefaultDel? -- concrete RPDel's should extend base Dels and implement RPDel i/face
public abstract class RPDelBase implements RPDel
{
	private Env<?> env;
	
	public RPDelBase()
	{

	}
	
	@Override
	public Env<?> env()
	{
		return this.env;
	}
	
	// "setEnv" rather than "env" as a non-defensive setter (cf. ModelNodeBase#del)
	@Override
	//protected void setEnv(Env env)
	public void setEnv(Env<?> env)
	{
		this.env = env;
	}
	
	public static <T extends Env<?>> void pushVisitorEnv(ScribDel del, EnvVisitor<T> ev)
	{
		T env = castEnv(ev, ev.peekEnv().enterContext());  // By default: copy
		ev.pushEnv(env);
	}
	
	public static <T1 extends Env<?>, T2 extends ScribNode>
			T2 popAndSetVisitorEnv(ScribDel del, EnvVisitor<T1> ev, T2 visited)
	{
		// No merge here: merging of child contexts into parent context is handled "manually" for each pass and (compound) interaction node as needed (e.g. WF-choice and reachability)
		T1 env = ev.popEnv();
		((RPDelBase) del).setEnv(env);
		return visited;
	}
	
	private static <T extends Env<?>> T castEnv(EnvVisitor<T> ev, Env<?> env) 
	{
		@SuppressWarnings("unchecked")
		T tmp = (T) env;
		return tmp;
	}
}
