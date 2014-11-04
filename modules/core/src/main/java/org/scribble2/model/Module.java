package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;


public class Module extends ModelNodeBase
{
	public final ModuleDecl moddecl;
	public final List<? extends ImportDecl> imports;
	public final List<DataTypeDecl> data;
	public final List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			protos;
	
	public Module( 
			ModuleDecl moddecl,
			List<? extends ImportDecl> imports,
			List<DataTypeDecl> data,
			List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos)
	{
		this.moddecl = moddecl;
		this.imports = new LinkedList<>(imports);
		this.data = new LinkedList<>(data);
		this.protos = new LinkedList<>(protos);
	}

	@Override
	public String toString()
	{
		String s = moddecl.toString();
		for (ImportDecl id : this.imports)
		{
			s += "\n" + id;
		}
		for (DataTypeDecl dtd : this.data)
		{
			s += "\n" + dtd;
		}
		for (ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd : this.protos)
		{
			s += "\n" + pd;
		}
		return s;
	}

	@Override
	protected Module copy()
	{
		return new Module(this.moddecl, this.imports, this.data, this.protos);
	}
}
