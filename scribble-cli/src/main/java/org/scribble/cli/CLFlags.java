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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


// Flag Strings must start with "-", e.g., "-project"
// A Scribble extension should override getUniqueFlags and getNonUniqueFlags
// E.g., ... new HashMap<>(super.getUniqueFlags()).putAll(myExtraFlags) ...
public class CLFlags
{
	//public final Map<Integer, String> uflags = getUniqueFlags();
	//public final Map<Integer, String> nflags = getNonUniqueFlags();
	
	
	/**
	 *  Implicit (and unique)
	 */
	public static final String MAIN_MOD_FLAG = "__main";


	/**
	 *  Unique flags
	 */
	public static final String JUNIT_FLAG = "-junit";  // For internal use (JUnit test harness)
	public static final String IMPORT_PATH_FLAG = "-ip";
	public static final String API_OUTPUT_DIR_FLAG = "-d";
	public static final String VERBOSE_FLAG = "-V";
	public static final String STATECHAN_SUBTYPES_FLAG = "-subtypes";
	public static final String OLD_WF_FLAG = "-oldwf";
	public static final String NO_LIVENESS_FLAG = "-nolive";
	public static final String LTSCONVERT_MIN_FLAG = "-minlts";  // Currently only affects EFSM output (i.e. -fsm..) and API gen -- doesn't affect validation
	public static final String FAIR_FLAG = "-fair";
	public static final String NO_LOCAL_CHOICE_SUBJECT_CHECK_FLAG = "-nolocalchoicecheck";
	public static final String NO_ACCEPT_CORRELATION_CHECK_FLAG = "-nocorrelation";
	public static final String DOT_FLAG = "-dot";
	public static final String AUT_FLAG = "-aut";
	public static final String NO_VALIDATION_FLAG = "-novalid";
	public static final String INLINE_MAIN_MOD_FLAG = "-inline";
	public static final String SPIN_FLAG = "-spin";


	/**
	 *  Non-unique flags
	 */
	public static final String PROJECT_FLAG = "-project";
	public static final String EFSM_FLAG = "-fsm";
	public static final String VALIDATION_EFSM_FLAG = "-vfsm";
	public static final String UNFAIR_EFSM_FLAG = "-ufsm";
	public static final String UNFAIR_EFSM_PNG_FLAG = "-ufsmpng";
	public static final String EFSM_PNG_FLAG = "-fsmpng";
	public static final String VALIDATION_EFSM_PNG_FLAG = "-vfsmpng";
	public static final String SGRAPH_FLAG = "-model";
	public static final String UNFAIR_SGRAPH_FLAG = "-umodel";
	public static final String SGRAPH_PNG_FLAG = "-modelpng";
	public static final String UNFAIR_SGRAPH_PNG_FLAG = "-umodelpng";
	public static final String API_GEN_FLAG = "-api";
	public static final String SESSION_API_GEN_FLAG = "-sessapi";
	public static final String STATECHAN_API_GEN_FLAG = "-chanapi";
	public static final String EVENTDRIVEN_API_GEN_FLAG = "-cbapi";
	//*/
	
	public final Map<String, CLAux> flags;

