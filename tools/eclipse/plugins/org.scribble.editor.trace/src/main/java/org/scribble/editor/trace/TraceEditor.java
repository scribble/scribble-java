/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.editor.trace;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.operations.UndoRedoActionGroup;
import org.scribble.editor.trace.commands.AbstractCommand;
import org.scribble.editor.trace.osgi.TraceEditorActivator;
import org.scribble.trace.model.Trace;

/**
 * Trace editor.
 */
public class TraceEditor extends FormEditor {
	
	private Trace _trace=null;
	
	protected static IUndoContext UNDO_CONTEXT=new UndoContext();
	
	/**
	 *  
	 */
	public TraceEditor() {
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormEditor#createToolkit(org.eclipse.swt.widgets.Display)
	 */
	protected FormToolkit createToolkit(Display display) {
		// Create a toolkit that shares colors between editors.
		return new FormToolkit(TraceEditorActivator.getDefault().getFormColors(
				display));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	protected void addPages() {
		try {
			addPage(new MainPage(this));
			addPage(new StepsPage(this));
		} catch (PartInitException e) {
			//
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		
		UndoRedoActionGroup historyActionGroup = new UndoRedoActionGroup(site, UNDO_CONTEXT, true);
	    historyActionGroup.fillActionBars(site.getActionBars());
	    
	    if (input instanceof IFileEditorInput) {
			IFileEditorInput fei=(IFileEditorInput)input;
			
			setPartName(fei.getName());
			
			try {
				java.io.InputStream is=fei.getFile().getContents();
				
				byte[] b=new byte[is.available()];
				
				is.read(b);
				
				is.close();
				
				_trace = org.scribble.trace.util.TraceUtil.deserializeTrace(b);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    
	    // Listen for changes to the operation history, to check if the trace has become dirty
	    OperationHistoryFactory.getOperationHistory().addOperationHistoryListener(new IOperationHistoryListener() {
			public void historyNotification(OperationHistoryEvent arg0) {
				editorDirtyStateChanged();
				firePropertyChange(PROP_DIRTY);
			}	    	
	    });
	}
	
	/**
	 * This method returns the trace.
	 * 
	 * @return The trace
	 */
	protected Trace getTrace() {
		return (_trace);
	}
	
	public boolean isDirty() {
		boolean ret=false;
//System.out.println("CHECK IF DIRTY: ");		
		
		// Check operation history
		IUndoableOperation[] undoOps=OperationHistoryFactory.getOperationHistory().getUndoHistory(UNDO_CONTEXT);
		
		for (int i=0; !ret && i < undoOps.length; i++) {
			if (undoOps[i] instanceof AbstractCommand) {
				ret = ((AbstractCommand)undoOps[i]).modifiesState();
			}
		}
		
		/*
		for (int i=0; i < undoOps.length; i++) {
System.out.println("            "+undoOps[i]+" class="+undoOps[i].getClass());	
		}
		*/
		
		return (ret);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		OperationHistoryFactory.getOperationHistory().dispose(UNDO_CONTEXT, true, true, true);
		
		firePropertyChange(PROP_DIRTY);		
		
		try {
			byte[] b=org.scribble.trace.util.TraceUtil.serializeTrace(_trace);
			
			if (getEditorInput() instanceof IFileEditorInput) {
				IFileEditorInput input=(IFileEditorInput)getEditorInput();
				
				java.io.InputStream is=new java.io.ByteArrayInputStream(b);
				
				input.getFile().setContents(is, true, true, null);
				
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#doSaveAs()
	 */
	public void doSaveAs() {
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}
}