package org.scribble.codegen.purescript;

import java.util.List;
import java.util.Objects;

public class DataType {
    public static final String KIND_TYPE = "Type";
    public final String name;
    private final List<ForeignType> params;
    private final String kind;
    private boolean isForeign;

    public static boolean isValidName(String name) {
        // Names must begin with capital letters and contain only letters and digits
		return !(name.isEmpty() || name.charAt(0) < 65 || name.charAt(0) > 90);
    }

    public DataType(String name, List<ForeignType> args, String kind, boolean isForeign) {
        this.isForeign = isForeign;
        if (!isValidName(name)) {
            throw new RuntimeException("`" + name + "' is an invalid data type name");
        }
        if (!isValidName(kind)) {
            throw new RuntimeException("`" + kind + "' is an invalid data type kind");
        }
        this.name = name;
        this.params = args;
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataType dataType = (DataType) o;
        return Objects.equals(params, dataType.params) &&
                Objects.equals(name, dataType.name) &&
                Objects.equals(kind, dataType.kind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params, name, kind);
    }

    public String generateDataType() {
        if (isForeign) {
            return "foreign import data " + name + " :: " + kind + "\n";
        } else {
            // TODO: Potential newtype optimisation for constructors with exactly one value
            // TODO: Derive JSON encoding/decoding
            StringBuilder sb = new StringBuilder();
            sb.append("data " + name + " = " + name);
            for (ForeignType type : params) {
                sb.append(" " + type.name);
            }
            sb.append("\n");
            if (kind.equals(KIND_TYPE)) {
                sb.append("derive instance generic" + name + " :: Generic " + name + " _\n");
                sb.append("instance encodeJson" + name + " :: EncodeJson " + name + " where\n");
                sb.append("  encodeJson = genericEncodeJson\n");
                sb.append("instance decodeJson" + name + " :: DecodeJson " + name + " where\n");
                sb.append("  decodeJson = genericDecodeJson\n");
            }
            return sb.toString();
        }
    }
}