	public CLFlags()
	{
		Map<String, CLAux> flags = new HashMap<>();
		
		// Unique; barrier irrelevant
		flags.put(IMPORT_PATH_FLAG, 
				new CLAux(IMPORT_PATH_FLAG, 1, true, false, "Missing path argument: "));
		flags.put(API_OUTPUT_DIR_FLAG, 
				new CLAux(API_OUTPUT_DIR_FLAG, 1, true, false,
						"Missing directory argument: "));
		flags.put(INLINE_MAIN_MOD_FLAG, 
				new CLAux(INLINE_MAIN_MOD_FLAG, 1, true, false, "Missing inline module: "));

		flags.put(JUNIT_FLAG, 
				new CLAux(JUNIT_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(VERBOSE_FLAG, 
				new CLAux(VERBOSE_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(STATECHAN_SUBTYPES_FLAG, 
				new CLAux(STATECHAN_SUBTYPES_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(OLD_WF_FLAG, 
				new CLAux(OLD_WF_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(NO_LIVENESS_FLAG, 
				new CLAux(NO_LIVENESS_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(LTSCONVERT_MIN_FLAG, 
				new CLAux(LTSCONVERT_MIN_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(FAIR_FLAG, 
				new CLAux(FAIR_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(NO_LOCAL_CHOICE_SUBJECT_CHECK_FLAG, 
				new CLAux(NO_LOCAL_CHOICE_SUBJECT_CHECK_FLAG, 0, true, false,
						"Duplicate flag: "));
		flags.put(NO_ACCEPT_CORRELATION_CHECK_FLAG, 
				new CLAux(NO_ACCEPT_CORRELATION_CHECK_FLAG, 0, true, false,
						"Duplicate flag: "));
		flags.put(NO_VALIDATION_FLAG, 
				new CLAux(NO_VALIDATION_FLAG, 0, true, false, "Duplicate flag: "));
		flags.put(SPIN_FLAG, 
				new CLAux(SPIN_FLAG, 0, true, false, "Duplicate flag: "));

		// These two clash
		flags.put(DOT_FLAG, 
				new CLAux(DOT_FLAG, 0, true, false, "Duplicate flag: ",
						CLFlags.AUT_FLAG));
		flags.put(AUT_FLAG, 
				new CLAux(AUT_FLAG, 0, true, false, "Duplicate flag: ",
						CLFlags.DOT_FLAG));

		// Non-unique, no barrier
		flags.put(PROJECT_FLAG, 
				new CLAux(PROJECT_FLAG, 2, false, false,
						"Missing protocol/role arguments: "));
		flags.put(EFSM_FLAG, 
				new CLAux(EFSM_FLAG, 2, false, false,
						"Missing protocol/role arguments: "));
		flags.put(VALIDATION_EFSM_FLAG, 
				new CLAux(VALIDATION_EFSM_FLAG, 2, false, false,
						"Missing protocol/role arguments: "));
		flags.put(UNFAIR_EFSM_FLAG, 
				new CLAux(UNFAIR_EFSM_FLAG, 2, false, false,
						"Missing protocol/role arguments: "));

		flags.put(EFSM_PNG_FLAG, 
				new CLAux(EFSM_PNG_FLAG, 3, false, false,
						"Missing protocol/role/file arguments: "));
		flags.put(VALIDATION_EFSM_PNG_FLAG, 
				new CLAux(VALIDATION_EFSM_PNG_FLAG, 3, false, false,
						"Missing protocol/role/file arguments: "));
		flags.put(UNFAIR_EFSM_PNG_FLAG, 
				new CLAux(UNFAIR_EFSM_PNG_FLAG, 3, false, false,
						"Missing protocol/role/file arguments: "));

		flags.put(SGRAPH_FLAG, 
				new CLAux(SGRAPH_FLAG, 2, false, false, "Missing protocol argument: "));
		flags.put(UNFAIR_SGRAPH_FLAG, 
				new CLAux(UNFAIR_SGRAPH_FLAG, 2, false, false,
						"Missing protocol argument: "));
		
		flags.put(SGRAPH_PNG_FLAG, 
				new CLAux(SGRAPH_PNG_FLAG, 1, false, false,
						"Missing protocol/file arguments: "));
		flags.put(UNFAIR_SGRAPH_PNG_FLAG, 
				new CLAux(UNFAIR_SGRAPH_PNG_FLAG, 1, false, false,
						"Missing protocol/file arguments: "));

		// // Non-unique, barrier
		flags.put(SESSION_API_GEN_FLAG, 
				new CLAux(SESSION_API_GEN_FLAG, 1, false, true,
						"Missing protocol argument: "));
		flags.put(API_GEN_FLAG, 
				new CLAux(API_GEN_FLAG, 2, false, true,
						"Missing protocol/role arguments: "));
		flags.put(STATECHAN_API_GEN_FLAG, 
				new CLAux(STATECHAN_API_GEN_FLAG, 2, false, true,
						"Missing protocol/role arguments: "));
		flags.put(EVENTDRIVEN_API_GEN_FLAG, 
				new CLAux(EVENTDRIVEN_API_GEN_FLAG, 2, false, true,
						"Missing protocol/role arguments: "));
		
		this.flags = Collections.unmodifiableMap(flags);
	}

		/*this.info.put(CLFlags.JUNIT_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.VERBOSE_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.STATECHAN_SUBTYPES_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.OLD_WF_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.NO_LIVENESS_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.LTSCONVERT_MIN_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.FAIR_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.NO_LOCAL_CHOICE_SUBJECT_CHECK_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.NO_ACCEPT_CORRELATION_CHECK_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.NO_VALIDATION_FLAG, new CLAux(0, "Duplicate flag: "));
		this.info.put(CLFlags.SPIN_FLAG, new CLAux(0, "Duplicate flag: "));

		this.info.put(CLFlags.DOT_FLAG, new CLAux(0, "Duplicate flag: ", CLFlags.AUT_FLAG));
		this.info.put(CLFlags.AUT_FLAG, new CLAux(0, "Duplicate flag: ", CLFlags.DOT_FLAG));

		this.info.put(CLFlags.API_OUTPUT_DIR_FLAG, new CLAux(1, "Missing directory argument: "));
		this.info.put(CLFlags.IMPORT_PATH_FLAG, new CLAux(1, "Missing path argument: "));

		this.info.put(CLFlags.PROJECT_FLAG, new CLAux(2, "Missing protocol/role arguments: "));
		this.info.put(CLFlags.EFSM_FLAG, new CLAux(2, "Missing protocol/role arguments: "));
		this.info.put(CLFlags.VALIDATION_EFSM_FLAG, new CLAux(2, "Missing protocol/role arguments: "));
		this.info.put(CLFlags.UNFAIR_EFSM_FLAG, new CLAux(2, "Missing protocol/role arguments: "));
		this.info.put(CLFlags.API_GEN_FLAG, new CLAux(2, "Missing protocol/role arguments: "));
		this.info.put(CLFlags.STATECHAN_API_GEN_FLAG, new CLAux(2, "Missing protocol/role arguments: "));
		this.info.put(CLFlags.EVENTDRIVEN_API_GEN_FLAG, new CLAux(2, "Missing protocol/role arguments: "));

		this.info.put(CLFlags.EFSM_PNG_FLAG, new CLAux(3, "Missing protocol/role/file arguments: "));
		this.info.put(CLFlags.VALIDATION_EFSM_PNG_FLAG, new CLAux(3, "Missing protocol/role/file arguments: "));
		this.info.put(CLFlags.UNFAIR_EFSM_PNG_FLAG, new CLAux(3, "Missing protocol/role/file arguments: "));

		this.info.put(CLFlags.SGRAPH_FLAG, new CLAux(1, "Missing protocol argument: "));
		this.info.put(CLFlags.UNFAIR_SGRAPH_FLAG, new CLAux(1, "Missing protocol argument: "));
		this.info.put(CLFlags.SESSION_API_GEN_FLAG, new CLAux(1, "Missing protocol argument: "));
		
		this.info.put(CLFlags.SGRAPH_PNG_FLAG, new CLAux(2, "Missing protocol/file arguments: "));
		this.info.put(CLFlags.UNFAIR_SGRAPH_PNG_FLAG, new CLAux(2, "Missing protocol/file arguments: "));*/

	/*
	public Map<Integer, String > getUniqueFlags()
	{
		Map<Integer, String> utmp = new HashMap<>();
		utmp.put(JUNIT, "-junit");
		utmp.put(IMPORT_PATH, IMPORT_PATH_FLAG);
		utmp.put(API_OUTPUT, API_OUTPUT_DIR_FLAG);
		utmp.put(VERBOSE, VERBOSE_FLAG);
		utmp.put(SCHAN_API_SUBTYPES, STATECHAN_SUBTYPES_FLAG);
		utmp.put(OLD_WF, OLD_WF_FLAG);
		utmp.put(NO_PROGRESS, NO_LIVENESS_FLAG);
		utmp.put(LTSCONVERT_MIN, LTSCONVERT_MIN_FLAG);
		utmp.put(FAIR, FAIR_FLAG);
		utmp.put(NO_LOCAL_CHOICE_SUBJECT_CHECK, NO_LOCAL_CHOICE_SUBJECT_CHECK_FLAG);
		utmp.put(NO_ACCEPT_CORRELATION_CHECK, NO_ACCEPT_CORRELATION_CHECK_FLAG);
		utmp.put(DOT, DOT_FLAG);
		utmp.put(AUT, AUT_FLAG);
		utmp.put(NO_VALIDATION, NO_VALIDATION_FLAG);
		utmp.put(INLINE_MAIN_MOD, INLINE_MAIN_MOD_FLAG);
		utmp.put(SPIN, SPIN_FLAG);
		return Collections.unmodifiableMap(utmp);
	}

	public Map<Integer, String > getNonUniqueFlags()
	{
		Map<Integer, String> ntmp = new HashMap<>();
		ntmp.put(PROJECT, PROJECT_FLAG);
		ntmp.put(EFSM, EFSM_FLAG);
		ntmp.put(VALIDATION_EFSM, VALIDATION_EFSM_FLAG);
		ntmp.put(UNFAIR_EFSM, UNFAIR_EFSM_FLAG);
		ntmp.put(UNFAIR_EFSM_PNG, UNFAIR_EFSM_PNG_FLAG);
		ntmp.put(EFSM_PNG, EFSM_PNG_FLAG);
		ntmp.put(VALIDATION_EFSM_PNG, VALIDATION_EFSM_PNG_FLAG);
		ntmp.put(SGRAPH, SGRAPH_FLAG);
		ntmp.put(UNFAIR_SGRAPH, UNFAIR_SGRAPH_FLAG);
		ntmp.put(SGRAPH_PNG, SGRAPH_PNG_FLAG);
		ntmp.put(UNFAIR_SGRAPH_PNG, UNFAIR_SGRAPH_PNG_FLAG);
		ntmp.put(API_GEN, API_GEN_FLAG);
		ntmp.put(SESS_API_GEN, SESSION_API_GEN_FLAG);
		ntmp.put(SCHAN_API_GEN, STATECHAN_API_GEN_FLAG);
		ntmp.put(ED_API_GEN, EVENTDRIVEN_API_GEN_FLAG);
		return Collections.unmodifiableMap(ntmp);
	}
	//*/
}
