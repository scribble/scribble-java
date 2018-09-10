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
package org.scribble.ext.go.ast.global;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.global.GDelegationElem;
import org.scribble.ast.local.LDelegationElem;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.name.RPCoreGDelegationType;
import org.scribble.ext.go.type.index.RPIndexFactory;
import org.scribble.type.kind.Local;
import org.scribble.type.name.PayloadElemType;

public class RPGDelegationElem extends GDelegationElem
{
	// FIXME: RoleNode used for variant name -- cf. toPayloadType, RPRoleVariant construction
	public RPGDelegationElem(CommonTree source, GProtocolNameNode proto, RoleNode role)
	{
		super(source, proto, role);
	}
	
	@Override
	public LDelegationElem project(AstFactory af)
	{
		//return af.RPCoreLDelegationElem(this.source, Projector.makeProjectedFullNameNode(af, this.source, this.proto.toName(), this.role.toName()));
		throw new RuntimeException("[rp-core] Shouldn't here: " + this);
	}

	@Override
	protected RPGDelegationElem copy()
	{
		return new RPGDelegationElem(this.source, this.proto, this.role);
	}
	
	@Override
	public RPGDelegationElem clone(AstFactory af)
	{
		GProtocolNameNode name = (GProtocolNameNode) this.proto.clone(af);
		RoleNode role = (RoleNode) this.role.clone(af);
		return ((RPAstFactory) af).RPGDelegationElem(this.source, name, role);
	}

	public RPGDelegationElem reconstruct(GProtocolNameNode proto, RoleNode role)
	{
		ScribDel del = del();
		RPGDelegationElem elem = new RPGDelegationElem(this.source, proto, role);
		elem = (RPGDelegationElem) elem.del(del);
		return elem;
	}

	@Override
	public PayloadElemType<Local> toPayloadType()
	{
		// FIXME all HACK
		String tmp = this.role.toString();
		int i = tmp.indexOf("_");  // Cf. RPCoreSTApiGenerator#getEndpointKindTypeName
		String name;
		int start;
		int end;
		if (i == -1)
		{
			name = tmp;
			start = 1;
			end = 1;
		}
		else
		{
			name = tmp.substring(0, i);
			int j = tmp.indexOf("_", i+1);
			start = Integer.parseInt(tmp.substring(i, j));
			end = Integer.parseInt(tmp.substring(j));
		}
		RPRoleVariant variant = new RPRoleVariant(name, 
				Stream.of(new RPInterval(RPIndexFactory.ParamIntVal(start), RPIndexFactory.ParamIntVal(end))).collect(Collectors.toSet()), 
				Collections.emptySet());
		return new RPCoreGDelegationType(this.proto.toName(), variant);
	}
}
