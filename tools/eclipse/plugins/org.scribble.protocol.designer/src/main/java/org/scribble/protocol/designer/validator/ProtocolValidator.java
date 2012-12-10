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
package org.scribble.protocol.designer.validator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.wst.validation.ValidationResult;
//import org.eclipse.wst.validation.ValidationState;
import org.scribble.common.resource.FileContent;
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.designer.DesignerServices;
import org.scribble.protocol.designer.logger.EclipseScribbleLogger;
import org.scribble.protocol.designer.osgi.Activator;
import org.scribble.protocol.model.ProtocolModel;

/**
 * Protocol validator.
 *
 */
public class ProtocolValidator {
                //extends org.eclipse.wst.validation.AbstractValidator {

    /**
     * Default constructor.
     */
    public ProtocolValidator() {
    }

    /*
    public ValidationResult validate(IResource resource, int kind, ValidationState state, IProgressMonitor monitor) {
        ValidationResult result=super.validate(resource, kind, state, monitor);

        // TODO: Not currently used. Instead a resource change listener is used to automatically
        // trigger validation. However if this turns out to be inefficient, or if the WST validator
        // can be automatically enabled for protocol definitions, then possibly can use the
        // standard WST approach
        System.out.println("VALIDATE:" + resource.getFullPath());
        return (result);
    }
    */
    
    /**
     * This method validates the resource.
     * 
     * @param res The resource
     */
    public void validateResource(IResource res) {
        
        try {
            EclipseScribbleLogger logger=
                    new EclipseScribbleLogger((IFile)res);
            
            FileContent content=new FileContent(((IFile)res).getRawLocation().toFile());
            
            DefaultProtocolContext context=new DefaultProtocolContext();
            context.setProtocolParserManager(DesignerServices.getParserManager());
            //context.setProtocolValidationManager(DesignerServices.getValidationManager());
            //context.setProtocolProjector(DesignerServices.getProtocolProjector());
            
            ProtocolModel model=
                DesignerServices.getParserManager().parse(null, content, logger);
            
            // TODO: Check if error occurred during parsing
            // possibly by using a logger proxy that counts
            // errors logged
            if (model != null && !logger.hasErrorOccurred()) {
                DesignerServices.getValidationManager().validate(context, model,
                                logger);
            }
            
            logger.finished();
            
        } catch (Exception e) {
            Activator.logError("Failed to record validation issue on resource '"+res+"'", e);
        }
    }
}
