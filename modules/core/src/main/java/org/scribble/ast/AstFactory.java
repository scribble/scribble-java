package org.scribble.ast;

import java.util.List;

import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.local.LAccept;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LConnect;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LDisconnect;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.local.LReceive;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.local.LSend;
import org.scribble.ast.local.LWrapClient;
import org.scribble.ast.local.LWrapServer;
import org.scribble.ast.local.SelfRoleDecl;
import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.qualified.QualifiedNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.DummyProjectionRoleNode;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;


public interface AstFactory
{
	Module Module(ModuleDecl moddecl, List<ImportDecl<?>> imports, List<NonProtocolDecl<?>> data, List<ProtocolDecl<?>> protos);
	
	MessageSigNode MessageSigNode(OpNode op, PayloadElemList payload);
	//PayloadElemList PayloadElemList(List<PayloadElem<?>> payloadelems);
	PayloadElemList PayloadElemList(List<PayloadElem> payloadelems);
	//PayloadElem PayloadElem(PayloadElemNameNode name);
	//UnaryPayloadElem DataTypeElem(PayloadElemNameNode<DataTypeKind> name);
	UnaryPayloadElem UnaryPayloadElem(PayloadElemNameNode name);
	DelegationElem DelegationElem(GProtocolNameNode name, RoleNode role);

	ModuleDecl ModuleDecl(ModuleNameNode fullmodname);
	ImportModule ImportModule(ModuleNameNode modname, ModuleNameNode alias);
	
	MessageSigNameDecl MessageSigNameDecl(String schema, String extName, String source, MessageSigNameNode name);
	DataTypeDecl DataTypeDecl(String schema, String extName, String source, DataTypeNode name);

	GProtocolDecl GProtocolDecl(List<GProtocolDecl.Modifiers> modifiers, GProtocolHeader header, GProtocolDef def);
	GProtocolHeader GProtocolHeader(GProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls);

	RoleDeclList RoleDeclList(List<RoleDecl> rds);
	RoleDecl RoleDecl(RoleNode role);
	//ConnectDecl ConnectDecl(RoleNode src, RoleNode role);
	NonRoleParamDeclList NonRoleParamDeclList(List<NonRoleParamDecl<NonRoleParamKind>> pds);
	<K extends NonRoleParamKind> NonRoleParamDecl<K> NonRoleParamDecl(K kind, NonRoleParamNode<K> name);
	
	GProtocolDef GProtocolDef(GProtocolBlock block);
	GProtocolBlock GProtocolBlock(GInteractionSeq gis);
	GInteractionSeq GInteractionSeq(List<GInteractionNode> gis);

	GMessageTransfer GMessageTransfer(RoleNode src, MessageNode msg, List<RoleNode> dests);
	GConnect GConnect(RoleNode src, MessageNode msg, RoleNode dest);
	//GConnect GConnect(RoleNode src, RoleNode dest);
	GDisconnect GDisconnect(RoleNode src, RoleNode dest);
	GWrap GWrap(RoleNode src, RoleNode dest);
	GChoice GChoice(RoleNode subj, List<GProtocolBlock> blocks);
	GRecursion GRecursion(RecVarNode recvar, GProtocolBlock block);
	GContinue GContinue(RecVarNode recvar);
	GDo GDo(RoleArgList roles, NonRoleArgList args, GProtocolNameNode proto);
	
	RoleArgList RoleArgList(List<RoleArg> roles);
	RoleArg RoleArg(RoleNode role);
	NonRoleArgList NonRoleArgList(List<NonRoleArg> args);
	NonRoleArg NonRoleArg(NonRoleArgNode arg);

	<K extends Kind> NameNode<K> SimpleNameNode(K kind, String identifier);
	<K extends Kind> QualifiedNameNode<K> QualifiedNameNode(K kind, String... elems);
	
	AmbigNameNode AmbiguousNameNode(String identifier);
	<K extends NonRoleParamKind> NonRoleParamNode<K> NonRoleParamNode(K kind, String identifier);
	DummyProjectionRoleNode DummyProjectionRoleNode();

	LProtocolDecl LProtocolDecl(LProtocolHeader header, LProtocolDef def);
	LProtocolHeader LProtocolHeader(LProtocolNameNode name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls);
	SelfRoleDecl SelfRoleDecl(RoleNode namenode);
	LProtocolDef LProtocolDef(LProtocolBlock block);
	LProtocolBlock LProtocolBlock(LInteractionSeq seq);
	LInteractionSeq LInteractionSeq(List<LInteractionNode> actions);

	LSend LSend(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LReceive LReceive(RoleNode src, MessageNode msg, List<RoleNode> dests);
	LConnect LConnect(RoleNode src, MessageNode msg, RoleNode dest);
	LAccept LAccept(RoleNode src, MessageNode msg, RoleNode dest);
	/*LConnect LConnect(RoleNode src, RoleNode dest);
	LAccept LAccept(RoleNode src, RoleNode dest);*/
	LDisconnect LDisconnect(RoleNode self, RoleNode peer);
	LWrapClient LWrapClient(RoleNode self, RoleNode peer);
	LWrapServer LWrapServer(RoleNode self, RoleNode peer);
	LChoice LChoice(RoleNode subj, List<LProtocolBlock> blocks);
	LRecursion LRecursion(RecVarNode recvar, LProtocolBlock block);
	LContinue LContinue(RecVarNode recvar);
	LDo LDo(RoleArgList roles, NonRoleArgList args, LProtocolNameNode proto);

	LProtocolDecl LProjectionDecl(GProtocolName fullname, Role self, LProtocolHeader header, LProtocolDef def);  // del extends that of LProtocolDecl 
}
