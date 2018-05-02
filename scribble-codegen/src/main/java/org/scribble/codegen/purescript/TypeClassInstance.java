package org.scribble.codegen.purescript;

import java.util.List;

public class TypeClassInstance {
    private final String instance;
    private final String typeclass;
    private final String[] parameters;

    public TypeClassInstance(String instance, String typeclass, String[] parameters) {
        this.instance = instance;
        this.typeclass = typeclass;
        this.parameters = parameters;
    }

    public String generateInstance() {
        StringBuilder sb = new StringBuilder();
        sb.append("instance " + instance + " :: " + typeclass);
        for (String parameter : parameters) {
            sb.append(" " + parameter);
        }
        sb.append("\n");
        return sb.toString();
    }
}
