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
package org.scribble.parser.scribble;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.parser.scribble.ScribbleAntlrConstants.AntlrNodeType;

public class AntlrToScribParserUtil
{
	public static List<CommonTree> toCommonTreeList(List<?> list)
	{
		return list.stream().map((x) -> (CommonTree) x).collect(Collectors.toList());
	}
	
	// Cf. Scribble.g output "node types" -- mapping to the AntlrNodeType enum
	public static AntlrNodeType getAntlrNodeType(CommonTree ct)
	{
		String type = ct.getToken().getText();
		switch (type)
		{
			////case AntlrConstants.EMPTY_PARAMETERDECLLST_NODE_TYPE: return AntlrNodeType.EMPTY_PARAMETERDECLLST;
			case ScribbleAntlrConstants.AMBIGUOUSNAME_NODE_TYPE:              return AntlrNodeType.AMBIGUOUSNAME;
			case ScribbleAntlrConstants.QUALIFIEDNAME_NODE_TYPE:              return AntlrNodeType.QUALIFIEDNAME;

			case ScribbleAntlrConstants.MESSAGESIGNATURE_NODE_TYPE:           return AntlrNodeType.MESSAGESIGNATURE;
			case ScribbleAntlrConstants.DELEGATION_NODE_TYPE:                 return AntlrNodeType.DELEGATION;
			
			case ScribbleAntlrConstants.PAYLOAD_NODE_TYPE:                    return AntlrNodeType.PAYLOAD;
			//case AntlrConstants.PAYLOADELEMENT_NODE_TYPE: return AntlrNodeType.PAYLOADELEMENT;

			case ScribbleAntlrConstants.MODULE_NODE_TYPE:                     return AntlrNodeType.MODULE;
			case ScribbleAntlrConstants.MODULEDECL_NODE_TYPE:                 return AntlrNodeType.MODULEDECL;
			case ScribbleAntlrConstants.IMPORTMODULE_NODE_TYPE:               return AntlrNodeType.IMPORTMODULE;
			case ScribbleAntlrConstants.IMPORTMEMBER_NODE_TYPE:               return AntlrNodeType.IMPORTMEMBER;
			case ScribbleAntlrConstants.PAYLOADTYPEDECL_NODE_TYPE:            return AntlrNodeType.PAYLOADTYPEDECL;
			case ScribbleAntlrConstants.MESSAGESIGNATUREDECL_NODE_TYPE:       return AntlrNodeType.MESSAGESIGNATUREDECL;
			case ScribbleAntlrConstants.ROLEDECLLIST_NODE_TYPE:               return AntlrNodeType.ROLEDECLLIST;
			case ScribbleAntlrConstants.ROLEDECL_NODE_TYPE:                   return AntlrNodeType.ROLEDECL;
			case ScribbleAntlrConstants.PARAMETERDECLLIST_NODE_TYPE:          return AntlrNodeType.PARAMETERDECLLIST; 
			case ScribbleAntlrConstants.PARAMETERDECL_NODE_TYPE:              return AntlrNodeType.PARAMETERDECL;
			case ScribbleAntlrConstants.ROLEINSTANTIATIONLIST_NODE_TYPE:      return AntlrNodeType.ROLEINSTANTIATIONLIST;
			case ScribbleAntlrConstants.ROLEINSTANTIATION_NODE_TYPE:          return AntlrNodeType.ROLEINSTANTIATION;
			case ScribbleAntlrConstants.ARGUMENTINSTANTIATIONLIST_NODE_TYPE:  return AntlrNodeType.ARGUMENTINSTANTIATIONLIST;
			//case ScribbleAntlrConstants.ARGUMENTINSTANTIATION_NODE_TYPE:      return AntlrNodeType.ARGUMENTINSTANTIATION;  // Not used directly?

			case ScribbleAntlrConstants.GLOBALPROTOCOLDECL_NODE_TYPE:         return AntlrNodeType.GLOBALPROTOCOLDECL;
			case ScribbleAntlrConstants.GLOBALPROTOCOLHEADER_NODE_TYPE:       return AntlrNodeType.GLOBALPROTOCOLHEADER;
			case ScribbleAntlrConstants.GLOBALPROTOCOLDEF_NODE_TYPE:          return AntlrNodeType.GLOBALPROTOCOLDEF;
			case ScribbleAntlrConstants.GLOBALPROTOCOLBLOCK_NODE_TYPE:        return AntlrNodeType.GLOBALPROTOCOLBLOCK;
			case ScribbleAntlrConstants.GLOBALINTERACTIONSEQUENCE_NODE_TYPE:  return AntlrNodeType.GLOBALINTERACTIONSEQUENCE;
			case ScribbleAntlrConstants.GLOBALMESSAGETRANSFER_NODE_TYPE:      return AntlrNodeType.GLOBALMESSAGETRANSFER;
			case ScribbleAntlrConstants.GLOBALCONNECT_NODE_TYPE:              return AntlrNodeType.GLOBALCONNECT;
			case ScribbleAntlrConstants.GLOBALWRAP_NODE_TYPE:                 return AntlrNodeType.GLOBALWRAP;
			case ScribbleAntlrConstants.GLOBALDISCONNECT_NODE_TYPE:           return AntlrNodeType.GLOBALDISCONNECT;
			case ScribbleAntlrConstants.GLOBALCHOICE_NODE_TYPE:               return AntlrNodeType.GLOBALCHOICE;
			case ScribbleAntlrConstants.GLOBALRECURSION_NODE_TYPE:            return AntlrNodeType.GLOBALRECURSION;
			case ScribbleAntlrConstants.GLOBALCONTINUE_NODE_TYPE:             return AntlrNodeType.GLOBALCONTINUE;
			/*case ScribbleAntlrConstants.GLOBALPARALLEL_NODE_TYPE:             return AntlrNodeType.GLOBALPARALLEL;
			case ScribbleAntlrConstants.GLOBALINTERRUPTIBLE_NODE_TYPE:        return AntlrNodeType.GLOBALINTERRUPTIBLE;
			case ScribbleAntlrConstants.GLOBALINTERRUPT_NODE_TYPE:            return AntlrNodeType.GLOBALINTERRUPT;*/
			case ScribbleAntlrConstants.GLOBALDO_NODE_TYPE:                   return AntlrNodeType.GLOBALDO;

			/*case ScribbleAntlrConstants.LOCALPROTOCOLDECL_NODE_TYPE:          return AntlrNodeType.LOCALPROTOCOLDECL; 
			case ScribbleAntlrConstants.LOCALROLEDECLLIST_NODE_TYPE:          return AntlrNodeType.ROLEDECLLIST;
			case ScribbleAntlrConstants.LOCALROLEDECL_NODE_TYPE:              return AntlrNodeType.ROLEDECL;
			case ScribbleAntlrConstants.SELFDECL_NODE_TYPE:                   return AntlrNodeType.SELFDECL;
			case ScribbleAntlrConstants.LOCALPROTOCOLDEF_NODE_TYPE:           return AntlrNodeType.LOCALPROTOCOLDEF;
			case ScribbleAntlrConstants.LOCALPROTOCOLBLOCK_NODE_TYPE:         return AntlrNodeType.LOCALPROTOCOLBLOCK;
			case ScribbleAntlrConstants.LOCALINTERACTIONSEQUENCE_NODE_TYPE:   return AntlrNodeType.LOCALINTERACTIONSEQUENCE;
			case ScribbleAntlrConstants.LOCALSEND_NODE_TYPE:                  return AntlrNodeType.LOCALSEND;
			case ScribbleAntlrConstants.LOCALRECEIVE_NODE_TYPE:               return AntlrNodeType.LOCALRECEIVE;
			case ScribbleAntlrConstants.LOCALCHOICE_NODE_TYPE:                return AntlrNodeType.LOCALCHOICE;
			case ScribbleAntlrConstants.LOCALRECURSION_NODE_TYPE:             return AntlrNodeType.LOCALRECURSION;
			case ScribbleAntlrConstants.LOCALCONTINUE_NODE_TYPE:              return AntlrNodeType.LOCALCONTINUE;
			case ScribbleAntlrConstants.LOCALPARALLEL_NODE_TYPE:              return AntlrNodeType.LOCALPARALLEL;
			case ScribbleAntlrConstants.LOCALINTERRUPTIBLE_NODE_TYPE:         return AntlrNodeType.LOCALINTERRUPTIBLE;
			case ScribbleAntlrConstants.LOCALTHROWS_NODE_TYPE:                return AntlrNodeType.LOCALTHROWS;
			case ScribbleAntlrConstants.LOCALCATCHES_NODE_TYPE:               return AntlrNodeType.LOCALCATCHES;
			case ScribbleAntlrConstants.LOCALDO_NODE_TYPE:                    return AntlrNodeType.LOCALDO;*/

			// Nodes without a "node type", e.g. parameter names, fall in here
			default: throw new RuntimeException("Unknown ANTLR node type label: " + type);
		}
	}
}
