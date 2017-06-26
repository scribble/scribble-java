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
package org.scribble.codegen.java.endpointapi;

import java.util.stream.Collectors;

import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.endpointapi.ioifaces.BranchIfaceGen;
import org.scribble.codegen.java.endpointapi.ioifaces.IOStateIfaceGen;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.Role;

public class CaseSockGen extends ScribSockGen
{
	public CaseSockGen(StateChannelApiGenerator apigen, EState curr)
	{
		super(apigen, curr);
	}
	
	@Override
	protected String getClassName()
	{
		return getCaseSocketName(super.getClassName());
	}

	@Override
	protected String getSuperClassType()
	{
		return CASESOCKET_CLASS + "<" + getSessionClassName() + ", " + getSelfClassName() + ">";
	}

	@Override
	protected void addImports()
	{
		super.addImports();
		this.cb.addImports(getOpsPackageName() + ".*");
	}
	
	@Override
	protected void addInitialStateConstructor()
	{
		return;
	}
	
	@Override
	protected MethodBuilder addConstructor()
	{
		String branchName = this.apigen.getSocketClassName(curr);  // Name of "parent" branch class (curr state is the branch state)
		String enumClassName = branchName + "." + BranchSockGen.getBranchEnumClassName(this.apigen, this.curr);

		MethodBuilder ctor = super.addConstructor();
		ctor.addParameters(enumClassName + " " + CASE_OP_PARAM, StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " " + CASE_MESSAGE_PARAM);
		ctor.addBodyLine(JavaBuilder.THIS + "." + CASE_OP_FIELD + " = " + CASE_OP_PARAM + ";");
		ctor.addBodyLine(JavaBuilder.THIS + "." + CASE_MESSAGE_FIELD + " = " + CASE_MESSAGE_PARAM + ";");
		return ctor;
	}

	//private String constructCaseClass(EndpointState curr, Module main)
	@Override
	protected void addMethods() throws ScribbleException
	{
		String branchName = this.apigen.getSocketClassName(curr);  // Name of "parent" branch class (curr state is the branch state)
		String enumClassName = branchName + "." + BranchSockGen.getBranchEnumClassName(this.apigen, this.curr);
		//String className = newClassName();  // Name of branch-receive class

		FieldBuilder fb1 = this.cb.newField(CASE_OP_FIELD);  // The op enum, for convenient switch/if/etc by user (correctly derived by code generation from the received ScribMessage)
		fb1.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.FINAL);
		fb1.setType(enumClassName);
		
		FieldBuilder fb2 = this.cb.newField(CASE_MESSAGE_FIELD);  // The received ScribMessage (branch-check checks the user-selected receive op against the ScribMessage op)
		fb2.addModifiers(JavaBuilder.PRIVATE, JavaBuilder.FINAL);
		fb2.setType(StateChannelApiGenerator.SCRIBMESSAGE_CLASS);

		for (EAction a : this.curr.getActions())
		{
			EState succ = this.curr.getSuccessor(a);
			addReceiveMethod(this.cb, a, succ);
			addCaseReceiveMethod(this.cb, a, succ);
			if (!a.payload.isEmpty() || a.mid.isMessageSigName())
			{
				addCaseReceiveDiscardMethod(this.cb, a, succ);
			}
		}
		
		if (!this.apigen.skipIOInterfacesGeneration)
		{
			Role self = this.apigen.getSelf();
			MethodBuilder mb = this.cb.newMethod("getOp");
			mb.addAnnotations("@Override");
			mb.addModifiers(JavaBuilder.PUBLIC);
			mb.setReturn(IOStateIfaceGen.getIOStateInterfaceName(self, this.curr) + "." + BranchIfaceGen.getBranchInterfaceEnumName(self, this.curr));
			mb.addBodyLine(JavaBuilder.RETURN + " " + JavaBuilder.THIS + "." + CASE_OP_FIELD + ";");
		}

