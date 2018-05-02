package org.scribble.codegen.purescript.endpointapi;

import org.scribble.util.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PolyTransition {

    private final String state;
    private final DataType thisRole;
    private final DataType thatRole;
    private final Transition kind;
    private final Map<String, Pair<String, DataType>> actions;

    public enum Transition {
        BRANCH,
        SELECT
	}

	public static String toTypeClass(Transition kind) {
        if (kind == Transition.BRANCH) return "Branch";
        else if (kind == Transition.SELECT) return "Select";
        return null;
    }

	public PolyTransition(String state, DataType thisRole, DataType thatRole, Transition kind, Map<String, Pair<String, DataType>> actions) {
        this.state = state;
        this.thisRole = thisRole;
        this.thatRole = thatRole;
        this.kind = kind;
        this.actions = actions;
    }

    public Pair<String, Set<DataType>> generateTransitions() {
        StringBuilder t = new StringBuilder();

        t.append("instance " + toTypeClass(kind).toLowerCase() + state + " :: ");
        t.append(toTypeClass(kind) + " ");
        String role = kind == Transition.BRANCH ? thisRole.name : thatRole.name;
        t.append(role + " " + state + " ");

        // The transitions from the intermediate states
        StringBuilder ts = new StringBuilder();
        Set<DataType> intermediates = new HashSet<>();
        String end = "Nil";
        for (String label : actions.keySet()) {
            Pair<String, DataType> action = actions.get(label);
            String iname = state + label;
            DataType intermediate = new DataType(iname, null, DataType.KIND_TYPE, true);
            intermediates.add(intermediate);

            t.append("(Cons \"" + action.left + "\" " + iname + " ");
            end += ")";

            ts.append("instance ");
            if (kind == Transition.BRANCH) t.append("receive");
            else if (kind == Transition.SELECT) t.append("send");
            ts.append(state + " :: ");
            if (kind == Transition.BRANCH) t.append("Receive");
            else if (kind == Transition.SELECT) t.append("Send");
            ts.append(" " + thatRole.name + " " + state + " " + action.left + " " + action.right.name + "\n");
        }
        t.append(end + "\n");
        t.append(ts.toString());

        return new Pair<>(t.toString(), intermediates);
    }

}
