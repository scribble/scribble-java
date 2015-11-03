package test.scratch.Scratch1.Proto1.channels.C;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Proto1_C_3_Future extends org.scribble.net.ScribFuture {
	public java.lang.Integer pay1;

	protected Proto1_C_3_Future(CompletableFuture<org.scribble.net.ScribMessage> fut) {
		super(fut);
	}

	public Proto1_C_3_Future sync() throws IOException {
		org.scribble.net.ScribMessage m = super.get();
		this.pay1 = (java.lang.Integer) m.payload[0];
		return this;
	}
}