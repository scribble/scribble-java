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
package org.scribble.cli;

public enum CLArgFlag
{
	// Unique flags
	JUNIT,  // For internal use (JUnit test harness)
	MAIN_MOD,
	IMPORT_PATH,
	VERBOSE,
	SCHAN_API_SUBTYPES,
	OLD_WF,
	NO_LIVENESS,
	LTSCONVERT_MIN,  // Currently only affects EFSM output (i.e. -fsm..) and API gen -- doesn't affect validation
	FAIR,
	NO_LOCAL_CHOICE_SUBJECT_CHECK,
	NO_ACCEPT_CORRELATION_CHECK,
	DOT,
	AUT,
	NO_VALIDATION,
	INLINE_MAIN_MOD,
	F17,

	// Non-unique flags
	PROJECT,
	API_OUTPUT,
	EFSM,
	VALIDATION_EFSM,
	UNFAIR_EFSM,
	UNFAIR_EFSM_PNG,
	EFSM_PNG,
	VALIDATION_EFSM_PNG,
	SGRAPH,
	UNFAIR_SGRAPH,
	SGRAPH_PNG,
	UNFAIR_SGRAPH_PNG,
	API_GEN,
	SESS_API_GEN,
	SCHAN_API_GEN,
}
