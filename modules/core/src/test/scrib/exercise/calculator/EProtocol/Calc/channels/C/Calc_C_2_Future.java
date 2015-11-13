package exercise.calculator.EProtocol.Calc.channels.C;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Calc_C_2_Future extends org.scribble.net.ScribFuture {
	public java.lang.Integer pay1;

	protected Calc_C_2_Future(CompletableFuture<org.scribble.net.ScribMessage> fut) {
		super(fut);
	}

	public Calc_C_2_Future sync() throws IOException {
		org.scribble.net.ScribMessage m = super.get();
		this.pay1 = (java.lang.Integer) m.payload[0];
		return this;
	}
}