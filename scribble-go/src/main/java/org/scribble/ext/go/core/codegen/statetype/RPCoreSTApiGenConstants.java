package org.scribble.ext.go.core.codegen.statetype;

public class RPCoreSTApiGenConstants
{
	public static final String INT_RUNTIME_SESSION_PACKAGE 
			= "github.com/rhu1/scribble-go-runtime/runtime/session2";
	public static final String INTPAIR_RUNTIME_SESSION_PACKAGE 
			= "github.com/rhu1/scribble-go-runtime/runtime/twodim/session2";
	public static final String TRANSPORT_PACKAGE 
			= "github.com/rhu1/scribble-go-runtime/runtime/transport2";
	public static final String UTIL_PACKAGE 
			= "github.com/rhu1/scribble-go-runtime/runtime/util";

	public static final String SCRIB_PROTOCOL_TYPE = "session2.Protocol";
	public static final String SCRIB_LISTENER_TYPE 
			= "transport2.ScribListener";
	public static final String SCRIB_MESSAGE_TYPE = "session2.ScribMessage";
	public static final String SCRIB_FORMATTER_TYPE 
			= "session2.ScribMessageFormatter";
	public static final String SCRIB_BINCHAN_TYPE 
			= "transport2.BinChannel";

	public static final String ENDPOINT_PROTO_FIELD = "Proto";
	public static final String ENDPOINT_SELF_FIELD = "Self";
	public static final String ENDPOINT_LIN_FIELD = "lin";
	public static final String ENDPOINT_MPCHAN_FIELD = "MPChan";
	public static final String ENDPOINT_FVARS_FIELD = "Fvars";  // foreach vars
	public static final String ENDPOINT_THREAD_FIELD = "Thread";

	public static final String LINEARRESOURCE_TYPE = "session2.LinearResource";  
			// Embedded field, so no field name
	public static final String LINEARRESOURCE_USE = "Use";  // net.LinearResource

	public static final String ENDPOINT_MPCHAN_TYPE = "session2.MPChan";
	public static final String MPCHAN_CONSTR
			= "session2.NewMPChan";
	//public static final String GO_MPCHAN_CONN_WG = "ConnWg"; // Counts initiated but unestablished connection
	public static final String MPCHAN_CONNS_FIELD = "Conns";
	public static final String MPCHAN_FMTS_FIELD = "Fmts";
	public static final String MPCHAN_MSEND = "MSend";
	public static final String MPCHAN_MRECV = "MRecv";
	public static final String MPCHAN_ISEND = "ISend";
	public static final String MPCHAN_IRECV = "IRecv";

	// TODO not currently used by API gen
	public static final String GO_MPCHAN_WRITEALL = "TODO"; //"Send";
	public static final String GO_MPCHAN_READALL = "TODO"; //"Recv";

	public static final String SCHAN_RES_FIELD = "Res";  // state
	public static final String SCHAN_EPT_FIELD       = "Ept";  // ep;
	public static final String SCHAN_ERR_FIELD = "Err";           // error

	public static final String API_IO_METHOD_RECEIVER = "s";
	public static final String API_SEND_PREFIX = "Send";
	public static final String API_SCATTER_PREFIX = "Scatter";
	public static final String API_SEND_ARG = "arg";
	public static final String API_RECEIVE_PREFIX = "Recv";
	public static final String API_GATHER_PREFIX = "Gather";
	public static final String API_RECEIVE_ARG = "arg";
	public static final String API_CASE_ARG = "arg";

	// TODO not currently used by API gen
	public static final String GO_CROSS_SPLIT_FUN_PREFIX = "Split";
	public static final String GO_CROSS_REDUCE_FUN_PREFIX = "Reduce";
}
