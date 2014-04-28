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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.scribble.trace.model.MessageTransfer;

public class MTPList implements java.util.List<MessageTransferParameter> {

	private MessageTransfer _messageTransfer;
	
	/**
	 * The constructor.
	 * 
	 * @param mt The message transfer
	 */
	public MTPList(MessageTransfer mt) {
		_messageTransfer = mt;
	}
	
	@Override
	public int size() {
		return _messageTransfer.getMessage().getTypes().size();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<MessageTransferParameter> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(MessageTransferParameter e) {
		_messageTransfer.getMessage().getTypes().add(e.getType());
		
		if (_messageTransfer.getMessage().getValues().size() > 0) {
			String val=e.getValue();
			
			if (val == null) {
				val = "";
			}
			
			_messageTransfer.getMessage().getValues().add(val);
		}
		
		return (true);
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends MessageTransferParameter> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index,
			Collection<? extends MessageTransferParameter> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MessageTransferParameter get(int index) {
		MessageTransferParameter ret=null;
		
		if (index < _messageTransfer.getMessage().getTypes().size()) {
			String type=_messageTransfer.getMessage().getTypes().get(index);
			String value=null;
			
			if (_messageTransfer.getMessage().getValues().size() > 0) {
				value = (String)_messageTransfer.getMessage().getValues().get(index);
			}
			
			ret = new MessageTransferParameter(type, value);
		}
		
		return (ret);
	}

	@Override
	public MessageTransferParameter set(int index,
			MessageTransferParameter element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, MessageTransferParameter element) {
		_messageTransfer.getMessage().getTypes().add(index, element.getType());
		
		if (_messageTransfer.getMessage().getValues().size() > 0) {
			String val=element.getValue();
			
			if (val == null) {
				val = "";
			}
			
			_messageTransfer.getMessage().getValues().add(index, val);
		}
	}

	@Override
	public MessageTransferParameter remove(int index) {
		MessageTransferParameter ret=null;
		
		if (index < _messageTransfer.getMessage().getTypes().size()) {
			String type=_messageTransfer.getMessage().getTypes().remove(index);
			String value=null;
			
			if (_messageTransfer.getMessage().getValues().size() > 0) {
				value = (String)_messageTransfer.getMessage().getValues().remove(index);
			}
			
			ret = new MessageTransferParameter(type, value);
		}
		
		return (ret);
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<MessageTransferParameter> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<MessageTransferParameter> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<MessageTransferParameter> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

}
