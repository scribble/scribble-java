/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.ctk.comparators;

import java.util.Comparator;

import org.scribble.protocol.ctk.ComparatorUtil;
import org.scribble.protocol.model.*;

public class ProtocolReferenceComparator implements Comparator<ModelObject> {

	@Override
	public int compare(ModelObject arg0, ModelObject arg1) {
		ProtocolReference m=(ProtocolReference)arg0;
		ProtocolReference e=(ProtocolReference)arg1;
		
		if (m.getName() != null &&
				e.getName() != null &&
				m.getName().equals(e.getName())) {

			if (m.getRole() == null &&
					e.getRole() == null) {
				return(0);
			} else if (m.getRole() != null &&
					e.getRole() != null) {
				RoleComparator pcomp=(RoleComparator)
					ComparatorUtil.getComparator(Role.class);
			
				return(pcomp.compare(m.getRole(), e.getRole()));
			}
		}
		
		return(1);
	}
}
