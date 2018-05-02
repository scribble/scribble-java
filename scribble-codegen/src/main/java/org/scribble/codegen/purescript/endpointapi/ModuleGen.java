package org.scribble.codegen.purescript.endpointapi;

import java.util.Map;
import java.util.Set;

public class ModuleGen {
    public final String moduleName;
    public final String protocolName;
    public final Set<ForeignType> foreignTypes;
//    public final Map<String, Map<String,  Transition>>
    public ModuleGen(String moduleName, String protocolName, Set<ForeignType> foreignTypes) {
        this.moduleName = moduleName;
        this.protocolName = protocolName;
        this.foreignTypes = foreignTypes;
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

        return "";
    }
}
