package org.scribble.codegen.java;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.Module;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.PayloadType;

public class InputFutureBuilder
{
	protected static final String SCRIBFUTURE_CLASS = "org.scribble.net.ScribFuture";

	protected final StateChannelApiGenerator apigen;
	//protected final EndpointState curr;
	//protected final String className;

	private final ClassBuilder cb;
	private final IOAction a;

	// Pre: cb is ReceiveSocketBuilder
	public InputFutureBuilder(StateChannelApiGenerator apigen, ClassBuilder cb, IOAction a)
	{
		this.apigen = apigen;
		//this.curr = curr;
		this.cb = cb;
		this.a = a;
	}

	protected ClassBuilder build()
	{
		final String FUTURE_PARAM = "fut";
		Module main = this.apigen.getMainModule();
		GProtocolName gpn = this.apigen.getGProtocolName();

		String futureClass = cb.getName() + "_Future";  // Fresh enough? need only one future class per receive (unary receive)

		//cb.addImports("java.util.concurrent.CompletableFuture");  // "parent" cb, not the future class
		//cb.addImports("java.util.concurrent.ExecutionException");

		//ClassBuilder future = cb.newClass();  // FIXME: inner class
		// Duplicated from BranchInterfaceBuilder -- FIXME: factor out
		ClassBuilder future = new ClassBuilder();
		future.setPackage(SessionApiGenerator.getStateChannelPackageName(gpn, this.apigen.getSelf()));  // FIXME: factor out with ScribSocketBuilder
		future.addImports("java.io.IOException");
		future.addImports("java.util.concurrent.CompletableFuture");  // "parent" cb, not the future class
		future.addModifiers(InterfaceBuilder.PUBLIC);

		future.setName(futureClass);
		future.setSuperClass(SCRIBFUTURE_CLASS);
		List<String> types = new LinkedList<>(); 
		if (a.mid.isOp())
		{
			if (!a.payload.isEmpty())
			{
				int i = 1;
				for (PayloadType<?> pt : a.payload.elems)
				{
					String type = main.getDataTypeDecl((DataType) pt).extName;
					types.add(type);
					FieldBuilder f = future.newField("pay" + i++);
					f.setType(type);
					f.addModifiers(ClassBuilder.PUBLIC);
				}
			}
		}
		else
		{
			String type = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName()).extName;
			types.add(type);
			FieldBuilder f = future.newField("msg");
			f.setType(type);
			f.addModifiers(ClassBuilder.PUBLIC);
		}

		MethodBuilder cons = future.newConstructor("CompletableFuture<" + StateChannelApiGenerator.SCRIBMESSAGE_CLASS + "> " + FUTURE_PARAM);
		cons.addModifiers(ClassBuilder.PROTECTED);
		cons.addBodyLine(ClassBuilder.SUPER + "(" + FUTURE_PARAM + ");");

		MethodBuilder sync = future.newMethod("sync");
		sync.addModifiers(ClassBuilder.PUBLIC);
		sync.setReturn(futureClass);
		//sync.addExceptions("ExecutionException", "InterruptedException");
		sync.addExceptions("IOException");
		String ln = (a.mid.isOp() && a.payload.isEmpty()) ? "" : StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " m = ";
		ln += ClassBuilder.SUPER + ".get();";
		sync.addBodyLine(ln);
		if (a.mid.isOp())
		{
			if (!a.payload.isEmpty())
			{
				int i = 1;
				for (String type : types)
				{
					sync.addBodyLine(ClassBuilder.THIS + "." + "pay" + i + " = (" + type + ") m.payload[" + (i - 1) + "];");
					i++;
				}
			}
		}
		else
		{
			sync.addBodyLine(ClassBuilder.THIS + "." + "msg" + " = (" + types.get(0) + ") m;");
		}
		sync.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.THIS + ";");

		return future;
	}
}
