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
package org.scribble.core.job;

public enum CoreArgs
{
	VERBOSE,
	FAIR,
	SPIN,
	NO_VALIDATION,
	NO_PROGRESS,  // TODO: deprecate
	MIN_EFSM,  // Currently only affects EFSM output (i.e. -fsm, -dot) and API gen -- doesn't affect model checking

	OLD_WF,  // TODO: deprecate
	NO_LCHOICE_SUBJ_CHECK,  // For debugging only?
	NO_ACC_CORRELATION_CHECK,  // Currently unused
	;
	
	/*public final boolean debug;
	public final boolean useOldWf;
	public final boolean noProgress;  // TODO: deprecate
	public final boolean minEfsm;  // Currently only affects EFSM output (i.e. -fsm, -dot) and API gen -- doesn't affect model checking
	public final boolean fair;
	public final boolean noLocalChoiceSubjectCheck;
	public final boolean noAcceptCorrelationCheck;  // Currently unused
	public final boolean noValidation;
	public final boolean spin;

//boolean debug, boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair, boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck, boolean noValidation, boolean spin
	public LangArgs(Set<Args> args)
	{
		this.debug = debug;
		this.useOldWf = useOldWf;
		this.noProgress = noLiveness;
		this.minEfsm = minEfsm;
		this.fair = fair;
		this.noLocalChoiceSubjectCheck = noLocalChoiceSubjectCheck;
		this.noAcceptCorrelationCheck = noAcceptCorrelationCheck;
		this.noValidation = noValidation;
		this.spin = spin;
	}*/

}
