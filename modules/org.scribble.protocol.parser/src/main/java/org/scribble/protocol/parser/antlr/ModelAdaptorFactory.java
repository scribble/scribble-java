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
		_modelAdaptors.put("argumentlist", new ArgumentListModelAdaptor());
		_modelAdaptors.put("create", new CreateModelAdaptor());
		_modelAdaptors.put("enter", new EnterModelAdaptor());
		_modelAdaptors.put("globaldo", new GlobalDoModelAdaptor());
		_modelAdaptors.put("globalchoice", new GlobalChoiceModelAdaptor());
		_modelAdaptors.put("globalcontinue", new GlobalContinueModelAdaptor());
		_modelAdaptors.put("globalmessagetransfer", new GlobalMessageTransferModelAdaptor());
		_modelAdaptors.put("globalparallel", new GlobalParallelModelAdaptor());
		_modelAdaptors.put("globalprotocolblock", new GlobalProtocolBlockModelAdaptor());
		_modelAdaptors.put("globalprotocoldecl", new GlobalProtocolDeclModelAdaptor());
		_modelAdaptors.put("globalrecursion", new GlobalRecursionModelAdaptor());
		_modelAdaptors.put("importdecl", new ImportDeclModelAdaptor());
		_modelAdaptors.put("localchoice", new LocalChoiceModelAdaptor());
		_modelAdaptors.put("localcontinue", new LocalContinueModelAdaptor());
		_modelAdaptors.put("localinteractionblock", new LocalInteractionBlockModelAdaptor());
		_modelAdaptors.put("localparallel", new LocalParallelModelAdaptor());
		_modelAdaptors.put("localprotocoldecl", new LocalProtocolDeclModelAdaptor());
		_modelAdaptors.put("localrecursion", new LocalRecursionModelAdaptor());
		_modelAdaptors.put("messagesignature", new MessageSignatureModelAdaptor());
		_modelAdaptors.put("parameterlist", new ParameterListModelAdaptor());
		_modelAdaptors.put("payloadelement", new PayloadModelAdaptor());
		_modelAdaptors.put("payloadtypedecl", new PayloadTypeDeclModelAdaptor());
		_modelAdaptors.put("module", new ModuleModelAdaptor());
		_modelAdaptors.put("moduledecl", new ModuleDeclModelAdaptor());
		_modelAdaptors.put("receive", new ReceiveModelAdaptor());
		_modelAdaptors.put("roledecllist", new RoleDeclListModelAdaptor());
		_modelAdaptors.put("roleinstantiationlist", new RoleInstantiationListModelAdaptor());
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
