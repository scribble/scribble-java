/*
 * Copyright 2009 www.scribble.org
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.model.global;

import org.scribble.model.Argument;
import org.scribble.model.ContainmentList;
import org.scribble.model.ParameterDecl;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.Role;
import org.scribble.model.RoleDecl;
import org.scribble.model.RoleInstantiation;
import org.scribble.model.Visitor;

/**
 * This class represents the protocol notation.
 */
public class GProtocolInstance extends ProtocolDecl {
    
    private String _memberName=null;
    private java.util.List<Argument> _arguments=new ContainmentList<Argument>(this, Argument.class);
    private java.util.List<RoleInstantiation> _roleInstantiations=new ContainmentList<RoleInstantiation>(this,
    											RoleInstantiation.class);

    /**
     * The default constructor.
     */
    public GProtocolInstance() {
    }
    
    /**
     * This method returns the member name.
     * 
     * @return The name
     */
    public String getMemberName() {
        return (_memberName);
    }
    
    /**
     * This method sets the member name.
     * 
     * @param name The name
     */
    public void setMemberName(String name) {
        _memberName = name;
    }
    
    /**
     * This method returns the role declarations associated with
     * the protocol.
     * 
     * @return The role declarations
     */
    public java.util.List<RoleInstantiation> getRoleInstantiations() {
        return (_roleInstantiations);
    }
    
    /**
     * This method returns the role instantiation associated with the supplied
     * role name, or null if not found.
     * 
     * @param role The role
     * @return The role instantiation, or null if not found
     */
    public RoleInstantiation getRoleInstantiation(RoleDecl role) {
    	for (RoleInstantiation ri : _roleInstantiations) {
    		if (role.isRole(new Role(ri.getName()))) {
    			return (ri);
    		}
    	}
    	
        return (null);
    }
    
    /**
     * This method returns the parameter declarations associated with
     * the protocol.
     * 
     * @return The parameter declarations
     */
    public java.util.List<Argument> getArguments() {
        return (_arguments);
    }
        
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
    	if (visitor instanceof GVisitor) {
	        ((GVisitor)visitor).accept(this);
    	}
    }
    
    public String toString() {
        String ret="global protocol "+getName();
        
        if (getParameterDeclarations().size() > 0) {
            ret += " <";
            
            for (int i=0; i < getParameterDeclarations().size(); i++) {
            	ParameterDecl pd=getParameterDeclarations().get(i);
            	
            	if (i > 0) {
            		ret += ",";
            	}
            	
                ret += (pd.getType().name().toLowerCase()+" "+pd.getName());
                if (pd.getAlias() != null) {
                	ret += " as "+pd.getAlias();
                }
            }
            
            ret += ">";       	
        }
        
        ret += " ( ";
        
        for (int i=0; i < getRoleDeclarations().size(); i++) {
        	RoleDecl role=getRoleDeclarations().get(i);
        	
        	if (i > 0) {
        		ret += ",";
        	}
        	
            ret += ("role "+role.getName()+" ");
            if (role.getAlias() != null) {
            	ret += " as "+role.getAlias();
            }
        }
        
        ret += ") instantiates ";
        
        ret += _memberName;
        
        return(ret);
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
    	indent(buf, level);
    	
    	buf.append("global protocol ");
    	
    	buf.append(getName());
    	
    	if (getParameterDeclarations().size() > 0) {
        	buf.append("<");
        	
        	for (int i=0; i < getParameterDeclarations().size(); i++) {
        		if (i > 0) {
        			buf.append(",");
        		}
        		getParameterDeclarations().get(i).toText(buf, level);
        	}
        	buf.append(">");
    	}
    	
    	buf.append("(");
    	
    	for (int i=0; i < getRoleDeclarations().size(); i++) {
    		if (i > 0) {
    			buf.append(",");
    		}
    		buf.append("role ");
    		getRoleDeclarations().get(i).toText(buf, level);
    	}
    	buf.append(") instantiates ");
    	
    	buf.append(getMemberName());
    	
    	if (getArguments().size() > 0) {
        	buf.append("<");
        	
        	for (int i=0; i < getArguments().size(); i++) {
        		if (i > 0) {
        			buf.append(",");
        		}
        		getArguments().get(i).toText(buf, level);
        	}
        	buf.append(">");
    	}
    	
    	buf.append("(");
    	
    	for (int i=0; i < getRoleInstantiations().size(); i++) {
    		if (i > 0) {
    			buf.append(",");
    		}
    		getRoleInstantiations().get(i).toText(buf, level);
    	}

    	buf.append(");\n");
	}    
}
