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
package org.scribble.ast;

import org.scribble.sesstype.Message;

// A sig kind node: MessageSignatureNode, MessageSignatureNameNode or NonRoleParamNode
public interface MessageNode extends NonRoleArgNode
{
	Message toMessage();
	
	MessageNode clone(AstFactory af);

	//Arg<? extends SigKind> toArg();  // Not possible due to "diamond" with PayloadElemNameNode at AmbigNameNode
	
	MessageNode project(AstFactory af);  // Not role sensitive  // Factor into visitor/env pattern?  Currently not
}
