package org.scribble.ext.go.core.cli;

public enum RPCoreCLArgFlag
{
	// Unique flags
	RPCORE_PARAM,
	RPCORE_SELECT_BRANCH,  // Default is type switch
	RPCORE_NO_COPY,
	RPCORE_PARFOREACH,
	RPCORE_DOTAPI,

	// Non-unique flags
	RPCORE_EFSM,
	RPCORE_EFSM_PNG,
	RPCORE_API_GEN,
}
