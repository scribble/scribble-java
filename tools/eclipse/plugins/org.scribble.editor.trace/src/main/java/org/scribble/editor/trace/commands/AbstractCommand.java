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
package org.scribble.editor.trace.commands;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;

public abstract class AbstractCommand extends AbstractOperation implements IUndoableOperation {

	private static java.util.Set<Object> _processing=new java.util.HashSet<Object>();
	
	private Object _focus;
	private boolean _modifiesState;
	private Runnable _postChangeHook;
	private Runnable _postUndoHook;
	
	public AbstractCommand(String name, Object focus, boolean modifiesState,
						Runnable postChangeHook, Runnable postUndoHook) {
		super(name);
		
		_focus = focus;
		_modifiesState = modifiesState;
		_postChangeHook = postChangeHook;
		_postUndoHook = postUndoHook;
	}
	
	protected void callPostChangeHook() {
		if (_postChangeHook != null) {
			_postChangeHook.run();
		}
	}

	protected void callPostUndoHook() {
		if (_postUndoHook != null) {
			_postUndoHook.run();
		}
	}

	public boolean canSchedule() {
		return (!_processing.contains(_focus));
	}
	
	public boolean modifiesState() {
		return (_modifiesState);
	}
	
	protected void startProcessing() {
		_processing.add(_focus);		
	}
	
	protected void finishProcessing() {
		_processing.remove(_focus);		
	}
}
