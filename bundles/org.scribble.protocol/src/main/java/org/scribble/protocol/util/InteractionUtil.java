/*
 * Copyright 2009-10 www.scribble.org
 *
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
package org.scribble.protocol.util;

import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.DefaultVisitor;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.Run;

/**
 * This class provides utility functions for the interaction construct.
 *
 */
public final class InteractionUtil {

    /**
     * Private constructor.
     */
    private InteractionUtil() {
    }
    
    /**
     * This method gets the 'from' role associated with the
     * activity.
     * 
     * @param act The activity
     * @return The 'from' role if found
     */
    public static Role getFromRole(ModelObject act) {
        Role ret=null;
        
        if (act instanceof Interaction) {
            ret = ((Interaction)act).getFromRole();
        }
        
        return (ret);
    }
    
    /**
     * This method gets the 'to' role associated with the
     * activity.
     * 
     * @param act The activity
     * @return The 'to' role if found
     */
    public static Role getToRole(ModelObject act) {
        Role ret=null;
        
        if (act instanceof Interaction) {
            // TODO: Need to handle multiple 'to' roles
            if (((Interaction)act).getToRoles().size() > 0) {
                ret = ((Interaction)act).getToRoles().get(0);
            }
        }
        
        return (ret);
    }
    
    /**
     * This method gets the message signature associated with the
     * activity.
     * 
     * @param act The activity
     * @return The message signature if found
     */
    public static MessageSignature getMessageSignature(ModelObject act) {
        MessageSignature ret=null;
        
        if (act instanceof Interaction) {
            ret = ((Interaction)act).getMessageSignature();
        }
        
        return (ret);
    }
    
    /**
     * This method determines the initial interactions associated with the
     * supplied model object.
     * 
     * @param scope The scope within which the search for initial interactions should be conducted
     * @return The list of initial 'interaction' based activities
     */
    public static java.util.List<ModelObject> getInitialInteractions(ModelObject scope) {
        java.util.List<ModelObject> ret=new java.util.Vector<ModelObject>();

        scope.visit(new InitialInteractionVisitor(ret));
        
        return (ret);
    }
    
    /**
     * This method determines whether the supplied interaction is the first within
     * the supplied scope.
     * 
     * @param scope The scope
     * @param interaction The interaction
     * @return Whether the interaction is an initial interaction in the supplied scope
     */
    public static boolean isInitialInteraction(ModelObject scope, ModelObject interaction) {
        boolean ret=false;
        
        java.util.List<ModelObject> initial=getInitialInteractions(scope);
        
        for (int i=0; !ret && i < initial.size(); i++) {
            ret = (initial.get(i) == interaction);
        }
        
        return (ret);
    }

    /**
     * This class identifies the initial interaction.
     *
     */
    public static class InitialInteractionVisitor extends DefaultVisitor {

        private java.util.List<ModelObject> _interactions=null;
        private boolean _record=true;
        private java.util.Map<ModelObject,Boolean> _savedState=
                        new java.util.HashMap<ModelObject,Boolean>();
        
        /**
         * This is the constructor.
         * 
         * @param list The list to contain the interactions
         */
        public InitialInteractionVisitor(java.util.List<ModelObject> list) {
            _interactions = list;
        }
        
        @Override
        public boolean start(Protocol elem) {
            // If top level protocol, then continue, otherwise finish.
            // Nested protocols should only be processed as part of a 
            // 'run' activity.
            return (!(elem.getParent() instanceof Protocol));
        }
        
        @Override
        public boolean start(Choice elem) {
            return (_record);
        }
        
        @Override
        public void end(Choice elem) {
            // TODO: SCRIBBLE-61 for now assume anything following
            // choice cannot be initial interaction
            _record = false;
        }
        
        @Override
        public boolean start(Block elem) {
            
            // If choice block, then need to save state so that can detect
            // first interactions across all paths
            if (elem.getParent() instanceof Choice) {
                _savedState.put(elem, _record);
            }
            
            return (true);
        }
        
        @Override
        public void end(Block elem) {
            if (elem.getParent() instanceof Choice) {
                _record = _savedState.get(elem);
            }
            
            // TODO: SCRIBBLE-61 Need to accumulate results from when paths
            // to determine if subsequent interactions should still be counted
            // as 'initial' - but then could end up with situation where
            // choice has initial interactions followed by another interaction
            // that could potentially be initial
        }
        
        @Override
        public void accept(Interaction elem) {
            
            if (_record) {
                _interactions.add(elem);
                
                _record = false;
            }
        }
        
        @Override
        public void accept(Run elem) {
            Protocol protocol=RunUtil.getInnerProtocol(elem.getEnclosingProtocol(),
                            elem.getProtocolReference()); //elem.getProtocol();
            
            if (protocol != null) {
                protocol.visit(this);
            }
        }
    }
    
}
