package org.scribble.del;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.DelegationElem;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.DelegationProtocolRefChecker;
import org.scribble.visit.NameDisambiguator;
import org.scribble.visit.ProtocolDeclContextBuilder;

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

	//@Override
	//public DelegationElem leaveProtocolDeclContextBuilding(ScribNodeScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder, ScribNode visited) throws ScribbleException  // FIXME: cannot access MessageTransfer roles from here
	// FIXME: apply this pattern to all other existing instances
	protected void leaveMessageTransferInProtocolDeclContextBuilding(MessageTransfer<?> mt, DelegationElem de, ProtocolDeclContextBuilder builder) throws ScribbleException
	{
		GProtocolName gpn = de.proto.toName();  // leaveDisambiguation has fully qualified the target name
		builder.addGlobalProtocolDependency(mt.src.toName(), gpn, de.role.toName());  // FIXME: does it make sense to use projection role as dependency target role? (seems to be used for Job.getProjections)
		mt.getDestinationRoles().forEach((r) -> builder.addGlobalProtocolDependency(r, gpn, de.role.toName()));
	}

	@Override
	public void enterDelegationProtocolRefCheck(ScribNode parent, ScribNode child, DelegationProtocolRefChecker checker) throws ScribbleException
	{
		DelegationElem de = (DelegationElem) child;
		ModuleContext mc = checker.getModuleContext();
		GProtocolName fullname = (GProtocolName) mc.getVisibleProtocolDeclFullName(de.proto.toName());
		if (fullname.equals(mc.getVisibleProtocolDeclFullName(checker.getProtocolDeclOnEntry().header.getDeclName())))  // Explicit done because ProtocolDeclContextBuilder dependencies explicitly include self protocoldecl dependencies (cf. GProtocolDeclDel.addSelfDependency)
		{
			throw new ScribbleException("Recursive protocol dependencies not supported for delegation types: " + de);
		}
	}

	/*@Override
	public ScribNode leaveDelegationProtocolRefCheck(ScribNode parent, ScribNode child, DelegationProtocolRefChecker checker, ScribNode visited) throws ScribbleException
	{

	}*/
}
