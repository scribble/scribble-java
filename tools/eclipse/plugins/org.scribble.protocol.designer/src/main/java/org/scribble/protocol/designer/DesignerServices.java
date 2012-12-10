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
package org.scribble.protocol.designer;

import org.scribble.protocol.export.ProtocolExportManager;
import org.scribble.protocol.monitor.ProtocolMonitor;
import org.scribble.protocol.parser.ProtocolParserManager;
import org.scribble.protocol.projection.ProtocolProjector;
import org.scribble.protocol.validation.ProtocolValidationManager;

/**
 * This class provides a manager for accessing services used
 * by the designer.
 *
 */
public final class DesignerServices {

    /**
     * Protocol file extension.
     */
    public static final String PROTOCOL_FILE_EXTENSION = "spr";

    private static ProtocolValidationManager validationManager=null;
    private static ProtocolParserManager parserManager=null;
    private static ProtocolProjector protocolProjector=null;
    private static ProtocolMonitor protocolMonitor=null;
    private static ProtocolExportManager protocolExportManager=null;

    /**
     * Private constructor.
     */
    private DesignerServices() {
    }
    
    /**
     * This method returns the validation manager.
     * 
     * @return The validation manager
     */
    public static ProtocolValidationManager getValidationManager() {
        return (validationManager);
    }
    
    /**
     * This method sets the validation manager.
     * 
     * @param vm The validation manager
     */
    public static void setProtocolValidationManager(ProtocolValidationManager vm) {
        validationManager = vm;
    }
    
    /**
     * This method returns the parser manager.
     * 
     * @return The parser manager
     */
    public static ProtocolParserManager getParserManager() {
        return (parserManager);
    }
    
    /**
     * This method sets the parser manager.
     * 
     * @param pm The parser manager
     */
    public static void setProtocolParserManager(ProtocolParserManager pm) {
        parserManager = pm;
    }
    
    /**
     * This method returns the protocol monitor.
     * 
     * @return The protocol monitor
     */
    public static ProtocolMonitor getProtocolMonitor() {
        return (protocolMonitor);
    }
    
    /**
     * This method sets the protocol monitor.
     * 
     * @param pm The protocol monitor
     */
    public static void setProtocolMonitor(ProtocolMonitor pm) {
        protocolMonitor = pm;
    }
    
    /**
     * This method returns the projector.
     * 
     * @return The projector
     */
    public static ProtocolProjector getProtocolProjector() {
        return (protocolProjector);
    }
    
    /**
     * This method sets the projector.
     * 
     * @param projector The projector
     */
    public static void setProtocolProjector(ProtocolProjector projector) {
        protocolProjector = projector;
    }    
    
    /**
     * This method returns the export manager.
     * 
     * @return The export manager
     */
    public static ProtocolExportManager getProtocolExportManager() {
        return (protocolExportManager);
    }
    
    /**
     * This method sets the export manager.
     * 
     * @param pem The export manager
     */
    public static void setProtocolExportManager(ProtocolExportManager pem) {
        protocolExportManager = pem;
    }    
}
