/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.model;

/**
 * This class provides a default visitor which can be used
 * to traverse a model.
 */
public abstract class AbstractModelObjectVisitor implements Visitor {
		
	public AbstractModelObjectVisitor() {
	}
	
	/**
	 * This method can be implemented to process all of the model
	 * objects within a particular protocol model.
	 * 
	 * @param obj The model object
	 */
	public abstract boolean process(ModelObject obj);
	
	/**
	 * This method indicates the start of a
	 * block.
	 * 
	 * @param elem The block
	 * @return Whether to process the contents
	 */
	public boolean start(Block elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * block.
	 * 
	 * @param elem The block
	 */
	public void end(Block elem) {
	}
	
	/**
	 * This method visits an import component.
	 * 
	 * @param elem The import
	 */
	public void accept(TypeImportList elem) {
		process(elem);
	}
	
	/**
	 * This method visits an import component.
	 * 
	 * @param elem The import
	 */
	public void accept(ProtocolImportList elem) {
		process(elem);
	}
	
	/**
	 * This method visits an interaction component.
	 * 
	 * @param elem The interaction
	 */
	public void accept(Interaction elem) {
		process(elem);
	}
	
	/**
	 * This method indicates the start of a
	 * run construct.
	 * 
	 * @param elem The run
	 * @return Whether to process the contents
	 */
	public void accept(Run elem) {
		process(elem);
	}
	
	/**
	 * This method visits an use component.
	 * 
	 * @param elem The use component
	 */
	public void accept(Include elem) {
		process(elem);
	}
	
	/**
	 * This method visits a recursion component.
	 * 
	 * @param elem The recursion
	 */
	public void accept(Recursion elem) {
		process(elem);
	}
	
	/**
	 * This method visits the role list.
	 * 
	 * @param elem The role list
	 */
	public void accept(Introduces elem) {
		process(elem);
	}
	
	/**
	 * This method indicates the start of a
	 * protocol.
	 * 
	 * @param elem The protocol
	 * @return Whether to process the contents
	 */
	public boolean start(Protocol elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * protocol.
	 * 
	 * @param elem The protocol
	 */
	public void end(Protocol elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * choice.
	 * 
	 * @param elem The choice
	 * @return Whether to process the contents
	 */
	public boolean start(Choice elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * choice.
	 * 
	 * @param elem The choice
	 */
	public void end(Choice elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * directed choice.
	 * 
	 * @param elem The directed choice
	 * @return Whether to process the contents
	 */
	public boolean start(DirectedChoice elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * directed choice.
	 * 
	 * @param elem The directed choice
	 */
	public void end(DirectedChoice elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * on-message.
	 * 
	 * @param elem The on-message element
	 * @return Whether to process the contents
	 */
	public boolean start(OnMessage elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * on-message.
	 * 
	 * @param elem The on-message element
	 */
	public void end(OnMessage elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * parallel.
	 * 
	 * @param elem The parallel
	 * @return Whether to process the contents
	 */
	public boolean start(Parallel elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * parallel.
	 * 
	 * @param elem The parallel
	 */
	public void end(Parallel elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * repeat.
	 * 
	 * @param elem The repeat
	 * @return Whether to process the contents
	 */
	public boolean start(Repeat elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * repeat.
	 * 
	 * @param elem The repeat
	 */
	public void end(Repeat elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * labelled block.
	 * 
	 * @param elem The labelled block
	 * @return Whether to process the contents
	 */
	public boolean start(RecBlock elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * labelled block.
	 * 
	 * @param elem The labelled block
	 */
	public void end(RecBlock elem) {
	}
	
	/**
	 * This method indicates the start of an
	 * Unordered construct.
	 * 
	 * @param elem The Unordered construct
	 * @return Whether to process the contents
	 */
	public boolean start(Unordered elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of an
	 * Unordered construct.
	 * 
	 * @param elem The Unordered construct
	 */
	public void end(Unordered elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * try escape.
	 * 
	 * @param elem The try escape
	 * @return Whether to process the contents
	 */
	public boolean start(Try elem) {
		return(process(elem));
	}
	
	/**
	 * This method indicates the end of a
	 * try escape.
	 * 
	 * @param elem The try escape
	 */
	public void end(Try elem) {
	}
	
	/**
	 * This method indicates the start of a
	 * catch block.
	 * 
	 * @param elem The catch block
	 * @return Whether to process the contents
	 */
	public boolean start(Catch elem) {
		boolean ret=process(elem);
		
		for (Interaction interaction : elem.getInteractions()) {
			process(interaction);
		}
		
		return(ret);
	}
	
	/**
	 * This method indicates the end of a
	 * catch block.
	 * 
	 * @param elem The catch block
	 */
	public void end(Catch elem) {
	}
	
	/**
	 * This method visits a type component.
	 * 
	 * @param elem The type
	 */
	public void accept(TypeImport elem) {
		process(elem);
	}
	
	/**
	 * This method visits a protocol import component.
	 * 
	 * @param elem The protocol import
	 */
	public void accept(ProtocolImport elem) {
		process(elem);
	}

}
