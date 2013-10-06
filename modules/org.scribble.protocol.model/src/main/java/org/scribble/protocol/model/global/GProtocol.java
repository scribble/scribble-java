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
package org.scribble.protocol.model.global;

import org.scribble.protocol.model.ParameterDecl;
import org.scribble.protocol.model.ProtocolDecl;
import org.scribble.protocol.model.RoleDecl;
import org.scribble.protocol.model.Visitor;

/**
 * This class represents the protocol notation.
 */
public class GProtocol extends ProtocolDecl {
    
    private GBlock _block=null;

    /**
     * The default constructor.
     */
    public GProtocol() {
    }
    
    /**
     * This method returns the block of activities associated
     * with the definition.
     * 
     * @return The block of activities
     */
    public GBlock getBlock() {
        
        if (_block == null) {
            _block = new GBlock();
            _block.setParent(this);
        }
        
        return (_block);
    }
    
    /**
     * This method sets the block of activities associated
     * with the definition.
     * 
     * @param block The block of activities
     */
    public void setBlock(GBlock block) {
        if (_block != null) {
            _block.setParent(null);
        }
        
        _block = block;
        
        if (_block != null) {
            _block.setParent(this);
        }
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(Visitor visitor) {
        
    	if (visitor instanceof GVisitor) {
	        if (((GVisitor)visitor).start(this)) {
	        
	            if (getBlock() != null) {
	                getBlock().visit(visitor);
	            }
	        }
	        
	        ((GVisitor)visitor).end(this);
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
        
        ret += ")\n";
        
        ret += getBlock();
        
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
    	buf.append(") ");
    	
    	
    	if (_block != null) {
    		_block.toText(buf, level);
    	}
    	
		buf.append("\n");
	}    
}
