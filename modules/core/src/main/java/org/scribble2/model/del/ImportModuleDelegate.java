package org.scribble2.model.del;

import org.scribble2.model.ImportModule;
import org.scribble2.model.ModelNode;
import org.scribble2.model.visit.Projector;

public class ImportModuleDelegate extends ModelDelegateBase
{
	public ImportModuleDelegate()
	{

	}

	/*Handled up front in ModuleContext
	@Override
	//public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder)
	public void enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder)
	{
		//builder.setModuleContext(new ModuleContext(builder.job.getJobContext(), (Module) child));
		builder.setModuleContext(new ModuleContext((Module) child));
		//return builder;
	}

	// Maybe better to create on enter, so can be used during the context build pass (Context would need to be "cached" in the visitor to be accessed)
	@Override
	public ModelNode leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		//visited = visited.del(new ModuleDelegate(builder.getJobContext(), (Module) visited));
		ImportModuleDelegate del = copy();  // FIXME: should be a deep clone in principle
		//del.context = new ModuleContext(builder.getJobContext(), (Module) visited);
		del.context = builder.getModuleContext();
		return visited.del(del);
	}*/

	@Override
	public ImportModule leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) //throws ScribbleException
	{
		/*// Don't know target protocol
		PrimitiveNameNode pnn = new PrimitiveNameNode(null, proj.getProjectedModuleName(this.fmn.smn, ...));
		ModuleNameNode mnn = new ModuleNameNode(this.fmn.pn, pnn);
		return new ImportModule(null, mnn, this.alias);*/
		//throw new RuntimeException("TODO");
		return (ImportModule) visited;  // FIXME: go through dependencies
	}
}