package exercise.voting.EProtocol.EVoting.channels.S;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class EVoting_S_1_Future extends org.scribble.net.ScribFuture {
	public java.lang.String pay1;

	protected EVoting_S_1_Future(CompletableFuture<org.scribble.net.ScribMessage> fut) {
		super(fut);
	}

	public EVoting_S_1_Future sync() throws IOException {
		org.scribble.net.ScribMessage m = super.get();
		this.pay1 = (java.lang.String) m.payload[0];
		return this;
	}
}