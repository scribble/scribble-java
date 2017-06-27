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

// FIXME: refer to values from core ast.Constants
public class AntlrConstants
{
	public static final String SCRIBBLE_FILE_EXTENSION = "scr";

	public static final String KIND_MESSAGESIGNATURE = "KIND_MESSAGESIGNATURE";
	public static final String KIND_PAYLOADTYPE = "KIND_PAYLOADTYPE";

	public static final String MESSAGESIGNATURE_NODE_TYPE = "MESSAGESIGNATURE";
	public static final String PAYLOAD_NODE_TYPE = "PAYLOAD";
	public static final String ANNOTPAYLOAD_NODE_TYPE = "ANNOTPAYLOAD";
	//public static final String PAYLOADELEMENT_NODE_TYPE = "PAYLOADELEMENT";
	public static final String DELEGATION_NODE_TYPE = "DELEGATION";

	//public static final String EMPTY_PARAMETERDECLLST_NODE_TYPE = "EMPTY_PARAMETERDECLLIST";
	public static final String AMBIGUOUSNAME_NODE_TYPE = "AMBIGUOUSNAME";
	public static final String QUALIFIEDNAME_NODE_TYPE = "QUALIFIEDNAME";
	
	public static final String MODULE_NODE_TYPE = "MODULE";
	public static final String MODULEDECL_NODE_TYPE = "MODULEDECL";
	public static final String IMPORTMODULE_NODE_TYPE = "IMPORTMODULE";
	public static final String IMPORTMEMBER_NODE_TYPE = "IMPORTMEMBER";
	public static final String PAYLOADTYPEDECL_NODE_TYPE = "PAYLOADTYPEDECL";
	public static final String MESSAGESIGNATUREDECL_NODE_TYPE = "MESSAGESIGNATUREDECL";
	public static final String ROLEDECLLIST_NODE_TYPE = "ROLEDECLLIST";
	public static final String ROLEDECL_NODE_TYPE = "ROLEDECL";
	public static final String PARAMETERDECLLIST_NODE_TYPE = "PARAMETERDECLLIST";
	public static final String PARAMETERDECL_NODE_TYPE = "PARAMETERDECL";
	public static final String ROLEINSTANTIATIONLIST_NODE_TYPE = "ROLEINSTANTIATIONLIST";
	public static final String ROLEINSTANTIATION_NODE_TYPE = "ROLEINSTANTIATION";
	public static final String ARGUMENTINSTANTIATIONLIST_NODE_TYPE = "ARGUMENTINSTANTIATIONLIST";
	public static final String ARGUMENTINSTANTIATION_NODE_TYPE = "ARGUMENTINSTANTIATION";

	public static final String GLOBALPROTOCOLDECL_NODE_TYPE = "GLOBALPROTOCOLDECL";
	public static final String GLOBALPROTOCOLHEADER_NODE_TYPE = "GLOBALPROTOCOLHEADER";
	public static final String GLOBALPROTOCOLDEF_NODE_TYPE = "GLOBALPROTOCOLDEF";
	public static final String GLOBALPROTOCOLBLOCK_NODE_TYPE = "GLOBALPROTOCOLBLOCK";
	public static final String GLOBALINTERACTIONSEQUENCE_NODE_TYPE = "GLOBALINTERACTIONSEQUENCE";
	public static final String GLOBALMESSAGETRANSFER_NODE_TYPE = "GLOBALMESSAGETRANSFER";
	public static final String ANNOTGLOBALMESSAGETRANSFER_NODE_TYPE = "ANNOTGLOBALMESSAGETRANSFER";
	public static final String GLOBALCONNECT_NODE_TYPE = "GLOBALCONNECT";
	public static final String GLOBALDISCONNECT_NODE_TYPE = "GLOBALDISCONNECT";
	public static final String GLOBALWRAP_NODE_TYPE = "GLOBALWRAP";
	public static final String GLOBALCHOICE_NODE_TYPE = "GLOBALCHOICE";
	public static final String GLOBALRECURSION_NODE_TYPE = "GLOBALRECURSION";
	public static final String GLOBALCONTINUE_NODE_TYPE = "GLOBALCONTINUE";
	public static final String GLOBALPARALLEL_NODE_TYPE = "GLOBALPARALLEL";
	public static final String GLOBALINTERRUPTIBLE_NODE_TYPE = "GLOBALINTERRUPTIBLE";
	public static final String GLOBALINTERRUPT_NODE_TYPE = "GLOBALINTERRUPT";
	public static final String GLOBALDO_NODE_TYPE = "GLOBALDO";

