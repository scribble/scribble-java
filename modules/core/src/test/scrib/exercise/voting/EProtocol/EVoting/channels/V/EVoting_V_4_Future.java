package exercise.voting.EProtocol.EVoting.channels.V;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class EVoting_V_4_Future extends org.scribble.net.ScribFuture {
	public java.lang.Integer pay1;

	protected EVoting_V_4_Future(CompletableFuture<org.scribble.net.ScribMessage> fut) {
		super(fut);
	}

	public EVoting_V_4_Future sync() throws IOException {
		org.scribble.net.ScribMessage m = super.get();
		this.pay1 = (java.lang.Integer) m.payload[0];
		return this;
	}
}