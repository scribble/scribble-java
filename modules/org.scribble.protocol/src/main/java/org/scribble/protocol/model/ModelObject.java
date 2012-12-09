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
package org.scribble.protocol.model;

import org.scribble.common.model.Annotation;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 */
public abstract class ModelObject {

    private ModelObject _parent=null;
    private java.util.List<Annotation> _annotations=
        new java.util.Vector<Annotation>();
    private java.util.Map<String, Object> _properties=
        new java.util.HashMap<String, Object>();

    /**
     * This is the default constructor for the model object.
     */
    public ModelObject() {
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param obj The object to copy
     */
    public ModelObject(ModelObject obj) {
        _properties.putAll(obj.getProperties());
        _annotations.addAll(obj.getAnnotations());
    }
    
    /**
     * This method returns the parent of this
     * model object.
     * 
     * @return The parent, or null if top model
     *                     object
     */
    public ModelObject getParent() {
        return (_parent);
    }
    
    /**
     * This method sets the parent model object.
     * 
     * @param parent The parent
     */
    public void setParent(ModelObject parent) {
        _parent = parent;
    }
    
    /**
     * This method establishes the necessary information to
     * indicate that the current model object is derived
     * from the supplied source model object.
     * 
     * @param modelObj The source model object
     */
    public void derivedFrom(ModelObject modelObj) {
        if (modelObj != null) {
            _properties = new java.util.HashMap<String, Object>(modelObj.getProperties());
            _annotations = new java.util.Vector<Annotation>(modelObj.getAnnotations());
        }
    }
    
    /**
     * This method returns the list of annotations associated with the
     * model object.
     * 
     * @return The list of annotations
     */
    public java.util.List<Annotation> getAnnotations() {
        return (_annotations);
    }
    
    /**
     * This method returns the properties associated
     * with this model object.
     * 
     * @return The properties
     */
    public java.util.Map<String, Object> getProperties() {
        return (_properties);
    }
    
    /**
     * This method returns the protocol model in which this
     * object is contained.
     * 
     * @return The protocol model, or null if not found
     */
    public ProtocolModel getModel() {
        ProtocolModel ret=null;
        ModelObject cur=this;
        
        while (ret == null && cur != null) {
            
            if (cur instanceof ProtocolModel) {
                ret = (ProtocolModel) cur;
            } else {
                cur = cur.getParent();
            }
        }
        
        return (ret);
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public abstract void visit(Visitor visitor);
}
