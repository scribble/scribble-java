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

public class InteractionComparator implements Comparator<ModelObject> {

	@Override
	public int compare(ModelObject arg0, ModelObject arg1) {
		Interaction m=(Interaction)arg0;
		Interaction e=(Interaction)arg1;
		
		Comparator<ModelObject> mscomp=
			ComparatorUtil.getComparator(MessageSignature.class);
		
		Comparator<ModelObject> pcomp=
			ComparatorUtil.getComparator(Role.class);
		
		if (mscomp.compare(m.getMessageSignature(),
					e.getMessageSignature()) != 0) {
			return(1);
		}
		
		if (m.getFromRole() == null) {
			if (e.getFromRole() != null) {
				return(1);
			}
		} else if (pcomp.compare(m.getFromRole(),
				e.getFromRole()) != 0) {
			return(1);
		}
		
		if (m.getToRoles().size() != e.getToRoles().size()) {
			return(1);
		} else {
			for (int i=0; i < m.getToRoles().size(); i++) {
				if (pcomp.compare(m.getToRoles().get(i),
							e.getToRoles().get(i)) != 0) {
					return(1);
				}
			}
		}
		
		Comparator<java.util.List<org.scribble.common.model.Annotation>> ancomp=new AnnotationsComparator();	
		
		if (ancomp.compare(m.getAnnotations(), e.getAnnotations()) != 0) {
			return(1);
		}
		
		return(0);
	}
}
