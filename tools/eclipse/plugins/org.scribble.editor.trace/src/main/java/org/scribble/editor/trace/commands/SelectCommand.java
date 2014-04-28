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

public class SelectCommand<MS,MT,T> extends AbstractCommand {

	private PropertyAccessor<MS,T> _source;
	private PropertyAccessor<MT,T> _target;
	private T _oldValue;
	private T _newValue;
	
	public SelectCommand(Object focus, PropertyAccessor<MS,T> source, PropertyAccessor<MT,T> target) {
		super("Select", focus, false, null, null);
		
		_source = source;
		_target = target;
	}

	@Override
	public IStatus execute(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		_oldValue = _target.getPropertyValue();
		_newValue = _source.getPropertyValue();
		_target.setPropertyValue(_newValue);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		startProcessing();
		_target.setPropertyValue(_newValue);
		_source.setPropertyValue(_newValue);
		finishProcessing();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor arg0, IAdaptable arg1)
			throws ExecutionException {
		startProcessing();
		_target.setPropertyValue(_oldValue);
		_source.setPropertyValue(_oldValue);
		finishProcessing();
		return Status.OK_STATUS;
	}

}
