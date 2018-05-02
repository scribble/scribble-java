package org.scribble.codegen.purescript.endpointapi;

import java.util.*;

public class ForeignType {
    public final String name;
    public final String source;
    // Primitive types in the language - no need to explicitly import
    // See https://pursuit.purescript.org/builtins/docs/Prim
    // TODO: Don't generate import statements for them
    public static final String[] PRIMS = new String[]{"Number", "Int", "String", "Char", "Boolean"};

    public ForeignType(String name, String source) {
        this.name = name;
        this.source = source;
    }

    public String generateImport() {
        return "import " + source + " (" + name + ")\n";
    }

    // Group by package source
    public static String generateImports(Set<ForeignType> types) {
        HashMap<String, Set<String>> imports = new HashMap();
        for (ForeignType type : types) {
            if (imports.containsKey(type.source)) {
                imports.get(type.source).add(type.name);
            } else {
                Set<String> mod = new HashSet<>();
                mod.add(type.name);
                imports.put(type.source, mod);
            }
        }
        for (String source : imports.keySet()) {
            String i = "import " + source + " (";
            for (String s : imports.get(source)) {
                i += s + ", ";
            }
        }
        return ")\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForeignType that = (ForeignType) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, source);
    }
}
