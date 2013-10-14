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
package org.scribble.protocol.designer.editor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.scribble.protocol.designer.osgi.Activator;

/**
 * 
 */
public class ScribbleDocumentSetupParticipant implements IDocumentSetupParticipant {
    
    /**
     */
    public ScribbleDocumentSetupParticipant() {
    }

    /**
     * {@inheritDoc}
     */
    public void setup(IDocument document) {
        if (document instanceof IDocumentExtension3) {
            IDocumentExtension3 extension3= (IDocumentExtension3) document;
            IDocumentPartitioner partitioner= new FastPartitioner(Activator.getDefault().getScribblePartitionScanner(), ScribblePartitionScanner.JAVA_PARTITION_TYPES);
            extension3.setDocumentPartitioner(Activator.SCRIBBLE_PARTITIONING, partitioner);
            partitioner.connect(document);
        }
    }
}
