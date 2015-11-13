package exercise.calculator.EProtocol.Calc.channels.C;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Calc_C_4_Future extends org.scribble.net.ScribFuture {

	protected Calc_C_4_Future(CompletableFuture<org.scribble.net.ScribMessage> fut) {
		super(fut);
	}

	public Calc_C_4_Future sync() throws IOException {
		super.get();
		return this;
	}
}