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
package org.scribble.model.global;

import org.scribble.model.ImportDecl;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.global.GChoice;

/**
 * This class represents the default visitor which can be used
 * to traverse a model.
 */
public class DefaultGVisitor implements GVisitor {
    
    /**
     * {@inheritDoc}
     */
    public boolean start(GBlock elem) {
    	return (true);
    }
    
    /**
     * {@inheritDoc}
     */
    public void end(GBlock elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean start(GChoice elem) {
    	return (true);
    }
    
    /**
     * {@inheritDoc}
     */
    public void end(GChoice elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean start(GParallel elem) {
    	return (true);
    }
    
    /**
     * {@inheritDoc}
     */
    public void end(GParallel elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean start(GProtocolDefinition elem) {
    	return (true);
    }
    
    /**
     * {@inheritDoc}
     */
    public void end(GProtocolDefinition elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean start(GRecursion elem) {
    	return (true);
    }
    
    /**
     * {@inheritDoc}
     */
    public void end(GRecursion elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean start(GInterruptible elem) {
    	return (true);
    }
    
    /**
     * {@inheritDoc}
     */
    public void end(GInterruptible elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void accept(GProtocolInstance elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void accept(GMessageTransfer elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void accept(GContinue elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void accept(GDo elem) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void accept(GCustomActivity elem) {
    }

    /**
     * {@inheritDoc}
     */
	public void accept(PayloadTypeDecl elem) {
	}

    /**
     * {@inheritDoc}
     */
	public void accept(ImportDecl elem) {
	}
    
}
