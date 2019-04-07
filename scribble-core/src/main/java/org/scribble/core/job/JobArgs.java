package org.scribble.core.job;

public enum JobArgs
{
	debug,
	useOldWf,
	noProgress,
	minEfsm,
	fair,
	noLocalChoiceSubjectCheck,
	noAcceptCorrelationCheck,
	noValidation,
	spin;
	
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
