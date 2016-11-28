package org.scribble.model.endpoint;

public enum EStateKind
{
	OUTPUT,      // SEND, CONNECT and WRAP_CLIENT
	UNARY_INPUT,
	POLY_INPUT,
	TERMINAL,
	ACCEPT,      // Unary/multi accept?
	WRAP_SERVER,
}