		this.apigen.addTypeDecl(this.cb);  // CaseSocketBuilder used by BranchSocketBuilder, not EndpointApiGenerator
	}

	// Same as in ReceiveSocketGenerator
	private MethodBuilder makeReceiveHeader(ClassBuilder cb, EAction a, EState succ) throws ScribbleException
	{
		MethodBuilder mb = cb.newMethod();
		ReceiveSockGen.setReceiveHeaderWithoutReturnType(this.apigen, a, mb);
		setNextSocketReturnType(this.apigen, mb, succ);
		return mb;
	}

	private void addReceiveMethod(ClassBuilder cb, EAction a, EState succ) throws ScribbleException
	{
		Module main = this.apigen.getMainModule();

		MethodBuilder mb = makeReceiveHeader(cb, a, succ);
		if (a.mid.isOp())
		{
			mb.addBodyLine(JavaBuilder.SUPER + ".use();");
			addBranchCheck(getSessionApiOpConstant(a.mid), mb, CASE_MESSAGE_FIELD);
			ReceiveSockGen.addPayloadBuffSetters(main, a, mb);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			mb.addBodyLine(JavaBuilder.SUPER + ".use();");
			addBranchCheck(getSessionApiOpConstant(a.mid), mb, CASE_MESSAGE_FIELD);
			mb.addBodyLine(CASE_ARG_PREFIX + "." + BUFF_VAL_FIELD + " = (" + msd.extName + ") " + CASE_MESSAGE_FIELD + ";");
		}
		addReturnNextSocket(mb, succ);
	}

	private MethodBuilder makeCaseReceiveHeader(ClassBuilder cb, EAction a, EState succ) throws ScribbleException
	{
		MethodBuilder mb = cb.newMethod();
		setCaseReceiveHeaderWithoutReturnType(this.apigen, a, mb);
		setNextSocketReturnType(this.apigen, mb, succ);
		return mb;
	}

	private void addCaseReceiveMethod(ClassBuilder cb, EAction a, EState succ) throws ScribbleException
	{
		MethodBuilder mb = makeCaseReceiveHeader(cb, a, succ);
		String ln = JavaBuilder.RETURN + " " + "receive(" + getSessionApiRoleConstant(a.obj) + ", ";
		//ln += mb.getParameters().stream().map((p) -> p.substring(p.indexOf(" ") + 1, p.length())).collect(Collectors.joining(", ")) + ");";
		boolean first = true;
		for (String param : mb.getParameters())
		{
			if (first)
			{
				first = false;
			}
			else
			{
				ln += ", "; 
			}
			if (param.contains("<"))
			{
				param = param.substring(param.lastIndexOf('>') + 1, param.length());
			}
			ln += param.substring(param.indexOf(" ") + 1, param.length());
		}
		mb.addBodyLine(ln + ");");
	}

	private void addCaseReceiveDiscardMethod(ClassBuilder cb, EAction a, EState succ)
	{
		Module main = this.apigen.getMainModule();

		MethodBuilder mb = cb.newMethod();
		setCaseReceiveDiscardHeaderWithoutReturnType(this.apigen, a, mb);
		setNextSocketReturnType(this.apigen, mb, succ);
		
		mb.addAnnotations("@SuppressWarnings(\"unchecked\")");
		String ln = JavaBuilder.RETURN + " ";
		ln += "receive(" + CASE_OP_PARAM + ", ";
		if (a.mid.isOp())
		{
			ln += a.payload.elems.stream()
						 .map((pt) -> getGarbageBuf(main.getDataTypeDecl(((DataType) pt)).extName)).collect(Collectors.joining(", ")) + ");";
		}
		else
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // Factor out? (send/receive/branchreceive/...)
			ln += getGarbageBuf(msd.extName) + ");";
		}
		mb.addBodyLine(ln);
	}

	private static void addBranchCheck(String opClassName, MethodBuilder mb, String messageField)
	{
		String op = JavaBuilder.THIS + "." + messageField + "." + StateChannelApiGenerator.SCRIBMESSAGE_OP_FIELD;
		mb.addBodyLine("if (!" + op + ".equals(" + opClassName + ")) {");
		mb.addBodyLine(1, "throw " + JavaBuilder.NEW + " " + StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS + "(\"Wrong branch, received: \" + " + op + ");");
		mb.addBodyLine("}");
	}

	// As for ReceiveSocket, but without peer param
	public static void setCaseReceiveHeaderWithoutReturnType(StateChannelApiGenerator apigen, EAction a, MethodBuilder mb) throws ScribbleException
	{
		//final String ROLE_PARAM = "role";
		Module main = apigen.getMainModule();  // FIXME: main not necessarily the right module?
		String opClass = SessionApiGenerator.getOpClassName(a.mid);

		mb.setName("receive");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.addParameters(opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);  // More params may be added later (payload-arg/future Buffs)
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");//, "ExecutionException", "InterruptedException");
		if (a.mid.isOp())
		{
			ReceiveSockGen.addReceiveOpParams(mb, main, a, true);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			ReceiveSockGen.addReceiveMessageSigNameParams(mb, msd, true);
		}
	}

	public static void setCaseReceiveDiscardHeaderWithoutReturnType(StateChannelApiGenerator apigen, EAction a, MethodBuilder mb)
	{
		// Duplicated from makeCaseReceiveHeader, without parameters
		final String opClass = SessionApiGenerator.getOpClassName(a.mid);

		mb.setName("receive");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.addParameters(opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);  // More params may be added later (payload-arg/future Buffs)
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");//, "ExecutionException", "InterruptedException");
	}
	
	public static String getCaseSocketName(String branchSocketName)
	{
		return branchSocketName + "_Cases";
	}
}
