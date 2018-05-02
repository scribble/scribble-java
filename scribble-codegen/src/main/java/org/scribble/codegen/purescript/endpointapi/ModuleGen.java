package org.scribble.codegen.purescript.endpointapi;

import org.scribble.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModuleGen {
    public final String moduleName;
    public final String protocolName;
    public final Set<ForeignType> foreignTypes;
    private Map<DataType, Pair<List<String>, List<DataType>>> efsms;

    public ModuleGen(String moduleName, String protocolName, Set<ForeignType> foreignTypes, Map<DataType, Pair<List<String>, List<DataType>>> efsms) {
        this.moduleName = moduleName;
        this.protocolName = protocolName;
        this.foreignTypes = foreignTypes;
        this.efsms = efsms;
    }

    private String moduleDeclaration() {
        return "module Scribble.Protocol." + moduleName + "." + protocolName + " where\n";
    }

    private String staticImports() {
        StringBuilder sb = new StringBuilder();
		sb.append("import Scribble.FSM (class Branch, class Initial, class Terminal, class Receive, class Select, class Send, kind Role)\n");
		sb.append("import Type.Row (Cons, Nil)\n");
		sb.append("import Data.Void (Void)\n");
        return sb.toString();
    }

    public String filename() {
		return "Scribble/Protocol/" + moduleName.replace('.', '/') + "/" + protocolName + ".purs";
	}

    public String generateApi() {
        StringBuilder sb = new StringBuilder();
        sb.append(moduleDeclaration());
        sb.append(ForeignType.generateImports(foreignTypes));
        for (DataType role : efsms.keySet()) {
            sb.append(role.generateDataType());
            for (DataType state : efsms.get(role).right) {
                sb.append(state.generateDataType());
            }
            for (String transition : efsms.get(role).left) {
                sb.append(transition);
            }
        }
        return sb.toString();
    }
}
