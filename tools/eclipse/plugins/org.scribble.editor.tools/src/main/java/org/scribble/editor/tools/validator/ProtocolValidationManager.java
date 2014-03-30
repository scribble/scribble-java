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
import org.scribble.editor.tools.logger.EclipseIssueLogger;
import org.scribble.editor.tools.osgi.Activator;
import org.scribble.model.Module;
import org.scribble.parser.ProtocolModuleLoader;
import org.scribble.parser.ProtocolParser;
import org.scribble.logging.ConsoleIssueLogger;
import org.scribble.context.DefaultModuleContext;
import org.scribble.resources.InputStreamResource;
import org.scribble.resources.Resource;
import org.scribble.resources.ResourceLocator;

/**
 * Protocol validator.
 *
 */
public class ProtocolValidationManager {
                //extends org.eclipse.wst.validation.AbstractValidator {

    /**
     * Default constructor.
     */
    public ProtocolValidationManager() {
    }

    /**
     * This method validates the resource.
     * 
     * @param res The resource
     */
    public void validateResource(final IResource res) {
        EclipseIssueLogger logger=
                new EclipseIssueLogger((IFile)res);
        
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
            
            // Use console logger, to 'ignore' parser errors, as these will be detected and
            // reported by the XText generated Scribble editor. However the validation errors
            // should be reported via the Eclipse based logger
            Module module=pp.parse(isr, loader, new ConsoleIssueLogger());
            
            if (module != null) {            	
            	// Validate
                org.scribble.validation.ProtocolValidator pv=new org.scribble.validation.ProtocolValidator();
                
                DefaultModuleContext context=new DefaultModuleContext(isr, module, loader);
                
                pv.validate(context, module, logger);
            }
            
        } catch (Exception e) {
            Activator.logError("Failed to record validation issue on resource '"+res+"'", e);
        } finally {
            logger.finished();
        }
    }
}
