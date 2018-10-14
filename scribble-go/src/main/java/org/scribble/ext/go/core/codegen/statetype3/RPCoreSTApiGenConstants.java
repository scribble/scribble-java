package org.scribble.ext.go.core.codegen.statetype3;

public class RPCoreSTApiGenConstants
{
	//public static final String GO_SCRIBBLERUNTIME_PACKAGE = "org/scribble/runtime/net";
	public static final String GO_SCRIBBLERUNTIME_SESSION_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/session2";
	public static final String GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/twodim/session2";
	public static final String GO_SCRIBBLERUNTIME_TRANSPORT_PACKAGE = "github.com/rhu1/scribble-go-runtime/runtime/transport2";
	public static final String GO_SCRIBBLERUNTIME_SHM_PACKAGE = "github.com/scribble/go-runtime/transport2/shm";
	public static final String GO_SCRIBBLERUNTIME_BYTES_PACKAGE = "bytes";
	public static final String GO_SCRIBBLERUNTIME_GOB_PACKAGE = "encoding/gob";

	public static final String GO_SCRIBBLERUNTIME_SESSIONPARAM_PACKAGE = "github.com/scribble/go-runtime/session2/param";
	
	public static final String GO_MPCHAN_TYPE = "session2.MPChan";  // net.MPSTEndpoint;
	public static final String GO_MPCHAN_CONSTRUCTOR = "session2.NewMPChan";
	public static final String GO_MPCHAN_PROTO = "Proto";
	public static final String GO_MPCHAN_ERR = "Err";
	public static final String GO_MPCHAN_FINALISE = "Finalise";
	public static final String GO_MPCHAN_SESSCHAN = "MPChan";
	public static final String GO_MPCHAN_PARAMS = "Params";
	public static final String GO_MPCHAN_CONN_WG = "ConnWg"; // Counts initiated but unestablished connection
	public static final String GO_MPCHAN_CONN_MAP = "Conns";
	public static final String GO_MPCHAN_FORMATTER_MAP = "Fmts";
	public static final String GO_MPCHAN_MSEND = "MSend";
	public static final String GO_MPCHAN_MRECV = "MRecv";
	public static final String GO_MPCHAN_ISEND = "ISend";
	public static final String GO_MPCHAN_IRECV = "IRecv";

	public static final String GO_MPCHAN_WRITEALL = "Send";
	public static final String GO_MPCHAN_READALL = "Recv";
	public static final String GO_MPCHAN_STARTPROTOCOL = "StartProtocol";
	public static final String GO_MPCHAN_FINISHPROTOCOL = "FinishProtocol";

	public static final String GO_FORMATTER_SERIALIZE = "Serialize";
	public static final String GO_FORMATTER_DESERIALIZE = "Deserialize";
	public static final String GO_FORMATTER_ENCODE_INT = "EncodeInt";
	public static final String GO_FORMATTER_DECODE_INT = "DecodeInt";
	public static final String GO_FORMATTER_ENCODE_STRING = "EncodeString";
	public static final String GO_FORMATTER_DECODE_STRING = "DecodeString";

	public static final String GO_FINALISER_TYPE = "session2.Finaliser";  // net.MPSTEndpoint;
	
	public static final String GO_ROLE_TYPE = "session2.Role";
	public static final String GO_ROLE_CONSTRUCTOR = "session2.NewRole";

	public static final String GO_PARAMROLE_TYPE = "session2.ParamRole";

	public static final String GO_PROTOCOL_TYPE = "session2.Protocol";
	public static final String GO_SCRIBMESSAGE_TYPE = "session2.ScribMessage";
	public static final String GO_FORMATTER_TYPE = "session2.ScribMessageFormatter";
	public static final String GO_SCRIB_LISTENER_TYPE = "transport2.ScribListener";
	public static final String GO_SCRIB_BINARY_CHAN_TYPE = "transport2.BinChannel";

	public static final String GO_LINEARRESOURCE_TYPE = "session2.LinearResource";  // net.LinearResource
	public static final String GO_LINEARRESOURCE_USE = "Use";  // net.LinearResource

	public static final String GO_SCHAN_ENDPOINT       = "Ept";  // ep;
	public static final String GO_SCHAN_LINEARRESOURCE = "Res";  // state
	public static final String GO_SCHAN_ERROR = "Err";           // error

	public static final String GO_SCHAN_END_TYPE = "End";
	
	public static final String GO_IO_METHOD_RECEIVER = "s";

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
