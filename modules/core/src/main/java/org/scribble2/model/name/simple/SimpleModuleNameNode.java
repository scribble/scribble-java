package org.scribble2.model.name.simple;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.name.ModuleName;


public class SimpleModuleNameNode extends SimpleNameNode
{
	public SimpleModuleNameNode(String name)
	{
		super(name);
	}

	@Override
	protected SimpleModuleNameNode reconstruct(String identifier)
	{
		ModelDelegate del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		SimpleModuleNameNode smnn = new SimpleModuleNameNode(identifier);
		smnn = (SimpleModuleNameNode) smnn.del(del);
		return smnn;
	}

	@Override
	protected SimpleModuleNameNode copy()  // Specified to be internal shallow copy (e.g. used by del)
	{
		return new SimpleModuleNameNode(this.identifier);
		//return reconstruct(this.identifier);
	}
	
	/*@Override
	public ModuleName toName()
	{
		return new ModuleName(this.identifier);
	}*/
}