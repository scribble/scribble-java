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
package org.scribble.editor.trace.ext;

import org.scribble.trace.model.MessageTransfer;

/**
 * This class provides a wrapper around a parameters type and value.
 *
 */
public class MessageTransferParameter {

	private MessageTransfer _messageTransfer=null;
	private int _parameterIndex=-1;
	private String _type=null;
	private String _value=null;
	
	/**
	 * The constructor.
	 * 
	 * @param mt The message type
	 * @param parameterIndex The parameter index
	 */
	public MessageTransferParameter(MessageTransfer mt, int parameterIndex) {
		_messageTransfer = mt;
		_parameterIndex = parameterIndex;
	}
	
	/**
	 * The constructor.
	 * 
	 * @param type The type
	 * @param value The value
	 */
	public MessageTransferParameter(String type, String value) {
		_type = type;
		_value = value;
	}
	
	/**
	 * This method returns the parameter type.
	 * 
	 * @return The parameter type
	 */
	public String getType() {
		if (_type != null) {
			return (_type);
		}
		return (_messageTransfer.getMessage().getTypes().get(_parameterIndex));
	}
	
	/**
	 * This method sets the parameter type.
	 * 
	 * @param type The parameter type
	 */
	public void setType(String type) {
		if (_messageTransfer != null) {
			_messageTransfer.getMessage().getTypes().set(_parameterIndex, type);
		}
	}
	
	/**
	 * This method returns the parameter value.
	 * 
	 * @return The parameter value
	 */
	public String getValue() {
		if (_value != null) {
			return (_value);
		}
		String ret="";
		
		if (_messageTransfer.getMessage().getValues().size() > _parameterIndex) {
			ret = (String)_messageTransfer.getMessage().getValues().get(_parameterIndex);
		}
		return (ret);
	}
	
	/**
	 * This method sets the parameter value.
	 * 
	 * @param value The parameter value
	 */
	public void setValue(String value) {
		if (_messageTransfer != null) {
			// Pad out
			while (_messageTransfer.getMessage().getValues().size() <= _parameterIndex) {
				_messageTransfer.getMessage().getValues().add("");
			}
			_messageTransfer.getMessage().getValues().set(_parameterIndex, value);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		String ret=getType();
		String value=getValue();
		
		if (value != null && value.trim().length() > 0) {
			String val=value.trim();
			
			val = val.replaceAll("[\t\r\n]", " ");
			
			if (val.length() > 20) {
				val = val.substring(0, 20)+"...";
			}
			
			ret += " ["+val+"]";
		}
		
		return (ret);
	}
}
