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
package org.scribble.editor.tools.logger;

import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.scribble.model.ModelObject;
import org.scribble.logging.IssueLogger;

/**
 * The Eclipse implementation of the journal.
 *
 */
public class EclipseIssueLogger implements IssueLogger {
    
    private static final Logger LOG=Logger.getLogger(EclipseIssueLogger.class.getName());

    private IFile _file=null;
    private boolean _finished=false;
    private boolean _errorOccurred=false;
    private java.util.Vector<ReportEntry> _entries=new java.util.Vector<ReportEntry>();
    private java.util.Vector<ReportEntry> _reported=new java.util.Vector<ReportEntry>();
    
    /**
     * The constructor.
     * 
     * @param file The file
     */
    public EclipseIssueLogger(IFile file) {
        _file = file;
    }

    /**
     * {@inheritDoc}
     */
    public void error(String issue, java.util.Map<String,Object> props) {
        reportIssue(issue, ReportEntry.ERROR_TYPE, props);
        _errorOccurred = true;
    }

    /**
     * {@inheritDoc}
     */
    public void error(String issue, ModelObject mobj) {
        reportIssue(issue, ReportEntry.ERROR_TYPE, mobj.getProperties());
        _errorOccurred = true;
    }

    /**
     * Has an error occurred.
     * 
     * @return Whether an error has occurred
     */
    public boolean hasErrorOccurred() {
        return (_errorOccurred);
    }
    
    /**
     * {@inheritDoc}
     */
    public void info(String issue, java.util.Map<String,Object> props) {
        reportIssue(issue, ReportEntry.INFORMATION_TYPE, props);
    }

    /**
     * {@inheritDoc}
     */
    public void info(String issue, ModelObject mobj) {
        reportIssue(issue, ReportEntry.INFORMATION_TYPE, mobj.getProperties());
    }

    /**
     * {@inheritDoc}
     */
    public void warning(String issue, java.util.Map<String,Object> props) {
        reportIssue(issue, ReportEntry.WARNING_TYPE, props);
    }
    
    /**
     * {@inheritDoc}
     */
    public void warning(String issue, ModelObject mobj) {
        reportIssue(issue, ReportEntry.WARNING_TYPE, mobj.getProperties());
    }
    
    /**
     * This method reports an issue.
     * 
     * @param issue The issue
     * @param issueType The issue type
     * @param props The properties
     */
    protected void reportIssue(String issue, int issueType, java.util.Map<String,Object> props) {
        
        if (_file != null) {
    
            synchronized (_entries) {
                _entries.add(new ReportEntry(issue, issueType, props));
            }
            
            if (_finished) {
                // Publish immediately
                finished();
            }
        }
    }            
    
