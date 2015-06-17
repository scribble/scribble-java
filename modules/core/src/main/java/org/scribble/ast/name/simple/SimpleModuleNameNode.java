package org.scribble.ast.name.simple;

import org.scribble.ast.name.qualified.ModuleNameNode;


@Deprecated
//public class SimpleModuleNameNode extends SimpleCompoundNameNode<ModuleName>
public class SimpleModuleNameNode extends ModuleNameNode
{
	public SimpleModuleNameNode(String name)
	{
		super(name);
	}

	/*@Override
	protected SimpleModuleNameNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		SimpleModuleNameNode smnn = new SimpleModuleNameNode(identifier);
		smnn = (SimpleModuleNameNode) smnn.del(del);
		return smnn;
	}*/

	@Override
	protected SimpleModuleNameNode copy()  // Specified to be internal shallow copy (e.g. used by del)
	{
		//return new SimpleModuleNameNode(this.identifier);
		return new SimpleModuleNameNode(getLastElement());
		//return reconstruct(this.identifier);
	}

	/*@Override
	public SimpleName toName()
	{
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public ModuleName toCompoundName()
	{
		// TODO Auto-generated method stub
		return null;
	}*/
	
	/*@Override
	public ModuleName toName()
	{
		return new ModuleName(this.identifier);
	}*/
}
