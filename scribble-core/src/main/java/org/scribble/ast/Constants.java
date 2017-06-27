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


// FIXME: cannot use AntlrConstants from org.scribble.parser due to Maven dependency restrictions
public class Constants
{
	public static final String SCRIBBLE_FILE_EXTENSION = "scr";
	
	// Duplicated from AntlrConstants due to Maven dependency restrictions
	public static final String MODULE_KW = "module";
	public static final String IMPORT_KW = "import";
	public static final String TYPE_KW = "type";
	public static final String PROTOCOL_KW = "protocol";
	public static final String GLOBAL_KW = "global";
	public static final String LOCAL_KW = "local";
	public static final String ROLE_KW = "role";
	public static final String ACCEPT_KW = "accept";
	public static final String SELF_KW = "self";
	public static final String SIG_KW = "sig";
	public static final String INSTANTIATES_KW = "instantiates";

	public static final String CONNECT_KW = "connect";
	public static final String DISCONNECT_KW = "disconnect";
	public static final String WRAP_KW = "wrap";
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
