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
package org.scribble.codegen.java.statechanapi;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.DataDecl;
import org.scribble.ast.SigDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.ConstructorBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.SigName;
import org.scribble.core.type.name.PayElemType;
import org.scribble.util.ScribException;

public class InputFutureGen extends AuxStateChanTypeGen
{
	protected static final String SCRIBFUTURE_CLASS = "org.scribble.runtime.util.ScribFuture";

	private final EAction a;

	// Pre: cb is ReceiveSocketBuilder
	public InputFutureGen(StateChannelApiGenerator apigen, ClassBuilder parent, EAction a)
	{
		super(apigen, parent);
		this.a = a;
	}

	@Override
	public ClassBuilder generateType() throws ScribException
	{
		final String FUTURE_PARAM = "fut";
		Module main = this.apigen.getMainModule();
		GProtoName gpn = this.apigen.getGProtocolName();

		String futureClass = getInputFutureName(this.parent.getName());  // Fresh enough? need only one future class per receive (unary receive)

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
				for (PayElemType<?> pt : a.payload.elems)
				{
					if (!pt.isDataName())
					{
						throw new ScribException("[TODO] API generation not supported for non- data type payloads: " + pt);
					}
					DataDecl dtd = main.getTypeDeclChild((DataName) pt);
					ScribSockGen.checkJavaDataTypeDecl(dtd);
					String type = dtd.getExtName();
					types.add(type);
					FieldBuilder f = future.newField("pay" + i++);
					f.setType(type);
					f.addModifiers(JavaBuilder.PUBLIC);
				}
			}
		}
		else
		{
			SigDecl msd = main.getSigDeclChild(((SigName) a.mid).getSimpleName());
			ScribSockGen.checkMessageSigNameDecl(msd);
			String type = msd.getExtName();
			types.add(type);
			FieldBuilder f = future.newField("msg");
			f.setType(type);
			f.addModifiers(JavaBuilder.PUBLIC);
		}

		ConstructorBuilder cons = future.newConstructor("CompletableFuture<" + StateChannelApiGenerator.SCRIBMESSAGE_CLASS + "> " + FUTURE_PARAM);
		cons.addModifiers(JavaBuilder.PROTECTED);
		cons.addBodyLine(JavaBuilder.SUPER + "(" + FUTURE_PARAM + ");");

		MethodBuilder sync = future.newMethod("sync");
		sync.addModifiers(JavaBuilder.PUBLIC);
		sync.setReturn(futureClass);
		//sync.addExceptions("ExecutionException", "InterruptedException");
		sync.addExceptions("IOException");
		String ln = (a.mid.isOp() && a.payload.isEmpty()) ? "" : StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " m = ";
		ln += JavaBuilder.SUPER + ".get();";
		sync.addBodyLine(ln);
		if (a.mid.isOp())
		{
			if (!a.payload.isEmpty())
			{
				int i = 1;
				for (String type : types)
				{
					sync.addBodyLine(JavaBuilder.THIS + "." + "pay" + i + " = (" + type + ") m.payload[" + (i - 1) + "];");
					i++;
				}
			}
		}
		else
		{
			sync.addBodyLine(JavaBuilder.THIS + "." + "msg" + " = (" + types.get(0) + ") m;");
		}
		sync.addBodyLine(JavaBuilder.RETURN + " " + JavaBuilder.THIS + ";");

		return future;
	}

	public static String getInputFutureName(String parent)
	{
		return parent + "_Future";  // Fresh enough? need only one future class per receive (unary receive)
	}
}
