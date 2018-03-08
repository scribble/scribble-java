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
package org.scribble.codegen.java.statechanapi.ioifaces;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.statechanapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// Cf. ScribSocketGenerator
// Partial I/O State I/f generator -- Successor Interfaces and cast methods added later
public abstract class IOStateIfaceGen extends IOIfaceGen
{
	public static final Comparator<EAction> IOACTION_COMPARATOR = new Comparator<EAction>()
			{
				@Override
				public int compare(EAction a1, EAction a2)
				{
					return ActionIfaceGen.getActionInterfaceName(a1).compareTo(ActionIfaceGen.getActionInterfaceName(a2));
				}
			};

	protected final Map<EAction, InterfaceBuilder> actions;
	
	protected final InterfaceBuilder ib = new InterfaceBuilder();

	// Preds can be null
	public IOStateIfaceGen(StateChannelApiGenerator apigen, Map<EAction, InterfaceBuilder> actions, EState curr)
	{
		super(apigen, curr);
		this.actions = Collections.unmodifiableMap(actions);
	}
	
	@Override
	public InterfaceBuilder generateType() throws ScribbleException
	{
		constructInterface();
		return this.ib;
	}

	protected void constructInterface() throws ScribbleException
	{
		addHeader();
		addSuccessorParamsAndActionInterfaces();
		//addSuccessorInterfaces();  // Do later (different states may share the same IO State I/f, don't know all successors for this I/f yet (only this state)
		addCastField();
	}

	protected void addHeader()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		String packname = IOInterfacesGenerator.getIOInterfacePackageName(gpn, self);
		String ifname = getIOStateInterfaceName(self, this.curr);

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addImports(SessionApiGenerator.getRolesPackageName(gpn) + ".*");
		this.ib.addModifiers(JavaBuilder.PUBLIC);
	}
	
	protected void addCastField()
	{
		String ifname = getIOStateInterfaceName(this.apigen.getSelf(), this.curr);
		//Set<EAction> as = this.curr.getActions();
		List<EAction> as = this.curr.getActions();

		FieldBuilder cast = this.ib.newField("cast");
		cast.addModifiers(TypeBuilder.PUBLIC, TypeBuilder.STATIC, TypeBuilder.FINAL);
		cast.setType(ifname + "<" + IntStream.range(1, as.size()+1).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">");  // FIXME: factor out
		cast.setExpression("null");
	}

	protected void addSuccessorParamsAndActionInterfaces()
	{
		int i = 1;
		for (EAction a : this.curr.getActions().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			if (a.isSend() || a.isReceive())  // HACK FIXME
			{
				String actif = this.actions.get(a).getName();
				this.ib.addParameters("__Succ" + i + " extends " + SuccessorIfaceGen.getSuccessorInterfaceName(a));
				this.ib.addInterfaces(actif + "<__Succ" + i + ">");
				i++;
			}
		}
	}

	/*protected void addSuccessorInterfaces()
	{
		if (this.preds != null)
		{
			System.out.println("AA: " + this.ib.getName() + ", " + this.preds.stream().map((p) -> p.getName()).collect(Collectors.toList()));
			
			for (InterfaceBuilder pred : this.preds)
			{
				this.ib.addInterfaces(pred.getName());  // Adds Successor Interfaces to this I/O State Interface
			}
		}
	}*/

	// Pre: s non-terminal
	public static String getIOStateInterfaceName(Role self, EState s)
	{
		String name = null;
		switch (s.getStateKind())
		{
			case OUTPUT:      name = "Select";  break;
			case UNARY_INPUT: name = "Receive"; break;
			case POLY_INPUT:  name = "Branch";  break;
			case TERMINAL:    throw new RuntimeScribbleException("Shouldn't get in here: " + s);
			default:          throw new RuntimeException("(TODO) I/O interface generation: " + s.getStateKind());
		}
		name = name + "_" + self + "_" + s.getActions().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionIfaceGen.getActionString(a)).collect(Collectors.joining("__"));
		checkIOStateInterfaceNameLength(name);
		return name;
	}
	
	// 255 is Linux, Windows, etc max file name length (Java is 65535)
	public static void checkIOStateInterfaceNameLength(String name) throws RuntimeScribbleException
	{
		if (name.length() > 250)  // .java
		{
			throw new RuntimeScribbleException("I/O Interface name too long (max 255): " + name);
		}
	}
}
