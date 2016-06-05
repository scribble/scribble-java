package org.scribble.model.local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public class AutParser
{
	public AutParser()
	{

	}
	
	public EndpointGraph parse(String aut)
	{
		Map<Integer, Map<String, Integer>> edges = new HashMap<>();
		int init = -1;
		try
		{
			BufferedReader br = new BufferedReader(new StringReader(aut));
			String line = br.readLine();
			if (line == null || !line.startsWith("des (") || !line.endsWith(")"))
			{
				throw new RuntimeException("Unexpected first line: " + line);
			}
			String[] first = line.substring("des (".length(), line.length() - 1).split(",");
			if (first.length != 3)
			{
				throw new RuntimeException("Unexpected first line: " + line);
			}
			init = Integer.parseInt(first[0]);
			//int trans = Integer.parseInt(first[1]);
			//int states = Integer.parseInt(first[2]);
			while ((line = br.readLine()) != null)
			{
				if (!line.startsWith("(") || !line.endsWith(")"))
				{
					throw new RuntimeException("Unexpected line: " + line);
				}
				String[] read = line.substring(1, line.length()-1).split(",");
				int s = Integer.parseInt(read[0]);
				String a = read[1].substring(1, read[1].length()-1);
				int succ = Integer.parseInt(read[2]);
				Map<String, Integer> tmp = edges.get(s);
				if (tmp == null)
				{
					tmp = new HashMap<>();
					edges.put(s, tmp);
				}
				tmp.put(a, succ);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		Set<Integer> succs = edges.values().stream().flatMap((j) -> j.values().stream()).collect(Collectors.toSet());
		int term = -1;
		Set<Integer> terms = succs.stream().filter((j) -> !edges.containsKey(j)).collect(Collectors.toSet());
		if (terms.size() > 0)
		{
			term = terms.iterator().next();
		}
		LGraphBuilder builder = new LGraphBuilder();
		builder.reset();
		Map<Integer, EndpointState> map = new HashMap<>();
		map.put(init, builder.getEntry());
		if (term != -1)
		{
			map.put(term, builder.getExit());
		}
		map.put(init, builder.getEntry());
		for (int i : edges.keySet())
		{
			if (i != init && i != term)
			{
				map.put(i, builder.newState(Collections.emptySet()));
			}
		}
		for (int i : succs)
		{
			if (!map.containsKey(i) && i != init && i != term)
			{
				map.put(i, builder.newState(Collections.emptySet()));
			}
		}
		for (int i : edges.keySet())
		{
			EndpointState s = map.get(i);
			Map<String, Integer> tmp = edges.get(i);
			if (tmp != null)
			{
				for (String a : tmp.keySet())
				{
					builder.addEdge(s, parseIOAction(a), map.get(tmp.get(a)));
				}
			}
		}
		return builder.finalise();
	}
	
	private static IOAction parseIOAction(String a)
	{
		String peer;
		String action;
		String op;
		String[] pay = null;
		
		int i = a.indexOf("!");
		i = (i == -1) ? a.indexOf("?") : i;
		int j = i+1;
		String tmp = a.substring(j, j+1);
		if (tmp.equals("!") || tmp.equals("?"))
		{
			j++;
		}
		action = a.substring(i, j);
		peer = a.substring(0, i);
		int k = a.indexOf("(");
		op = a.substring(j, k);
		String p = a.substring(k+1, a.length()-1);
		if (!p.isEmpty())
		{
			pay = p.split(",");
		}

		switch (action)
		{
			case "!":
			{
				Payload payload = (pay != null) ? new Payload(Arrays.asList(pay).stream().map((pe) -> new DataType(pe)).collect(Collectors.toList())) : Payload.EMPTY_PAYLOAD;
				return new Send(new Role(peer), new Op(op), payload);  // FIXME: how about MessageSiGnames? -- currently OK, treated as empty payload (cf. ModelAction)
			}
			case "?":
			{
				Payload payload = (pay != null) ? new Payload(Arrays.asList(pay).stream().map((pe) -> new DataType(pe)).collect(Collectors.toList())) : Payload.EMPTY_PAYLOAD;
				return new Receive(new Role(peer), new Op(op), payload);  // FIXME: how about messagesignames?)
			}
			case "!!":
			{
				return new Connect(new Role(peer));
			}
			case "??":
			{
				return new Accept(new Role(peer));
			}
			default:
			{
				throw new RuntimeException("Shouldn't get in here: " + op);
			}
		}
	}
}
