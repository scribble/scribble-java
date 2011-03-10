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
import org.scribble.common.model.Annotation;

public class AnnotationsComparator implements Comparator<java.util.List<Annotation>> {

	public int compare(java.util.List<Annotation> arg0, java.util.List<Annotation> arg1) {
		java.util.List<Annotation> m=(java.util.List<Annotation>)arg0;
		java.util.List<Annotation> e=(java.util.List<Annotation>)arg1;
		
		if (m == null && e == null) {
			return(0);
		}
		
		if (m == null || e == null){
			return(1);
		}
		
		if (m.size() != e.size()) {
			return(2);
		}
		
		for (int i=0; i < m.size(); i++) {
			if (m.get(i).toString().equals(e.get(i).toString()) == false) {
				return(1);
			}
		}
		
		return(0);
	}
}
