/*
 * Copyright 2009-11 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.protocol.parser.antlr;

/**
 * This class provides the model adaptor factory.
 *
 */
public class ModelAdaptorFactory {
	
	private static java.util.Map<String,ModelAdaptor> _modelAdaptors=new java.util.HashMap<String,ModelAdaptor>();
	
	static {
		_modelAdaptors.put("argumentList", new ArgumentListModelAdaptor());
		_modelAdaptors.put("choice", new ChoiceModelAdaptor());
		_modelAdaptors.put("continueDef", new ContinueModelAdaptor());
		_modelAdaptors.put("create", new CreateModelAdaptor());
		_modelAdaptors.put("doDef", new DoDefModelAdaptor());
		_modelAdaptors.put("enter", new EnterModelAdaptor());
		_modelAdaptors.put("globalInteractionBlock", new GlobalInteractionBlockModelAdaptor());
		_modelAdaptors.put("globalProtocolDecl", new GlobalProtocolDeclModelAdaptor());
		_modelAdaptors.put("importDecl", new ImportDeclModelAdaptor());
		_modelAdaptors.put("localChoice", new LocalChoiceModelAdaptor());
		_modelAdaptors.put("localContinue", new LocalContinueModelAdaptor());
		_modelAdaptors.put("localInteractionBlock", new LocalInteractionBlockModelAdaptor());
		_modelAdaptors.put("localParallel", new LocalParallelModelAdaptor());
		_modelAdaptors.put("localProtocolDecl", new LocalProtocolDeclModelAdaptor());
		_modelAdaptors.put("localRecursion", new LocalRecursionModelAdaptor());
		_modelAdaptors.put("message", new MessageModelAdaptor());
		_modelAdaptors.put("messageSignature", new MessageSignatureModelAdaptor());
		_modelAdaptors.put("packageDecl", new PackageDeclModelAdaptor());
		_modelAdaptors.put("parallel", new ParallelModelAdaptor());
		_modelAdaptors.put("parameterList", new ParameterListModelAdaptor());
		_modelAdaptors.put("payloadType", new PayloadTypeModelAdaptor());
		_modelAdaptors.put("payloadTypeDecl", new PayloadTypeDeclModelAdaptor());
		_modelAdaptors.put("module", new ModuleModelAdaptor());
		_modelAdaptors.put("receive", new ReceiveModelAdaptor());
		_modelAdaptors.put("recursion", new RecursionModelAdaptor());
		_modelAdaptors.put("roleDef", new RoleDefModelAdaptor());
		_modelAdaptors.put("roleInstantiationList", new RoleInstantiationListModelAdaptor());
		_modelAdaptors.put("roleName", new RoleNameModelAdaptor());
		_modelAdaptors.put("send", new SendModelAdaptor());
		_modelAdaptors.put("spawn", new SpawnModelAdaptor());
	}

	/**
	 * This method returns the model adaptor implementation associated with
	 * the supplied rule name.
	 * 
	 * @param ruleName The rule name
	 * @return The model adaptor, or null if not relevant
	 */
	public static ModelAdaptor getModelAdaptor(String ruleName) {
		return (_modelAdaptors.get(ruleName));
	}
}
