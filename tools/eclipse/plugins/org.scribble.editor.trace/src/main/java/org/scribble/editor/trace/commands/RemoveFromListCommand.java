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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class RemoveFromListCommand<T> extends AbstractCommand {

	private java.util.List<T> _list;
	private T _value;
	private int _index;
	
	public RemoveFromListCommand(Object focus, java.util.List<T> list, T value,
									Runnable postChangeHook, Runnable postUndoHook) {
		super("RemoveFromList", focus, true, postChangeHook, postUndoHook);
		
		_list = list;
		_value = value;
		_index = _list.indexOf(_value);
	}

	public RemoveFromListCommand(Object focus, java.util.List<T> list, int pos,
			Runnable postChangeHook, Runnable postUndoHook) {
		super("RemoveFromList", focus, true, postChangeHook, postUndoHook);

		_list = list;
		_value = _list.get(pos);
		_index = pos;
	}

	@Override
	public IStatus execute(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		startProcessing();
		if (_index != -1) {
			_list.remove(_index);
		}
		callPostChangeHook();
		finishProcessing();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		startProcessing();
		if (_index != -1) {
			_list.remove(_index);
		}
		callPostChangeHook();
		finishProcessing();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		startProcessing();
		if (_index != -1) {
			_list.add(_index, _value);
		}
		callPostUndoHook();
		finishProcessing();
		return Status.OK_STATUS;
	}

}
