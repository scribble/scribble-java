package org.scribble.ext.go.core.codegen.statetype;

public class ParamCoreSTApiGenConstants
{
	//public static final String GO_SCRIBBLERUNTIME_PACKAGE = "org/scribble/runtime/net";
	public static final String GO_SCRIBBLERUNTIME_SESSION_PACKAGE = "github.com/scribble/go-runtime/session";
	public static final String GO_SCRIBBLERUNTIME_SESSIONPARAM_PACKAGE = "github.com/scribble/go-runtime/session/param";
	public static final String GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE = "github.com/scribble/go-runtime/transport";
	public static final String GO_SCRIBBLERUNTIME_SHM_PACKAGE = "github.com/scribble/go-runtime/transport/shm";
	public static final String GO_SCRIBBLERUNTIME_BYTES_PACKAGE = "bytes";
	public static final String GO_SCRIBBLERUNTIME_GOB_PACKAGE = "encoding/gob";
	
	public static final String GO_ENDPOINT_TYPE = "session.Endpoint";  // net.MPSTEndpoint;
	public static final String GO_ENDPOINT_CONSTRUCTOR = "session.NewEndpoint";
	public static final String GO_ENDPOINT_PROTO = "Proto";
	public static final String GO_ENDPOINT_ERR = "Err";
	public static final String GO_ENDPOINT_WRITEALL = "WriteAll";
	public static final String GO_ENDPOINT_READALL = "ReadAll";
	public static final String GO_ENDPOINT_STARTPROTOCOL = "StartProtocol";
	public static final String GO_ENDPOINT_FINISHPROTOCOL = "FinishProtocol";
	public static final String GO_ENDPOINT_FINALISE = "Finalise";
	public static final String GO_ENDPOINT_ENDPOINT = "Ept";

	public static final String GO_FINALISER_TYPE = "session.Finaliser";  // net.MPSTEndpoint;
	
	public static final String GO_ROLE_TYPE = "session.Role";
	public static final String GO_ROLE_CONSTRUCTOR = "session.NewPRole";

	public static final String GO_PARAMROLE_TYPE = "session.ParamRole";

	public static final String GO_LINEARRESOURCE_TYPE = "session.LinearResource";  // net.LinearResource
	public static final String GO_LINEARRESOURCE_USE = "Use";  // net.LinearResource

	public static final String GO_SCHAN_ENDPOINT       = "ept";  // ep;
	public static final String GO_SCHAN_LINEARRESOURCE = "res";  // state

	public static final String GO_SCHAN_END_TYPE = "End";
	
	public static final String GO_IO_FUN_RECEIVER = "s";
	
	public static final String GO_CROSS_SEND_FUN_PREFIX = "Send";
	public static final String GO_CROSS_SEND_FUN_ARG = "arg";
	public static final String GO_CROSS_RECEIVE_FUN_PREFIX = "Recv";
	public static final String GO_CROSS_RECEIVE_FUN_ARG = "arg";
	
}
