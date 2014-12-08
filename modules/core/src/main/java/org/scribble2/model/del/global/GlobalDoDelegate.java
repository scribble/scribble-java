package org.scribble2.model.del.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.context.ModuleContext;
import org.scribble2.model.Choice;
import org.scribble2.model.Do;
import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.Interrupt;
import org.scribble2.model.Interruptible;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Parallel;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.Recursion;
import org.scribble2.model.del.CompoundInteractionNodeDelegate;
import org.scribble2.model.global.GlobalChoice;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.SubprotocolVisitor;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.ScopedMessage;
import org.scribble2.sesstype.ScopedMessageSignature;
import org.scribble2.sesstype.ScopedSubprotocolSignature;
import org.scribble2.sesstype.SubprotocolSignature;
import org.scribble2.sesstype.name.Operator;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;
import org.scribble2.sesstype.name.ScopedMessageParameter;
import org.scribble2.util.MessageMap;
import org.scribble2.util.ScribbleException;

public class GlobalDoDelegate extends CompoundInteractionNodeDelegate
{
	public void enter(Do doo, WellFormedChoiceChecker checker)
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
			}*/
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
	}
}
