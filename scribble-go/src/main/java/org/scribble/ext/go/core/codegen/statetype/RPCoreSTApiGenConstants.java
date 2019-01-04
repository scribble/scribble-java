package org.scribble.ext.go.core.codegen.statetype;

public class RPCoreSTApiGenConstants
{
	public static final String INT_RUNTIME_SESSION_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/session2";
	public static final String INTPAIR_RUNTIME_SESSION_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/twodim/session2";

	public static final String RUNTIME_LINEARRESOURCE_TYPE = "session2.LinearResource";
	public static final String RUNTIME_MPCHAN_CONSTRUCTOR = "session2.NewMPChan";
	public static final String RUNTIME_TRANSPORT_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/transport2";
	public static final String RUNTIME_UTIL_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/util";

	public static final String GO_MPCHAN_CONN_WG = "ConnWg"; // Counts initiated but unestablished connection
	public static final String GO_MPCHAN_CONN_MAP = "Conns";
	public static final String GO_MPCHAN_FORMATTER_MAP = "Fmts";

	public static final String GO_SCRIB_LISTENER_TYPE = "transport2.ScribListener";
	public static final String GO_SCRIB_BINARY_CHAN_TYPE = "transport2.BinChannel";
	public static final String GO_FORMATTER_TYPE = "session2.ScribMessageFormatter";

	public static final String ENDPOINT_PROTO_FIELD = "Proto";
	public static final String ENDPOINT_PROTOCOL_TYPE = "session2.Protocol";
	public static final String ENDPOINT_SELF_FIELD = "Self";
	public static final String ENDPOINT_LIN_FIELD = "lin";
	public static final String ENDPOINT_MPCHAN_FIELD = "MPChan";
	public static final String ENDPOINT_MPCHAN_TYPE = "session2.MPChan";
	public static final String ENDPOINT_FVARS_FIELD = "Fvars";  // foreach vars
	public static final String ENDPOINT_THREAD_FIELD = "Thread";

	public static final String GO_MPCHAN_ERR = "Err";
}
