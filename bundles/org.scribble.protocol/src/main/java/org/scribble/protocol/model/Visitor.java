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
package org.scribble.protocol.model;

/**
 * This interface represents a visitor which can be used
 * to traverse a model.
 */
public interface Visitor {
	
	/**
	 * This method indicates the start of a
	 * block.
	 * 
	 * @param elem The block
	 * @return Whether to process the contents
	 */
	public boolean start(Block elem);
	
	/**
	 * This method indicates the end of a
	 * block.
	 * 
	 * @param elem The block
	 */
	public void end(Block elem);
	
	/**
	 * This method indicates the start of a
	 * choice.
	 * 
	 * @param elem The choice
	 * @return Whether to process the contents
	 */
	public boolean start(Choice elem);
	
	/**
	 * This method indicates the end of a
	 * choice.
	 * 
	 * @param elem The choice
	 */
	public void end(Choice elem);
	
	/**
	 * This method indicates the start of a
	 * parallel.
	 * 
	 * @param elem The parallel
	 * @return Whether to process the contents
	 */
	public boolean start(Parallel elem);
	
	/**
	 * This method indicates the end of a
	 * parallel.
	 * 
	 * @param elem The parallel
	 */
	public void end(Parallel elem);
	
	/**
	 * This method indicates the start of a
	 * protocol.
	 * 
	 * @param elem The protocol
	 * @return Whether to process the contents
	 */
	public boolean start(Protocol elem);
	
	/**
	 * This method indicates the end of a
	 * protocol.
	 * 
	 * @param elem The protocol
	 */
	public void end(Protocol elem);
	
	/**
	 * This method indicates the start of a
	 * repeat.
	 * 
	 * @param elem The repeat
	 * @return Whether to process the contents
	 */
	public boolean start(Repeat elem);
	
	/**
	 * This method indicates the end of a
	 * repeat.
	 * 
	 * @param elem The repeat
	 */
	public void end(Repeat elem);
	
	/**
	 * This method indicates the start of a
	 * labelled block.
	 * 
	 * @param elem The labelled block
	 * @return Whether to process the contents
	 */
	public boolean start(RecBlock elem);
	
	/**
	 * This method indicates the end of a
	 * labelled block.
	 * 
	 * @param elem The labelled block
	 */
	public void end(RecBlock elem);
	
	/**
	 * This method indicates the start of a
	 * unordered block.
	 * 
	 * @param elem The unordered block
	 * @return Whether to process the contents
	 */
	public boolean start(Unordered elem);
	
	/**
	 * This method indicates the end of a
	 * unordered block.
	 * 
	 * @param elem The unordered block
	 */
	public void end(Unordered elem);
	
	/**
	 * This method indicates the start of a
	 * try escape.
	 * 
	 * @param elem The try escape
	 * @return Whether to process the contents
	 */
	public boolean start(Try elem);
	
	/**
	 * This method indicates the end of a
	 * try escape.
	 * 
	 * @param elem The try escape
	 */
	public void end(Try elem);
	
	/**
	 * This method indicates the start of a
	 * catch block.
	 * 
	 * @param elem The catch block
	 * @return Whether to process the contents
	 */
	public boolean start(Catch elem);
	
	/**
	 * This method indicates the end of a
	 * catch block.
	 * 
	 * @param elem The catch block
	 */
	public void end(Catch elem);
	
	/**
	 * This method indicates the start of a
	 * run construct.
	 * 
	 * @param elem The run
	 * @return Whether to process the contents
	 */
	public boolean start(Run elem);
	
	/**
	 * This method indicates the end of a
	 * run construct.
	 * 
	 * @param elem The run
	 */
	public void end(Run elem);
	
	/**
	 * This method visits an import component.
	 * 
	 * @param elem The import
	 */
	public void accept(TypeImportList elem);
	
	/**
	 * This method visits an import component.
	 * 
	 * @param elem The import
	 */
	public void accept(ProtocolImportList elem);
	
	/**
	 * This method visits an interaction component.
	 * 
	 * @param elem The interaction
	 */
	public void accept(Interaction elem);
	
	/**
	 * This method visits the role list.
	 * 
	 * @param elem The role list
	 */
	public void accept(RoleList elem);
	
	/**
	 * This method visits a recursion component.
	 * 
	 * @param elem The recursion
	 */
	public void accept(Recursion elem);
	
	/**
	 * This method visits an include component.
	 * 
	 * @param elem The include
	 */
	public void accept(Use elem);
	
	/**
	 * This method visits a type import component.
	 * 
	 * @param elem The type import
	 */
	public void accept(TypeImport elem);
	
	/**
	 * This method visits a protocol import component.
	 * 
	 * @param elem The protocol import
	 */
	public void accept(ProtocolImport elem);
	
}
