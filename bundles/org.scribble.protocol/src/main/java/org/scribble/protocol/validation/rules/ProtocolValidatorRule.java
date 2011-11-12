/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.validation.rules;

import java.text.MessageFormat;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.ProtocolDefinitions;
import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.DefaultVisitor;
import org.scribble.protocol.model.DirectedChoice;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.Introduces;
import org.scribble.protocol.model.Parameter;
import org.scribble.protocol.model.ParameterDefinition;
import org.scribble.protocol.model.RecBlock;
import org.scribble.protocol.model.Repeat;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.Run;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.validation.ProtocolComponentValidatorRule;
import org.scribble.protocol.validation.ProtocolValidatorContext;

/**
 * This class provides the validation rule for the Protocol
 * model component.
 *
 */
public class ProtocolValidatorRule implements ProtocolComponentValidatorRule {

    /**
     * This method determines whether the rule is applicable
     * for the supplied model object.
     * 
     * @param obj The object to check
     * @return Whether the rule can be used to validate the
     *                 supplied model object
     */
    public boolean isSupported(ModelObject obj) {
        return (obj.getClass() == Protocol.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public void validate(ProtocolValidatorContext pvc, ModelObject obj,
                    Journal logger) {
        Protocol elem=(Protocol)obj;
        
        // If non-located protocol, then check for connectedness
        if (elem.getLocatedRole() == null) {
            elem.visit(new ConnectednessVisitor(elem, logger));
        }
        
        // Check protocol name
        if (elem.getName() != null) {
            String name=elem.getName().toLowerCase();
            
            boolean reservedWord=false;
            
            for (String reserved : ProtocolDefinitions.RESERVED_WORDS) {
                if (reserved.equals(name)) {
                    reservedWord = true;
                    break;
                }
            }
            
            if (reservedWord) {
                logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                        "org.scribble.protocol.Messages").getString("_CANNOT_USE_RESERVED_WORD"),
                        elem.getName()), elem.getProperties());
           }
        }
    }
    
    /**
     * This class checks for connectedness between the visited components.
     *
     */
    protected class ConnectednessVisitor extends DefaultVisitor {
        
        private Protocol _protocol=null;
        private Journal _logger=null;
        private java.util.Stack<java.util.Set<Role>> _roleStack=
                            new java.util.Stack<java.util.Set<Role>>();
        private java.util.Stack<java.util.Stack<java.util.Set<Role>>> _saveStack=
                new java.util.Stack<java.util.Stack<java.util.Set<Role>>>();
        
        /**
         * Constructor for the connectness visitor.
         * 
         * @param p The protocol to traverse
         * @param logger The journal
         */
        public ConnectednessVisitor(Protocol p, Journal logger) {
            _protocol = p;
            _logger = logger;
        }
        
        /**
         * This method determines whether the supplied role is already known.
         * 
         * @param r The role
         * @return Whether known
         */
        protected boolean isRoleKnown(Role r) {
            boolean ret=false;
            
            for (java.util.Set<Role> roles : _roleStack) {
                if (roles.contains(r)) {
                    ret = true;
                    break;
                }
            }
            
            return (ret);
        }
        
        /**
         * This method validates the supplied activity with the supplied
         * role to determine if they are connected.
         * 
         * @param act The activity
         * @param r The role
         */
        protected void validate(Activity act, Role r) {
            if (r != null && !isRoleKnown(r)) {
                _logger.error(MessageFormat.format(
                        java.util.PropertyResourceBundle.getBundle(
                        "org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
                        r.getName()), act.getProperties());
            }
        }
        
        /**
         * This method adds the role.
         * 
         * @param r The role
         */
        protected void addRole(Role r) {
            if (!isRoleKnown(r)) {
                java.util.Set<Role> roles=_roleStack.peek();
    
                roles.add(r);
            }
        }
        
        /**
         * This method pushes the role stack.
         */
        protected void push() {
            if (_roleStack == null) {
                _roleStack = new java.util.Stack<java.util.Set<Role>>();
            }
            _roleStack.push(new java.util.HashSet<Role>());
        }
        
        /**
         * This method pops the role stack.
         */
        protected void pop() {
            _roleStack.pop();
        }

        /**
         * This method saves the current stack.
         */
        protected void saveStack() {
            _saveStack.push(_roleStack);
            _roleStack = null;
            push();
        }
        
        /**
         * This method restores the stack.
         */
        protected void restoreStack() {
            _roleStack = _saveStack.pop();
        }

        @Override
        public boolean start(Block elem) {
            push();
            return true;
        }

        @Override
        public void end(Block elem) {
            pop();
        }

        @Override
        public boolean start(Choice elem) {
            validate(elem, elem.getRole());
            saveStack();
            addRole(elem.getRole());
            return (true);
        }

        @Override
        public void end(Choice elem) {
            restoreStack();
        }

        @Override
        public boolean start(DirectedChoice elem) {
            // Validate that the from role is already known, but
            // add the from and to roles, so that the to roles
            // can subsequently be used for validation
            validate(elem, elem.getFromRole());

            saveStack();

            addRole(elem.getFromRole());
            for (Role r : elem.getToRoles()) {
                addRole(r);
            }
            return (true);
        }

        @Override
        public void end(DirectedChoice elem) {
            restoreStack();
        }

        @Override
        public boolean start(Protocol elem) {
            if (elem == _protocol) {
                push();
                for (ParameterDefinition pd : elem.getParameterDefinitions()) {
                    if (pd.getType() == null) {
                        addRole(new Role(pd.getName()));
                    }
                }
                return (true);
            } else {
                return (false);
            }
        }

        @Override
        public void end(Protocol elem) {
            if (elem == _protocol) {
                pop();
            }
        }

        @Override
        public boolean start(Repeat elem) {
            for (Role r : elem.getRoles()) {
                validate(elem, r);
            }
            saveStack();
            for (Role r : elem.getRoles()) {
                addRole(r);
            }
            return (true);
        }

        @Override
        public void end(Repeat elem) {
            restoreStack();
        }

        @Override
        public boolean start(RecBlock elem) {
            push();
            return (true);
        }

        @Override
        public void end(RecBlock elem) {
            pop();
        }

        @Override
        public void accept(Run elem) {
            for (Parameter p : elem.getParameters()) {
                validate(elem, new Role(p.getName()));
            }
        }

        @Override
        public void accept(Interaction elem) {
            // Validate that the from role is already known, but
            // add the from and to roles, so that the to roles
            // can subsequently be used for validation
            validate(elem, elem.getFromRole());
            
            addRole(elem.getFromRole());
            for (Role r : elem.getToRoles()) {
                addRole(r);
            }
        }

        @Override
        public void accept(Introduces elem) {
            validate(elem, elem.getIntroducer());
            addRole(elem.getIntroducer());
            for (Role r : elem.getIntroducedRoles()) {
                addRole(r);
            }
        }
        
    }
}
