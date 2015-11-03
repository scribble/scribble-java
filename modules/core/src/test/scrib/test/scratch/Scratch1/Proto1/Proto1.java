package test.scratch.Scratch1.Proto1;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.scribble.sesstype.name.Role;
import test.scratch.Scratch1.Proto1.roles.*;
import test.scratch.Scratch1.Proto1.ops.*;

public final class Proto1 extends org.scribble.net.session.Session {
	public static final List<String> IMPATH = new LinkedList<>();
	public static final String SOURCE = "getSource";
	public static final org.scribble.sesstype.name.GProtocolName PROTO = org.scribble.sesstype.SessionTypeFactory.parseGlobalProtocolName("test.scratch.Scratch1.Proto1");
	public static final C C = test.scratch.Scratch1.Proto1.roles.C.C;
	public static final S S = test.scratch.Scratch1.Proto1.roles.S.S;
	public static final _2 _2 = test.scratch.Scratch1.Proto1.ops._2._2;
	public static final _3 _3 = test.scratch.Scratch1.Proto1.ops._3._3;
	public static final _4 _4 = test.scratch.Scratch1.Proto1.ops._4._4;
	public static final _1 _1 = test.scratch.Scratch1.Proto1.ops._1._1;
	public static final List<Role> ROLES = Collections.unmodifiableList(Arrays.asList(new Role[] {C, S}));

	public Proto1() {
		super(Proto1.IMPATH, Proto1.SOURCE, Proto1.PROTO);
	}

	@Override
	public List<Role> getRoles() {
		return Proto1.ROLES;
	}
}