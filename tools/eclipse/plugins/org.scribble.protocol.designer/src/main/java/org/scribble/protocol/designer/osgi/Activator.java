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
package org.scribble.protocol.designer.osgi;

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
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.scribble.protocol.designer.DesignerServices;
import org.scribble.protocol.designer.editor.ScribblePartitionScanner;
import org.scribble.protocol.designer.editor.lang.ScribbleCodeScanner;
import org.scribble.protocol.designer.editor.util.ScribbleColorProvider;
import org.scribble.protocol.designer.validator.ProtocolValidator;
import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.export.ProtocolExportManagerFactory;
import org.scribble.protocol.export.monitor.MonitorProtocolExporter;
import org.scribble.protocol.export.text.TextProtocolExporter;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.parser.ProtocolParserManagerFactory;
import org.scribble.protocol.validation.ProtocolValidationManager;
import org.scribble.protocol.validation.ProtocolValidationManagerFactory;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {
    
    private static Logger logger = Logger.getLogger("org.scribble.protocol.designer");

    /**
     * Scribble partitioning.
     */
    public final static String SCRIBBLE_PARTITIONING= "__scribble_partitioning";   //$NON-NLS-1$
    
    private ScribblePartitionScanner _fPartitionScanner;
    private ScribbleColorProvider _fColorProvider;
    private ScribbleCodeScanner _fCodeScanner;
    
    private ProtocolValidator _validator=new ProtocolValidator();

    /**
     * Plugin id.
     */
    public static final String PLUGIN_ID = "org.scribble.protocol.designer";

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

        // Initialise the parser
        ProtocolParserManager ppm=ProtocolParserManagerFactory.getParserManager();
        ppm.getParsers().add(new org.scribble.protocol.parser.antlr.ANTLRProtocolParser());
        
        DesignerServices.setProtocolParserManager(ppm);
        
        // Initialise the projector
        org.scribble.protocol.projection.ProtocolProjector pp=
        			new org.scribble.protocol.projection.impl.ProtocolProjectorImpl();
        DesignerServices.setProtocolProjector(pp);
        
        // Initialise the validator
        ProtocolValidationManager pvm=ProtocolValidationManagerFactory.getValidationManager();
        pvm.getValidators().add(new org.scribble.protocol.validation.rules.DefaultProtocolComponentValidator());
        pvm.setProtocolProjector(pp);
        
        DesignerServices.setProtocolValidationManager(pvm);

        // Create the export manager
        ProtocolExportManager em=ProtocolExportManagerFactory.getExportManager();
        em.getExporters().add(new TextProtocolExporter());
        em.getExporters().add(new MonitorProtocolExporter());
        
        DesignerServices.setProtocolExportManager(em);
   
        // Register protocol monitor
        DesignerServices.setProtocolMonitor(new org.scribble.protocol.monitor.DefaultProtocolMonitor());
        
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
                && DesignerServices.PROTOCOL_FILE_EXTENSION.equals(((IFile)res).getFileExtension())
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
    
    /**
     * Return a scanner for creating Java partitions.
     * 
     * @return a scanner for creating Java partitions
     */
    public ScribblePartitionScanner getScribblePartitionScanner() {
        if (_fPartitionScanner == null) {
            _fPartitionScanner= new ScribblePartitionScanner();
        }
        return _fPartitionScanner;
    }
    
    /**
     * Returns the singleton Java code scanner.
     * 
     * @return the singleton Java code scanner
     */
    public RuleBasedScanner getScribbleCodeScanner() {
         if (_fCodeScanner == null) {
            _fCodeScanner= new ScribbleCodeScanner(getScribbleColorProvider());
         }
         return _fCodeScanner;
    }
    
    /**
     * Returns the singleton Java color provider.
     * 
     * @return the singleton Java color provider
     */
    public ScribbleColorProvider getScribbleColorProvider() {
         if (_fColorProvider == null) {
            _fColorProvider= new ScribbleColorProvider();
         }
        return _fColorProvider;
    }
}
