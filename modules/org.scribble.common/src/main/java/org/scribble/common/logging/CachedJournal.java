/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.common.logging;

import java.util.Map;

/**
 * This class provides an implementation of the journal that
 * caches reported issues.
 *
 */
public class CachedJournal implements Journal {
    
    private java.util.List<IssueDetails> _issues=new java.util.Vector<IssueDetails>();
    private boolean _errors=false;
    private boolean _warnings=false;

    /**
     * This method records an error issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void error(String issue, Map<String, Object> props) {
        _issues.add(new IssueDetails(IssueType.Error, issue, props));
        _errors = true;
    }

    /**
     * This method records an information issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void info(String issue, Map<String, Object> props) {
        _issues.add(new IssueDetails(IssueType.Info, issue, props));
    }

    /**
     * This method records a warning issue.
     * 
     * @param issue The issue text
     * @param props The optional properties associated with the issue
     */
    public void warning(String issue, Map<String, Object> props) {
        _issues.add(new IssueDetails(IssueType.Warning, issue, props));
        _warnings = true;
    }
    
    /**
     * This method returns the list of issues that have been reported to this
     * journal.
     * 
     * @return The list of issues
     */
    public java.util.List<IssueDetails> getIssues() {
        return (_issues);
    }
    
    /**
     * This method determines whether any errors have been reported.
     * 
     * @return Whether errors have been reported
     */
    public boolean hasErrors() {
        return (_errors);
    }
    
    /**
     * This method determines whether any warnings have been reported.
     * 
     * @return Whether warnings have been reported
     */
    public boolean hasWarnings() {
        return (_warnings);
    }
    
    /**
     * This method applies any cached issues to the supplied logger.
     * 
     * @param logger The logger
     */
    public void apply(Journal logger) {
    	
    }
    
    /**
     * This method applies any cached issues to the supplied logger.
     * 
     * @param prefix The optional message prefix
     * @param logger The logger
     */
    public void apply(Journal logger, String prefix) {
    	
    	if (prefix == null) {
    		prefix = "";
    	}
    	
        for (IssueDetails id : _issues) {
            if (id.getIssueType() == IssueType.Error) {
                logger.error(prefix+id.getMessage(), id.getProperties());
            } else if (id.getIssueType() == IssueType.Info) {
                logger.info(prefix+id.getMessage(), id.getProperties());
            } else if (id.getIssueType() == IssueType.Warning) {
                logger.warning(prefix+id.getMessage(), id.getProperties());
            }
        }
    }

    /**
     * This enum represents the type of issue.
     *
     */
    public enum IssueType {
        /**
         * Error issue type.
         */
        Error,
        /**
         * Information issue type.
         */
        Info,
        /**
         * Warning issue type.
         */
        Warning
    }

    /**
     * This class represents the issue details.
     *
     */
    public class IssueDetails {
        
        private IssueType _type=IssueType.Info;
        private String _message=null;
        private Map<String,Object> _properties=null;

        /**
         * Constructor for the issue details.
         * 
         * @param type The issue type
         * @param mesg The message
         * @param props The properties
         */
        public IssueDetails(IssueType type, String mesg,
                        Map<String, Object> props) {
            _type = type;
            _message = mesg;
            _properties = props;
        }
        
        /**
         * This method returns the issue type.
         * 
         * @return The issue type
         */
        public IssueType getIssueType() {
            return (_type);
        }
        
        /**
         * This method returns the message.
         * 
         * @return The message
         */
        public String getMessage() {
            return (_message);
        }
        
        /**
         * This method returns the properties.
         * 
         * @return The properties
         */
        public Map<String,Object> getProperties() {
            return (_properties);
        }
    }
}
