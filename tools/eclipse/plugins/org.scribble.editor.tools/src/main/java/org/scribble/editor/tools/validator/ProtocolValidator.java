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
package org.scribble.editor.tools.validator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.wst.validation.ValidationResult;
//import org.eclipse.wst.validation.ValidationState;
import org.scribble.editor.tools.logger.EclipseScribbleLogger;
import org.scribble.editor.tools.osgi.Activator;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;
import org.scribble.common.resources.InputStreamResource;
import org.scribble.common.resources.Resource;
import org.scribble.common.resources.ResourceLocator;

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
    public void validateResource(final IResource res) {
        EclipseScribbleLogger logger=
                new EclipseScribbleLogger((IFile)res);
        
        try {
            InputStreamResource isr = new InputStreamResource(res.getProjectRelativePath().toPortableString(),
            					((IFile)res).getContents());
            
            // Create a locator based on the Eclipse project root
            ResourceLocator locator=new ResourceLocator() {

				public Resource getResource(String name) {
					String filename=name.replace('.', java.io.File.separatorChar)+".scr";
					
		            IFile file=res.getProject().getFile(filename);
		            
		            if (file != null) {
		            	try {
		            		return (new InputStreamResource(filename, file.getContents()));
		            	} catch (Exception e) {
		            		e.printStackTrace();
		            	}
		            }
		            
					return null;
				}
            };
            
            ProtocolParser pp=new ProtocolParser();
            
            ProtocolModuleLoader loader=new ProtocolModuleLoader(pp, locator, logger);
            
            pp.parse(isr, loader, logger);
            
        } catch (Exception e) {
            Activator.logError("Failed to record validation issue on resource '"+res+"'", e);
        } finally {
            logger.finished();
        }
    }
}