	// # FIXME: local nodes are not ANTLR nodes
	public static final String LOCALPROTOCOLDECL_NODE_TYPE = "LOCALPROTOCOLDECL";
	public static final String LOCALROLEDECLLIST_NODE_TYPE = "LOCALROLEDECLLIST";
	public static final String LOCALROLEDECL_NODE_TYPE = "LOCALROLEDECL";
	public static final String SELFDECL_NODE_TYPE = "SELFDECL";
	public static final String LOCALPROTOCOLDEF_NODE_TYPE = "LOCALPROTOCOLDEF";
	public static final String LOCALPROTOCOLBLOCK_NODE_TYPE = "LOCALPROTOCOLBLOCK";
	public static final String LOCALINTERACTIONSEQUENCE_NODE_TYPE = "LOCALINTERACTIONSEQUENCE";
	public static final String LOCALSEND_NODE_TYPE = "LOCALSEND";
	public static final String LOCALRECEIVE_NODE_TYPE = "LOCALRECEIVE";
	public static final String LOCALCHOICE_NODE_TYPE = "LOCALCHOICE";
	public static final String LOCALRECURSION_NODE_TYPE = "LOCALRECURSION";
	public static final String LOCALCONTINUE_NODE_TYPE = "LOCALCONTINUE";
	public static final String LOCALPARALLEL_NODE_TYPE = "LOCALPARALLEL";
	public static final String LOCALINTERRUPTIBLE_NODE_TYPE = "LOCALINTERRUPTIBLE";
	public static final String LOCALTHROWS_NODE_TYPE = "LOCALTHROWS";
	public static final String LOCALCATCHES_NODE_TYPE = "LOCALCATCHES";
	public static final String LOCALDO_NODE_TYPE = "LOCALDO";

	// Cf. Scribble.g
	public enum AntlrNodeType 
	{
		//EMPTY_PARAMETERDECLLST,
		AMBIGUOUSNAME,
		QUALIFIEDNAME,

		MODULE,
		MODULEDECL,
		IMPORTMODULE,
		IMPORTMEMBER,
		PAYLOADTYPEDECL,
		MESSAGESIGNATUREDECL,
		ROLEDECLLIST,
		ROLEDECL,
		//CONNECTDECL,
		PARAMETERDECLLIST,
		PARAMETERDECL,
		ROLEINSTANTIATIONLIST,
		ROLEINSTANTIATION,
		ARGUMENTINSTANTIATIONLIST,
		ARGUMENTINSTANTIATION,
		MESSAGESIGNATURE,
		PAYLOAD,
		ANNOTPAYLOAD, 
		PAYLOADELEMENT,
		DELEGATION,

		GLOBALPROTOCOLDECL,
		GLOBALPROTOCOLHEADER,
		GLOBALPROTOCOLDEF,
		GLOBALPROTOCOLBLOCK,
		GLOBALINTERACTIONSEQUENCE,
		GLOBALMESSAGETRANSFER,
		ANNOTGLOBALMESSAGETRANSFER,
		GLOBALCONNECT,
		GLOBALDISCONNECT,
		GLOBALWRAP,
		GLOBALCHOICE,
		GLOBALRECURSION,
		GLOBALCONTINUE,
		GLOBALPARALLEL,
		GLOBALINTERRUPTIBLE,
		GLOBALINTERRUPT,
		GLOBALDO,

		LOCALPROTOCOLDECL,
		LOCALROLEDECLLIST,
		LOCALROLEDECL,
		SELFDECL,
		LOCALPROTOCOLDEF,
		LOCALPROTOCOLBLOCK,
		LOCALINTERACTIONSEQUENCE,
		LOCALSEND,
		LOCALRECEIVE,
		LOCALCHOICE,
		LOCALRECURSION,
		LOCALCONTINUE,
		LOCALPARALLEL,
		LOCALINTERRUPTIBLE,
		LOCALTHROWS,
		LOCALCATCHES,
		LOCALDO
	}

	// Duplicated from Scribble2.g -- what's the best way to share it?
	// Mostly used in projection (outputting local types)
	public static final String MODULE_KW = "module";
	public static final String IMPORT_KW = "import";
	public static final String TYPE_KW = "type";
	public static final String PROTOCOL_KW = "protocol";
	public static final String GLOBAL_KW = "global";
	public static final String LOCAL_KW = "local";
	public static final String ROLE_KW = "role";
	public static final String SELF_KW = "self";
	public static final String SIG_KW = "sig";
	public static final String INSTANTIATES_KW = "instantiates";

	public static final String CONNECT_KW = "connect";
	public static final String DISCONNECT_KW = "disconnect";
	public static final String FROM_KW = "from";
	public static final String TO_KW = "to";
	public static final String CHOICE_KW = "choice";
	public static final String AT_KW = "at";
	public static final String OR_KW = "or";
	public static final String REC_KW = "rec";
	public static final String CONTINUE_KW = "continue";
	public static final String PAR_KW = "par";
	public static final String AND_KW = "and";
	public static final String INTERRUPTIBLE_KW = "interruptible";
	public static final String WITH_KW = "with";
	public static final String BY_KW = "by";
	public static final String DO_KW = "do";
	public static final String AS_KW = "as";
	public static final String SPAWN_KW = "spawn";
	public static final String THROWS_KW = "throws";
	public static final String CATCHES_KW = "catches";
}
