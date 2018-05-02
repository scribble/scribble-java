package org.scribble.codegen.purescript.endpointapi;

import org.scribble.util.Pair;

public class UnaryTransition {
    private final String state;
    private final DataType role;
    private final Transition kind;
    private final Pair<String, DataType> action;

    public enum Transition {
        SEND,
        RECEIVE
	}

	public static String toTypeClass(Transition kind) {
        if (kind == Transition.SEND) return "Send";
        else if (kind == Transition.RECEIVE) return "Receive";
        return null;
    }

    public UnaryTransition(String state, DataType role, Transition kind, Pair<String, DataType> action) {
        this.state = state;
        this.role = role;
        this.kind = kind;
        this.action = action;
    }

    public String generateTransition() {
        return "instance " + toTypeClass(kind).toLowerCase() + state
                + " :: " + toTypeClass(kind) + " " + role.name + " " + state
                + " " + action.left + " " + action.right.name + "\n";
    }
}
