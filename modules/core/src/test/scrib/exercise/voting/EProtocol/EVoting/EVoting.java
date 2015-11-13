package exercise.voting.EProtocol.EVoting;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.scribble.sesstype.name.Role;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;

public final class EVoting extends org.scribble.net.session.Session {
	public static final List<String> IMPATH = new LinkedList<>();
	public static final String SOURCE = "getSource";
	public static final org.scribble.sesstype.name.GProtocolName PROTO = org.scribble.sesstype.SessionTypeFactory.parseGlobalProtocolName("exercise.voting.EProtocol.EVoting");
	public static final S S = exercise.voting.EProtocol.EVoting.roles.S.S;
	public static final V V = exercise.voting.EProtocol.EVoting.roles.V.V;
	public static final No No = exercise.voting.EProtocol.EVoting.ops.No.No;
	public static final Reject Reject = exercise.voting.EProtocol.EVoting.ops.Reject.Reject;
	public static final Ok Ok = exercise.voting.EProtocol.EVoting.ops.Ok.Ok;
	public static final Yes Yes = exercise.voting.EProtocol.EVoting.ops.Yes.Yes;
	public static final Authenticate Authenticate = exercise.voting.EProtocol.EVoting.ops.Authenticate.Authenticate;
	public static final Result Result = exercise.voting.EProtocol.EVoting.ops.Result.Result;
	public static final List<Role> ROLES = Collections.unmodifiableList(Arrays.asList(new Role[] {S, V}));

	public EVoting() {
		super(EVoting.IMPATH, EVoting.SOURCE, EVoting.PROTO);
	}

	@Override
	public List<Role> getRoles() {
		return EVoting.ROLES;
	}
}