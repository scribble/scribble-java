package org.scribble2.foo.ast;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;


public class Module extends ScribbleASTBase
{
	/*public final ModuleDecl moddecl;
	public final List<? extends ImportDecl> imports;
	public final List<DataTypeDecl> data;
	public final List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
			protos;*/
	
	public Module(Token t/*, 
			ModuleDecl moddecl,
			List<? extends ImportDecl> imports,
			List<DataTypeDecl> data,
			List<? extends ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>> protos*/)
	{
		super(t);
		/*this.moddecl = moddecl;
		this.imports = new LinkedList<>(imports);
		this.data = new LinkedList<>(data);
		this.protos = new LinkedList<>(protos);*/
	}

	/*@Override
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
	}*/
}
