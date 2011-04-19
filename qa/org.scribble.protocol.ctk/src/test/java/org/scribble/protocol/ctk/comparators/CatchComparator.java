/*
 * Copyright 2009-10 www.scribble.org
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

public class CatchComparator implements Comparator<ModelObject> {

	public int compare(ModelObject arg0, ModelObject arg1) {
		Catch m=(Catch)arg0;
		Catch e=(Catch)arg1;
						
		if (m.getInteractions().size() != e.getInteractions().size()) {
			return(1);
		}
		
		java.util.List<Interaction> interactions=new java.util.Vector<Interaction>(e.getInteractions());
		Comparator<ModelObject> intComparator=ComparatorUtil.getComparator(Interaction.class);
		
		for (Interaction interaction : m.getInteractions()) {
			boolean f_found=false;
			
			for (int i=0; f_found == false && i < interactions.size(); i++) {
				
				if (f_found = (intComparator.compare(interaction, interactions.get(i)) == 0)) {
					// Remove matched interaction from the list
					interactions.remove(i);
				}
			}
			
			if (f_found == false) {
				return(1);
			}
		}
		
		return(0);
	}
}
