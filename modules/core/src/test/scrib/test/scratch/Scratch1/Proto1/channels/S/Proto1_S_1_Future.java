package test.scratch.Scratch1.Proto1.channels.S;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Proto1_S_1_Future extends org.scribble.net.ScribFuture {

	protected Proto1_S_1_Future(CompletableFuture<org.scribble.net.ScribMessage> fut) {
		super(fut);
	}

	public Proto1_S_1_Future sync() throws IOException {
		super.get();
		return this;
	}
}