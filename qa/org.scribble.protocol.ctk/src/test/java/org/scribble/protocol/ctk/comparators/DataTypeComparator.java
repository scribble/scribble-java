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
import org.scribble.protocol.model.*;

public class DataTypeComparator implements Comparator<ModelObject> {

	public int compare(ModelObject arg0, ModelObject arg1) {
		DataType m=(DataType)arg0;
		DataType e=(DataType)arg1;
		
		if (m == null && e == null) {
			return(0);
		}
		
		if (m == null || e == null){
			return(1);
		}
		
		if (m.getDetails() != null && e.getDetails() != null) {
			if (m.getDetails().equals(e.getDetails()) == false) {
				return(1);
			}
		} else if (m.getDetails() != null || e.getDetails() != null) {
			return(1);
		}
		
		return(0);
	}
}
