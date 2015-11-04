package org.scribble.codegen.java.endpointapi;

import java.util.stream.Collectors;

import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.util.Builder;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;

public class CaseSocketBuilder extends ScribSocketBuilder
{
	public CaseSocketBuilder(StateChannelApiGenerator apigen, EndpointState curr)
	{
		super(apigen, curr);
	}
	
	@Override
	protected String getClassName()
	{
		return super.getClassName() + "_Cases";
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
		String enumClassName = branchName + "." + BranchSocketBuilder.getBranchEnumClassName(this.apigen, this.curr);

		MethodBuilder ctor = super.addConstructor();
		ctor.addParameters(enumClassName + " " + CASE_OP_PARAM, StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " " + CASE_MESSAGE_PARAM);
		ctor.addBodyLine(Builder.THIS + "." + CASE_OP_FIELD + " = " + CASE_OP_PARAM + ";");
		ctor.addBodyLine(Builder.THIS + "." + CASE_MESSAGE_FIELD + " = " + CASE_MESSAGE_PARAM + ";");
		return ctor;
	}

	//private String constructCaseClass(EndpointState curr, Module main)
	@Override
	protected void addMethods()
	{
		String branchName = this.apigen.getSocketClassName(curr);  // Name of "parent" branch class (curr state is the branch state)
		String enumClassName = branchName + "." + BranchSocketBuilder.getBranchEnumClassName(this.apigen, this.curr);
		//String className = newClassName();  // Name of branch-receive class

		FieldBuilder fb1 = cb.newField(CASE_OP_FIELD);  // The op enum, for convenient switch/if/etc by user (correctly derived by code generation from the received ScribMessage)
		fb1.addModifiers(Builder.PUBLIC, Builder.FINAL);
		fb1.setType(enumClassName);
		
		FieldBuilder fb2 = cb.newField(CASE_MESSAGE_FIELD);  // The received ScribMessage (branch-check checks the user-selected receive op against the ScribMessage op)
		fb2.addModifiers(Builder.PRIVATE, Builder.FINAL);
		fb2.setType(StateChannelApiGenerator.SCRIBMESSAGE_CLASS);

		for (IOAction a : curr.getAcceptable())
		{
			EndpointState succ = curr.accept(a);
			addCaseReceiveMethod(cb, a, succ);
			if (!a.payload.isEmpty() || a.mid.isMessageSigName())
			{
				addCaseReceiveDiscardMethod(cb, a, succ);
			}
		}

		this.apigen.addClass(cb);  // CaseSocketBuilder used by BranchSocketBuilder, not EndpointApiGenerator
	}

	private MethodBuilder makeCaseReceiveHeader(ClassBuilder cb, IOAction a, EndpointState succ)
	{
		//final String ROLE_PARAM = "role";
		String opClass = SessionApiGenerator.getOpClassName(a.mid);

		MethodBuilder mb = cb.newMethod("receive");
		mb.addModifiers(Builder.PUBLIC);
		setNextSocketReturnType(mb, succ);
		mb.addParameters(opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);  // More params may be added later (payload-arg/future Buffs)
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");//, "ExecutionException", "InterruptedException");
		return mb;
	}

	private void addCaseReceiveMethod(ClassBuilder cb, IOAction a, EndpointState succ)
	{
		Module main = this.apigen.getMainModule();

		MethodBuilder mb = makeCaseReceiveHeader(cb, a, succ);
		if (a.mid.isOp())
		{
			ReceiveSocketBuilder.addReceiveOpParams(mb, main, a);
			mb.addBodyLine(Builder.SUPER + ".use();");
			addBranchCheck(getSessionApiOpConstant(a.mid), mb, CASE_MESSAGE_FIELD);
			ReceiveSocketBuilder.addPayloadBuffSetters(main, a, mb);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			ReceiveSocketBuilder.addReceiveMessageSigNameParams(mb, a, msd);
			mb.addBodyLine(Builder.SUPER + ".use();");
			addBranchCheck(getSessionApiOpConstant(a.mid), mb, CASE_MESSAGE_FIELD);
			mb.addBodyLine(CASE_ARG_PREFIX + "." + BUFF_VAL_FIELD + " = (" + msd.extName + ") " + CASE_MESSAGE_FIELD + ";");
		}
		addReturnNextSocket(mb, succ);
	}

	private void addCaseReceiveDiscardMethod(ClassBuilder cb, IOAction a, EndpointState succ)
	{
		Module main = this.apigen.getMainModule();

		MethodBuilder mb = makeCaseReceiveHeader(cb, a, succ);
		mb.addAnnotations("@SuppressWarnings(\"unchecked\")");
		String ln = Builder.RETURN + " ";
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
		String op = Builder.THIS + "." + messageField + "." + StateChannelApiGenerator.SCRIBMESSAGE_OP_FIELD;
		mb.addBodyLine("if (!" + op + ".equals(" + opClassName + ")) {");
		mb.addBodyLine(1, "throw " + Builder.NEW + " " + StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS + "(\"Wrong branch, received: \" + " + op + ");");
		mb.addBodyLine("}");
	}
}
