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
package org.scribble.trace.parser.antlr;

import org.antlr.runtime.CommonToken;
import org.scribble.model.ModelObject;

/**
 * This class provides the abstract based implementation for a model adapter.
 *
 */
public abstract class AbstractModelAdaptor implements ModelAdaptor {

	/**
	 * This method sets the start properties on the supplied model object.
	 * 
	 * @param mobj The model object
	 * @param source The source object
	 */
	protected void setStartProperties(ModelObject mobj, Object source) {
		if (source instanceof CommonToken) {
			mobj.getProperties().put(ModelObject.START_LINE, ((CommonToken)source).getLine());
			mobj.getProperties().put(ModelObject.START_COLUMN, ((CommonToken)source).getCharPositionInLine());
		} else if (source instanceof ModelObject) {
			mobj.getProperties().put(ModelObject.START_LINE, ((ModelObject)source).getProperties().get(ModelObject.START_LINE));
			mobj.getProperties().put(ModelObject.START_COLUMN, ((ModelObject)source).getProperties().get(ModelObject.START_COLUMN));
		}
	}
	
	/**
	 * This method sets the end properties on the supplied model object.
	 * 
	 * @param mobj The model object
	 * @param source The source object
	 */
	protected void setEndProperties(ModelObject mobj, Object source) {		
		if (source instanceof CommonToken) {
			mobj.getProperties().put(ModelObject.END_LINE, ((CommonToken)source).getLine());
			mobj.getProperties().put(ModelObject.END_COLUMN, ((CommonToken)source).getCharPositionInLine()
                    +((CommonToken)source).getText().length());
		} else if (source instanceof ModelObject) {
			mobj.getProperties().put(ModelObject.END_LINE, ((ModelObject)source).getProperties().get(ModelObject.END_LINE));
			mobj.getProperties().put(ModelObject.END_COLUMN, ((ModelObject)source).getProperties().get(ModelObject.END_COLUMN));
		}
	}
}
