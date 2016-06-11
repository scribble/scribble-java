package org.scribble.del;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.DelegationElem;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.NameDisambiguator;

public class DelegationElemDel extends ScribDelBase
{
	public DelegationElemDel()
	{
	
	}

	// Duplicated from DoDel
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		DelegationElem de = (DelegationElem) child;
		GProtocolName gpn = de.proto.toName();
		if (!mc.isVisibleProtocolDeclName(gpn))
		{
			throw new ScribbleException("Protocol decl not visible: " + gpn);
		}
	}

	// Duplicated from DoDel
	//@Override
	//public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	public DelegationElem visitForNameDisambiguation(NameDisambiguator disamb, DelegationElem de) throws ScribbleException
	{
		//DelegationElem de = (DelegationElem) visited;
		ModuleContext mc = disamb.getModuleContext();
		GProtocolName fullname = (GProtocolName) mc.getVisibleProtocolDeclFullName(de.proto.toName());

		Role rn = de.role.toName();
		ProtocolDecl<Global> gpd = disamb.getJobContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
		if (!gpd.header.roledecls.getRoles().contains(rn))
		{
			throw new ScribbleException("Invalid delegation role: " + de);
		}

		GProtocolNameNode pnn = (GProtocolNameNode) AstFactoryImpl.FACTORY.QualifiedNameNode(fullname.getKind(), fullname.getElements());  // Not keeping original namenode del
		return de.reconstruct(pnn, de.role);
	}

	/*// Is this needed?  Or DataTypeNodes always created from AmbigNameNode? (in this same pass)
	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited)
			throws ScribbleException
	{
		ModuleContext mc = disamb.getModuleContext();
		DataTypeNode dtn = (DataTypeNode) visited;
		DataType fullname = mc.getVisibleDataTypeFullName(dtn.toName());
		return (DataTypeNode)
				AstFactoryImpl.FACTORY.QualifiedNameNode(DataTypeKind.KIND, fullname.getElements());  // Didn't keep original del
	}*/
}
