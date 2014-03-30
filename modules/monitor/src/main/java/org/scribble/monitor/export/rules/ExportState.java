/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.monitor.export.rules;

/**
 * This class represents state information associated with the exported
 * protocols.
 *
 */
public class ExportState {
	
	private java.util.Stack<StateInformation> _state=new java.util.Stack<StateInformation>();

	/**
	 * This method pushes state information to the stack.
	 */
	public void push() {
		_state.push(new StateInformation());
	}
	
	/**
	 * This method pops state information from the stack.
	 */
	public void pop() {
		_state.pop();
	}
	
	/**
	 * This method registers the label against the index.
	 * 
	 * @param label The label
	 * @param index The index
	 */
	public void registerLabelIndex(String label, int index) {
		_state.peek().registerLabelIndex(label, index);
	}
	
	/**
	 * This method unregisters the label.
	 * 
	 * @param label The label
	 */
	public void unregisterLabel(String label) {
		_state.peek().unregisterLabel(label);
	}
	
	/**
	 * This method returns the index associated with the
	 * supplied label.
	 * 
	 * @param label The label
	 * @return The index, or -1 if not found
	 */
	public int getLabelIndex(String label) {
		int ret=-1;
		
		for (int i=_state.size()-1; ret == -1 && i >= 0; i--) {
			ret = _state.get(i).getLabelIndex(label);
		}
		
		return (ret);
	}
	
	/**
	 * This class represents the state information on the stack.
	 *
	 */
	public static class StateInformation {
		
		private java.util.Map<String,Integer> _labelIndexes=new java.util.HashMap<String, Integer>();
		
		/**
		 * This method registers the label against the index.
		 * 
		 * @param label The label
		 * @param index The index
		 */
		public void registerLabelIndex(String label, int index) {
			_labelIndexes.put(label, index);
		}
		
		/**
		 * This method unregisters the label.
		 * 
		 * @param label The label
		 */
		public void unregisterLabel(String label) {
			_labelIndexes.remove(label);
		}
		
		/**
		 * This method returns the index associated with the
		 * supplied label.
		 * 
		 * @param label The label
		 * @return The index, or -1 if not found
		 */
		public int getLabelIndex(String label) {
			if (_labelIndexes.containsKey(label)) {
				return (_labelIndexes.get(label));
			}
			return (-1);
		}
	}
}
