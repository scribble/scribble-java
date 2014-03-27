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
package org.scribble.editor.tools.osgi;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.scribble.editor.tools.validator.ProtocolValidationManager;
import org.scribble.parser.ProtocolDefinitions;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {
    
    private static Logger logger = Logger.getLogger("org.scribble.eclipse.tools");

    private ProtocolValidationManager _validator=new ProtocolValidationManager();

    /**
     * Plugin id.
     */
    public static final String PLUGIN_ID = "org.scribble.editor.tools";

    // The shared instance
    private static Activator plugin;
    
    /**
     * The constructor.
     */
    public Activator() {
    }

    /**
     * {@inheritDoc}
     */
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        
        // Register resource change listener
        IResourceChangeListener rcl=new IResourceChangeListener() {
            public void resourceChanged(IResourceChangeEvent evt) {       
                try {
                    evt.getDelta().accept(new IResourceDeltaVisitor() {                        
                        public boolean visit(IResourceDelta delta) {
                            boolean ret=true;
                            IResource res = delta.getResource();                           
                            // Determine if the change is relevant
                            if (isChangeRelevant(res, delta)) {
                                // Validate the resource
                                _validator.validateResource(res);
                            }
                            return (ret);
                        }
                     });
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to process resource change event", e);
                }
            }
        };
        
        // Register the resource change listener
        ResourcesPlugin.getWorkspace().addResourceChangeListener(rcl,
                IResourceChangeEvent.POST_CHANGE);        
    }
    
    /**
     * This method determines if the change is relevant.
     * 
     * @param res The resource
     * @param delta The delta
     * @return Whether the change is relevant
     */
    protected boolean isChangeRelevant(IResource res, IResourceDelta delta) {
        return (res instanceof IFile
                && ((IFile)res).getFileExtension().equals(ProtocolDefinitions.PROTOCOL_EXTENSION)
                && (((delta.getFlags() & IResourceDelta.CONTENT) != 0)
                        || delta.getKind() == IResourceDelta.ADDED));
    }

    /**
     * {@inheritDoc}
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * This method logs an error against the plugin.
     * 
     * @param mesg The error message
     * @param t The optional exception
     */
    public static void logError(String mesg, Throwable t) {
        
        if (getDefault() != null) {
            Status status=new Status(IStatus.ERROR,
                    PLUGIN_ID, 0, mesg, t);
            
            getDefault().getLog().log(status);
        }
        
        logger.severe("LOG ERROR: "+mesg+(t == null ? "" : ": "+t));
    }
    
}
