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
package org.scribble.protocol.util;

import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.RoleList;

public class ActivityUtil {

	/**
	 * This method determines whether the activity is
	 * a declaration.
	 * 
	 * @param act The activity
	 * @return Whether the activity is a declaration
	 */
	public static boolean isDeclaration(Activity act) {
		boolean ret=false;
		
		if (act instanceof RoleList) {
			ret = true;
		}
		
		return(ret);
	}
	
	/**
	 * This method determines whether the activity is
	 * a behavioural element.
	 * 
	 * @param act The activity
	 * @return Whether the activity is a behavioural element
	 */
	public static boolean isBehaviour(Activity act) {
		return(!isDeclaration(act));
	}
}