    /**
     * {@inheritDoc}
     */
    public void finished() {
        org.eclipse.swt.widgets.Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                
                if (_file != null && _file.exists()) {
                    
                    // Clear current markers
                    try {
                        synchronized (_entries) {
                            
                            if (!_finished) {
                                _file.deleteMarkers(ScribbleMarker.SCRIBBLE_PROBLEM, true,
                                        IFile.DEPTH_INFINITE);
                                _finished = true;
                            }
                        
                            // Update the markers
                            for (int i=0; i < _entries.size(); i++) {
                                ReportEntry re=(ReportEntry)_entries.get(i);
                                
                                if (!_reported.contains(re)) {
                                    createMarker(re.getIssue(), re.getType(),
                                        re.getProperties());
                                    
                                    _reported.add(re);
                                }
                            }
                            
                            _entries.clear();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
    /**
     * This method creates a marker.
     * 
     * @param mesg The message
     * @param type The type
     * @param props The properties
     */
    protected void createMarker(String mesg, int type,
                    java.util.Map<String,Object> props) {
                    
        // Create marker for message
        try {
            IMarker marker=_file.createMarker(ScribbleMarker.SCRIBBLE_PROBLEM);
            
            // Initialize the attributes on the marker
            marker.setAttribute(IMarker.MESSAGE, mesg);
            
            if (type == ReportEntry.ERROR_TYPE) {
                marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
            } else if (type == ReportEntry.WARNING_TYPE) {
                marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
            } else if (type == ReportEntry.INFORMATION_TYPE) {
                marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
            }
            
            derivePosition(_file, marker, props);

        } catch (Exception e) {
            
            // TODO: report error
            e.printStackTrace();
        }
    }
    
    /**
     * This method derives the position.
     * 
     * @param file The file
     * @param marker The marker
     * @param props The properties
     * @throws Exception Failed to derive position
     */
    protected void derivePosition(IFile file, IMarker marker, java.util.Map<String,Object> props)
                            throws Exception {
        int endMarkerAfter=-1;
        String contents=null;
        
        if (props == null) {
            LOG.severe("Unable to derive position associated with marker as not properties provided");
            return;
        }
        
        if (props.containsKey(ModelObject.START_POSITION)) {
            marker.setAttribute(IMarker.CHAR_START, (Integer)props.get(ModelObject.START_POSITION));
            
            if (props.containsKey(ModelObject.END_POSITION)) {
                marker.setAttribute(IMarker.CHAR_END, (Integer)props.get(ModelObject.END_POSITION));
            } else {
                endMarkerAfter = (Integer)props.get(ModelObject.START_POSITION);
            }
        } else if (props.containsKey(ModelObject.START_LINE)) {
            int pos=-1;
            
            contents = getContents(file);
            
            if (contents != null) {
                pos = 0;
                
                int curline=1;
                int line=(Integer)props.get(ModelObject.START_LINE);
                
                while (curline < line) {
                    // Find next end of line
                    int nextPos=contents.indexOf('\n', pos);
                    
                    curline++;
                    pos = nextPos+1;
                }
                
                if (props.containsKey(ModelObject.START_COLUMN)) {
                    pos += (Integer)props.get(ModelObject.START_COLUMN);
                }
            }
            
            marker.setAttribute(IMarker.CHAR_START, pos);

            if (props.containsKey(ModelObject.END_LINE)) {
                if (contents != null) {
                    pos = 0;
                    
                    int curline=1;
                    int line=(Integer)props.get(ModelObject.END_LINE);
                    
                    while (curline < line) {
                        // Find next end of line
                        int nextPos=contents.indexOf('\n', pos);
                        
                        curline++;
                        pos = nextPos+1;
                    }
                    
                    if (props.containsKey(ModelObject.END_COLUMN)) {
                        pos += (Integer)props.get(ModelObject.END_COLUMN);
                    }
                    
                    marker.setAttribute(IMarker.CHAR_END, pos);
                }
            } else {
                endMarkerAfter = pos;
            }
        }
        
        if (endMarkerAfter != -1) {
            if (contents == null) {
                contents = getContents(file);
            }
            
            // Find next whitespace after this position
            int nextPos=endMarkerAfter;
            
            while (nextPos < contents.length() && !Character.isWhitespace(contents.charAt(nextPos))) {
                nextPos++;
            }
            
            if (nextPos != -1) {
                marker.setAttribute(IMarker.CHAR_END, nextPos);
            }
        }
    }
    
    /**
     * This method returns the file contents.
     * 
     * @param file The file
     * @return The contents
     * @throws Exception Failed to get contents
     */
    protected String getContents(IFile file) throws Exception {
        java.io.InputStream is=file.getContents();
        byte[] b=new byte[is.available()];
        is.read(b);
        is.close();
        
        return (new String(b));
    }

    /**
     * This is a simple data container class to hold the
     * information reported during validation.
     *
     */
    public class ReportEntry {
        
        /**
         * Error type.
         */
        public static final int ERROR_TYPE=0;

        /**
         * Warning type.
         */
        public static final int WARNING_TYPE=1;

        /**
         * Information type.
         */
        public static final int INFORMATION_TYPE=2;
        
        private String _issue=null;
        private int _type=0;
        private java.util.Map<String, Object> _properties=null;

        /**
         * Constructor.
         * 
         * @param issue The issue
         * @param type The type
         * @param props The properties
         */
        public ReportEntry(String issue, int type,
                    java.util.Map<String, Object> props) {
            _issue = issue;
            _type = type;
            _properties = props;
        }
        
        /**
         * This method returns the issue.
         * 
         * @return The issue
         */
        public String getIssue() {
            return (_issue);
        }
        
        /**
         * This method returns the type.
         * 
         * @return The type
         */
        public int getType() {
            return (_type);
        }
        
        /**
         * This method returns the properties.
         * 
         * @return The properties
         */
        public java.util.Map<String,Object> getProperties() {
            return (_properties);
        }
    }
}
