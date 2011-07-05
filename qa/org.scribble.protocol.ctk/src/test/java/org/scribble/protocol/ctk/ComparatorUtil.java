/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.ctk;

import java.util.Comparator;

import org.scribble.protocol.ctk.comparators.*;
import org.scribble.protocol.model.*;

public class ComparatorUtil {

	private static java.util.Map<Class<? extends ModelObject>,Comparator<ModelObject>> m_comparators=
		new java.util.HashMap<Class<? extends ModelObject>,Comparator<ModelObject>>();

	static {
		m_comparators.put(ProtocolModel.class, new ModelComparator());
		m_comparators.put(TypeImportList.class, new TypeImportListComparator());
		m_comparators.put(TypeImport.class, new TypeImportComparator());
		m_comparators.put(ProtocolImportList.class, new ProtocolImportListComparator());
		m_comparators.put(ProtocolImport.class, new ProtocolImportComparator());
		m_comparators.put(Protocol.class, new ProtocolComparator());
		m_comparators.put(Block.class, new BlockComparator());
		m_comparators.put(Introduces.class, new IntroducesComparator());
		m_comparators.put(Role.class, new RoleComparator());
		m_comparators.put(Interaction.class, new InteractionComparator());
		m_comparators.put(MessageSignature.class, new MessageSignatureComparator());
		m_comparators.put(TypeReference.class, new TypeReferenceComparator());
		m_comparators.put(ProtocolReference.class, new ProtocolReferenceComparator());
		m_comparators.put(Do.class, new DoComparator());
		m_comparators.put(Interrupt.class, new InterruptComparator());
		m_comparators.put(Choice.class, new ChoiceComparator());
		m_comparators.put(OnMessage.class, new OnMessageComparator());
		m_comparators.put(DirectedChoice.class, new DirectedChoiceComparator());
		m_comparators.put(Parallel.class, new ParallelComparator());
		m_comparators.put(Repeat.class, new RepeatComparator());
		m_comparators.put(RecBlock.class, new RecBlockComparator());
		m_comparators.put(Recursion.class, new RecursionComparator());
		m_comparators.put(Run.class, new RunComparator());
		m_comparators.put(Include.class, new UseComparator());
		m_comparators.put(Parameter.class, new ParameterComparator());
		m_comparators.put(DataType.class, new DataTypeComparator());
		m_comparators.put(ParameterDefinition.class, new ParameterDefinitionComparator());
		m_comparators.put(Unordered.class, new UnorderedComparator());
		m_comparators.put(End.class, new EndComparator());
	}

	public static Comparator<ModelObject> getComparator(Class<? extends ModelObject> cls) {
		return(m_comparators.get(cls));
	}
}
