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
package org.scribble.core.model.global;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.scribble.core.job.Core;
import org.scribble.core.job.CoreArgs;
import org.scribble.core.model.endpoint.actions.ESend;
import org.scribble.core.type.name.Role;
import org.scribble.util.ScribException;


// For doing validation operations on an SGraph (cf. EFsm, use Graph as an FSM)
public class SModel
{
	public final SGraph graph;
	
	private Core core;  // only used for debug printing  // CHECKME: refactor?  but avoiding in constructor to keep mf more independent/uniform

	public SModel(SGraph graph)
	{
		this.graph = graph;
	}

	public void validate(Core core) throws ScribException
	{
		this.core = core;

		String errorMsg = "";

		errorMsg = getSafetyErrors().values().stream()
				.map(x -> x.toErrorMessage(this.graph))
				.collect(Collectors.joining(""));
		
		if (!core.config.args.get(CoreArgs.NO_PROGRESS))
		{
			//job.debugPrintln("(" + this.graph.proto + ") Checking progress: ");  // Incompatible with current errorMsg approach*/
			errorMsg = checkProgress(errorMsg);
		}

		if (!errorMsg.equals(""))
		{
			//throw new ScribbleException("\n" + init.toDot() + errorMsg);
			throw new ScribException(errorMsg);
		}
		//job.debugPrintln("(" + this.graph.proto + ") Progress satisfied.");  // Also safety... current errorMsg approach
	}

	protected SortedMap<Integer, SStateErrors> getSafetyErrors()  // s.id key lighter than full SConfig
	{
		SortedMap<Integer, SStateErrors> res = new TreeMap<>();
		this.graph.states.entrySet().stream().forEach(x ->
				res.put(x.getKey(), x.getValue().getErrors()));
		return res;
	}

	private String checkProgress(String errorMsg) throws ScribException
	{
		Set<Set<SState>> termsets = this.graph.getTermSets();
		for (Set<SState> termset : termsets)
		{
			/*job.debugPrintln("(" + this.graph.proto + ") Checking terminal set: "
						+ termset.stream().map((i) -> new Integer(all.get(i).id).toString()).collect(Collectors.joining(",")));  // Incompatible with current errorMsg approach*/

			Set<Role> starved = checkRoleProgress(termset);
			Map<Role, Set<ESend>> ignored = 
					checkEventualReception(termset);
			errorMsg = appendProgressErrorMessages(errorMsg, starved, ignored,
					termset);
		}
		return errorMsg;
	}

	protected String appendProgressErrorMessages(String errorMsg,
			Set<Role> starved, Map<Role, Set<ESend>> ignored, Set<SState> termset)
	{
		if (!starved.isEmpty())
		{
			errorMsg += "\nRole progress violation for " + starved
					+ " in session state terminal set:\n    "
					+ termSetToString(termset, this.graph.states);
		}
		if (!ignored.isEmpty())
		{
			errorMsg += "\nEventual reception violation for " + ignored
					+ " in session state terminal set:\n    "
					+ termSetToString(termset, this.graph.states);
		}
		return errorMsg;
	}
	
	protected String termSetToString(Set<SState> termset,
			Map<Integer, SState> all)
	{
		return this.core.config.args.get(CoreArgs.VERBOSE)
				? termset.stream().map(x -> x.toString())
						.collect(Collectors.joining(","))
				: termset.stream().map(x -> Integer.toString(x.id))
						.collect(Collectors.joining(","));
	}

	// Could subsume terminal state check, if terminal sets included size 1 with reflexive reachability (but not a good approach)
	protected Set<Role> checkRoleProgress(Set<SState> termset)
			throws ScribException
	{
		SState s = termset.iterator().next();  // Pick any state to check canSafelyTerminate (later, if needed), if non progressing equivalent for all
		Set<Role> todo = new HashSet<>(s.config.efsms.keySet());
		for (Iterator<SState> i = termset.iterator(); 
				i.hasNext() && !todo.isEmpty(); )
		{
			i.next().getActions().stream().map(x -> x.subj).distinct()
					.forEach(x -> todo.remove(x));
				// cf. a.containsRole(r) -- implies obj will be subj for the counterpart action somewhere in the termset?
		}

		// todo is now all roles that are not the subj of any action in the termset
		return todo.stream()
				.filter(x -> !s.config.canSafelyTerminate(x)
						&& s.config.queues.getQueue(x).values().stream()
								.allMatch(y -> y == null))  // Check empty queues for starved -- o/w, is a stuck-message
				.collect(Collectors.toSet());
	}

	// cf. eventual stability (could also check within termsets)
	protected Map<Role, Set<ESend>> checkEventualReception(
			Set<SState> termset) throws ScribException
	{
		Set<Role> roles = //states.get(termset.iterator().next())
				termset.iterator().next()
				.config.efsms.keySet();

		Iterator<SState> i = termset.iterator();
		Map<Role, Map<Role, ESend>> b0 = i.next()//states.get(i.next())
				.config.queues
				.getQueues();
		while (i.hasNext())
		{
			SState s = i.next();//states.get(i.next());
			SingleBuffers b = s.config.queues;
			for (Role r1 : roles)
			{
				for (Role r2 : roles)
				{
					ESend s0 = b0.get(r1).get(r2);
					if (s0 != null)
					{
						ESend tmp = b.getQueue(r1).get(r2);
						if (tmp == null)
						{
							b0.get(r1).put(r2, null);
						}
					}
				}
			}
		}
	
		Map<Role, Set<ESend>> ignored = new HashMap<>();
		for (Role r1 : roles)
		{
			for (Role r2 : roles)
			{
				ESend m = b0.get(r1).get(r2);
				if (m != null)
				{
					Set<ESend> tmp = ignored.get(r2);
					if (tmp == null)
					{
						tmp = new HashSet<>();
						ignored.put(r2, tmp);
					}
					tmp.add(m);
				}
			}
		}
		return ignored;
	}
}
