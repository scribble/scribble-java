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
package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class AntlrMessageSig
{
	public static final int OPERATOR_CHILD_INDEX = 0;
	public static final int PAYLOAD_CHILD_INDEX = 1;
	
	public static MessageSigNode parseMessageSig(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		OpNode op = AntlrSimpleName.toOpNode(getOpChild(ct));
		PayloadElemList payload = (PayloadElemList) parser.parse(getPayloadElemListChild(ct));
		return AstFactoryImpl.FACTORY.MessageSigNode(ct, op, payload);
	}

	public static CommonTree getOpChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(OPERATOR_CHILD_INDEX);
	}
	
	public static CommonTree getPayloadElemListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(PAYLOAD_CHILD_INDEX);
	}
}
