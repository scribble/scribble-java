/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-11, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.scribble.protocol.export.text;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.ModelObject;

/**
 * This interface represents a rule used to provide
 * protocol model object's with text export capabilities.
 *
 */
public interface TextProtocolExporterRule {

    /**
     * This method determines whether the text protocol
     * exporter rule is associated with the supplied
     * model object.
     * 
     * @param obj The model object
     * @return Whether the rule supports the supplied object
     */
    public boolean isSupported(ModelObject obj);
	
    /**
     * This method exports the supplied model object to export.
     * If errors occur, then they will be reported to
     * the journal, and a null will be returned.
     * 
     * @param obj The model object
     * @param journal The journal
     * @return The text, or null if cannot export
     */
    public String getText(ModelObject obj, Journal journal);
	
}
