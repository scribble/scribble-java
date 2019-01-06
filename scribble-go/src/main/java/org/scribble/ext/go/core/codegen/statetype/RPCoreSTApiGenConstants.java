package org.scribble.ext.go.core.codegen.statetype;

public class RPCoreSTApiGenConstants
{
	public static final String INT_RUNTIME_SESSION_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/session2";
	public static final String INTPAIR_RUNTIME_SESSION_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/twodim/session2";

	public static final String RUNTIME_LINEARRESOURCE_TYPE = "session2.LinearResource";
	public static final String RUNTIME_MPCHAN_CONSTRUCTOR = "session2.NewMPChan";
	public static final String RUNTIME_TRANSPORT_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/transport2";
	public static final String RUNTIME_UTIL_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/util";

	public static final String ENDPOINT_PROTO_FIELD = "Proto";
	public static final String ENDPOINT_PROTOCOL_TYPE = "session2.Protocol";
	public static final String ENDPOINT_SELF_FIELD = "Self";
	public static final String ENDPOINT_LIN_FIELD = "lin";
	public static final String ENDPOINT_MPCHAN_FIELD = "MPChan";
	public static final String ENDPOINT_MPCHAN_TYPE = "session2.MPChan";
	public static final String ENDPOINT_FVARS_FIELD = "Fvars";  // foreach vars
	public static final String ENDPOINT_THREAD_FIELD = "Thread";

	
	// FIXME rename
	
  // Endpoint
	public static final String GO_MPCHAN_PROTO = "Proto";
	public static final String GO_MPCHAN_ERR = "Err";

	public static final String GO_MPCHAN_WRITEALL = "Send";
	public static final String GO_MPCHAN_READALL = "Recv";

	public static final String GO_MPCHAN_CONN_WG = "ConnWg"; // Counts initiated but unestablished connection
	public static final String GO_MPCHAN_CONN_MAP = "Conns";
	public static final String GO_MPCHAN_FORMATTER_MAP = "Fmts";
	public static final String GO_MPCHAN_MSEND = "MSend";
	public static final String GO_MPCHAN_MRECV = "MRecv";
	public static final String GO_MPCHAN_ISEND = "ISend";
	public static final String GO_MPCHAN_IRECV = "IRecv";

	public static final String GO_SCRIB_LISTENER_TYPE = "transport2.ScribListener";
	public static final String GO_SCRIB_BINARY_CHAN_TYPE = "transport2.BinChannel";
	public static final String GO_SCRIBMESSAGE_TYPE = "session2.ScribMessage";
	public static final String GO_FORMATTER_TYPE = "session2.ScribMessageFormatter";

	public static final String GO_SCHAN_LINEARRESOURCE = "Res";  // state
	public static final String GO_SCHAN_ENDPOINT       = "Ept";  // ep;
	public static final String GO_SCHAN_ERROR = "Err";           // error

	public static final String GO_IO_METHOD_RECEIVER = "s";

	public static final String GO_LINEARRESOURCE_TYPE = "session2.LinearResource";  // net.LinearResource
	public static final String GO_LINEARRESOURCE_USE = "Use";  // net.LinearResource

	public static final String GO_CROSS_SPLIT_FUN_PREFIX = "Split";
	public static final String GO_CROSS_SEND_FUN_PREFIX = "Send";
	public static final String GO_CROSS_SEND_METHOD_ARG = "arg";
	public static final String GO_CROSS_REDUCE_FUN_PREFIX = "Reduce";
	public static final String GO_CROSS_RECEIVE_FUN_PREFIX = "Recv";
	public static final String GO_CROSS_RECEIVE_METHOD_ARG = "arg";
	public static final String GO_CASE_METHOD_ARG = "arg";

	public static final String RP_SCATTER_METHOD_PREFIX = "Scatter";
	public static final String RP_GATHER_METHOD_PREFIX = "Gather";
}
