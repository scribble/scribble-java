package org.scribble.codegen.purescript;

import java.util.*;

public class ForeignType {
    public static final String PRIM_MODULE = "Prim";
    public static final String[] PRIMS = new String[]{"Number", "Int", "String", "Char", "Boolean"};
    public final String name;
    public final String source;
    // Primitive types in the language - no need to explicitly import
    // See https://pursuit.purescript.org/builtins/docs/Prim

    public ForeignType(String name, String source) {
        this.name = name;
        this.source = source;
    }

    // Group by package source
    public static String generateImports(Set<ForeignType> types) {
        StringBuilder is = new StringBuilder();
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
            // No need to explicitly import
            if (source.equals(PRIM_MODULE)) continue;
            is.append("import " + source + " (");
            List<String> ts = new ArrayList<>(imports.get(source));
            is.append(ts.get(0));
            ts.remove(0);
            for (String type : ts) {
                is.append(", " + type);
            }
            is.append(")\n");
        }
        return is.toString();
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
