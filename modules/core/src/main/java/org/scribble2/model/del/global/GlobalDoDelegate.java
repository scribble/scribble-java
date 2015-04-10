package org.scribble2.model.del.global;

import java.util.List;

import org.scribble2.model.Do;
import org.scribble2.model.del.SimpleInteractionNodeDelegate;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.ScopedSubprotocolSignature;
import org.scribble2.sesstype.SubprotocolSignature;
import org.scribble2.sesstype.name.Scope;
import org.scribble2.util.MessageMap;

// FIXME: simple or compound?
public class GlobalDoDelegate extends SimpleInteractionNodeDelegate
{
	/*public void enter(Do doo, WellFormedChoiceChecker checker)
	{
		WellFormedChoiceEnv copy = copy();
		SubprotocolSignature subsig = checker.peekStack().sig;
		if (!copy.subsigs.containsKey(subsig))
		{
			copy.recording.add(subsig);
			copy.subsigs.put(subsig, new MessageMap<>());
		}
		checker.setEnv(copy);
	}

	public void leave(Do doo, WellFormedChoiceChecker checker)
	{
		WellFormedChoiceEnv copy = copy();
		ScopedSubprotocolSignature subsig = checker.peekStack();
		if (copy.recording.contains(subsig.sig))
		{
			copy.recording.remove(subsig.sig);
		}

		//int isCycle = checker.isCycle();
		//if (isCycle != -1)
		if (checker.isCycle())
		{
			//addSubprotocolEnabled(subsig);
			List<ScopedSubprotocolSignature> stack = checker.getStack();
			stack = stack.subList(0, stack.size() - 1);
			Scope scope = stack.get(stack.size() - 1).scope;  // Doesn't include final cycle sig scope
			/*for (int i = stack.size() - 2; i >= 0; i--)
			{
				SubprotocolSignature tmp = stack.get(i);
				if (tmp.equals(subsig))
				{
					break;
				}
				addSubprotocolEnabled(tmp);
			}* /
			int entry = checker.getCycleEntryIndex();
			Scope prev = (entry == 0) ? Scope.ROOT_SCOPE : stack.get(entry).scope;
			for (int i = checker.getCycleEntryIndex(); i < stack.size(); i++)
			{
				ScopedSubprotocolSignature ssubsig = stack.get(i);
				if (!prev.equals(ssubsig.scope))
				{
					scope = new Scope(scope, ssubsig.scope.getSimpleName());
					prev = ssubsig.scope;
				}
				copy.addSubprotocolEnabled(scope, ssubsig.sig);
			}
		}

		//return copy;
		checker.setEnv(copy);
	}*/
}
