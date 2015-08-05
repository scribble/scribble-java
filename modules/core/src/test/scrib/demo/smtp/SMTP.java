package demo.smtp;

import java.util.LinkedList;
import java.util.List;

public class SMTP extends org.scribble.net.session.Session {
	public static final List<String> IMPATH = new LinkedList<>();
	public static final String SOURCE = "getSource";
	public static final org.scribble.sesstype.name.GProtocolName PROTO = org.scribble.sesstype.SessionTypeFactory.parseGlobalProtocolName("demo.smtp.Smtp.SMTP");
	public static final S S = new S();
	public static final C C = new C();
	public static final SUBJECT SUBJECT = new SUBJECT();
	public static final STARTTLS STARTTLS = new STARTTLS();
	public static final ATAD ATAD = new ATAD();
	public static final MAIL MAIL = new MAIL();
	public static final _501 _501 = new _501();
	public static final RCPT RCPT = new RCPT();
	public static final _535 _535 = new _535();
	public static final _250_ _250_ = new _250_();
	public static final _235 _235 = new _235();
	public static final _354 _354 = new _354();
	public static final QUIT QUIT = new QUIT();
	public static final _220 _220 = new _220();
	public static final EHLO EHLO = new EHLO();
	public static final _250 _250 = new _250();
	public static final AUTH AUTH = new AUTH();
	public static final DATA DATA = new DATA();
	public static final DATALINE DATALINE = new DATALINE();

	public SMTP() {
		super(SMTP.IMPATH, SMTP.SOURCE, SMTP.PROTO);
	}
}

class S extends org.scribble.sesstype.name.Role {
	private static final long serialVersionUID = 1L;

	protected S() {
		super("S");
	}
}

class C extends org.scribble.sesstype.name.Role {
	private static final long serialVersionUID = 1L;

	protected C() {
		super("C");
	}
}

class SUBJECT extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected SUBJECT() {
		super("SUBJECT");
	}
}

class STARTTLS extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected STARTTLS() {
		super("STARTTLS");
	}
}

class ATAD extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected ATAD() {
		super("ATAD");
	}
}

class MAIL extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected MAIL() {
		super("MAIL");
	}
}

class _501 extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected _501() {
		super("_501");
	}
}

class RCPT extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected RCPT() {
		super("RCPT");
	}
}

class _535 extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected _535() {
		super("_535");
	}
}

class _250_ extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected _250_() {
		super("_250_");
	}
}

class _235 extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected _235() {
		super("_235");
	}
}

class _354 extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected _354() {
		super("_354");
	}
}

class QUIT extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected QUIT() {
		super("QUIT");
	}
}

class _220 extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected _220() {
		super("_220");
	}
}

class EHLO extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected EHLO() {
		super("EHLO");
	}
}

class _250 extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected _250() {
		super("_250");
	}
}

class AUTH extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected AUTH() {
		super("AUTH");
	}
}

class DATA extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected DATA() {
		super("DATA");
	}
}

class DATALINE extends org.scribble.sesstype.name.Op {
	private static final long serialVersionUID = 1L;

	protected DATALINE() {
		super("DATALINE");
	}
}