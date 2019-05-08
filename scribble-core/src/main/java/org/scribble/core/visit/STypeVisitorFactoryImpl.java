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
package org.scribble.core.visit;

import java.util.List;

import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.Seq;
import org.scribble.core.visit.global.GTypeVisitorFactory;
import org.scribble.core.visit.local.LTypeVisitorFactory;

// No interface due to fields -- CHECKME: factor out "neutral" factory field for RecPruner etc.
public class STypeVisitorFactoryImpl extends STypeVisitorFactory
{
	public STypeVisitorFactoryImpl(GTypeVisitorFactory global,
			LTypeVisitorFactory local)
	{
		super(global, local);
	}

	@Override
	public <K extends ProtoKind, B extends Seq<K, B>> Substitutor<K, B> 
			Substitutor(List<Role> rold, List<Role> rnew,
					List<MemberName<? extends NonRoleParamKind>> aold,
					List<Arg<? extends NonRoleParamKind>> anew, boolean passive)
	{
		return new Substitutor<>(rold, rnew, aold, anew, passive);
	}

	@Override
	public <K extends ProtoKind, B extends Seq<K, B>> RecPruner<K, B> 
			RecPruner()
	{
		return new RecPruner<>();
	}
}
