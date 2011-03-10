/*
 * Copyright 2009 www.scribble.org
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

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * This class represents a list that understands how to manage
 * containment and parent relationships.
 *
 * @param <E>
 */
public class ContainmentList<E extends ModelObject> extends Vector<E> implements List<E> {

    private ModelObject m_parent=null;
    private Class<E> m_type=null;

	/**
	 * This is the constructor for the containment list
	 * @param parent
	 */
	public ContainmentList(ModelObject parent, Class<E> type) {
		m_parent = parent;
		m_type = type;
	}
	
	/**
	 * This method returns the base type associated with the list
	 * elements.
	 * 
	 * @return The base type for the list elements
	 */
	public Class<E> getType() {
		return(m_type);
	}

    /**
     * Appends the specified element to the end of this list (optional
     * operation). <p>
     *
     * Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some
     * lists will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what elements may be added.
     *
     * @param o element to be appended to this list.
     * @return <tt>true</tt> (as per the general contract of the
     *            <tt>Collection.add</tt> method).
     * 
     * @throws UnsupportedOperationException if the <tt>add</tt> method is not
     * 		  supported by this list.
     * @throws ClassCastException if the class of the specified element
     * 		  prevents it from being added to this list.
     * @throws NullPointerException if the specified element is null and this
     *           list does not support null elements.
     * @throws IllegalArgumentException if some aspect of this element
     *            prevents it from being added to this list.
     */
    public boolean add(E o) {
    	boolean ret=super.add(o);
		
    	if (ret) {
    		o.setParent(m_parent);
    	}
    	
    	return(ret);
    }
    
    /**
     * Removes the first occurrence in this list of the specified element 
     * (optional operation).  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index i
     * such that <tt>(o==null ? get(i)==null : o.equals(get(i)))</tt> (if
     * such an element exists).
     *
     * @param o element to be removed from this list, if present.
     * @return <tt>true</tt> if this list contained the specified element.
     * @throws ClassCastException if the type of the specified element
     * 	          is incompatible with this list (optional).
     * @throws NullPointerException if the specified element is null and this
     *            list does not support null elements (optional).
     * @throws UnsupportedOperationException if the <tt>remove</tt> method is
     *		  not supported by this list.
     */
    public boolean remove(Object o) {
    	boolean ret=super.remove(o);
    	
    	if (ret && o instanceof ModelObject) {
    		((ModelObject)o).setParent(null);
    	}
    	
    	return(ret);
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation is unspecified if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
     *
     * @param c collection whose elements are to be added to this list.
     * @return <tt>true</tt> if this list changed as a result of the call.
     * 
     * @throws UnsupportedOperationException if the <tt>addAll</tt> method is
     *         not supported by this list.
     * @throws ClassCastException if the class of an element in the specified
     * 	       collection prevents it from being added to this list.
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this list does not support null
     *         elements, or if the specified collection is <tt>null</tt>.
     * @throws IllegalArgumentException if some aspect of an element in the
     *         specified collection prevents it from being added to this
     *         list.
     * @see #add(Object)
     */
    public boolean addAll(Collection<? extends E> c) {
    	boolean ret=super.addAll(c);
    	
    	if (ret) {
    		java.util.Iterator<? extends E> iter=c.iterator();
    		
    		while (iter.hasNext()) {
    			iter.next().setParent(m_parent);
    		}
    	}
    	
    	return(ret);
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list at the specified position (optional operation).  Shifts the
     * element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this list in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * unspecified if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * @param index index at which to insert first element from the specified
     *	            collection.
     * @param c elements to be inserted into this list.
     * @return <tt>true</tt> if this list changed as a result of the call.
     * 
     * @throws UnsupportedOperationException if the <tt>addAll</tt> method is
     *		  not supported by this list.
     * @throws ClassCastException if the class of one of elements of the
     * 		  specified collection prevents it from being added to this
     * 		  list.
     * @throws NullPointerException if the specified collection contains one
     *           or more null elements and this list does not support null
     *           elements, or if the specified collection is <tt>null</tt>.
     * @throws IllegalArgumentException if some aspect of one of elements of
     *		  the specified collection prevents it from being added to
     *		  this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *		  &lt; 0 || index &gt; size()).
     */
    public boolean addAll(int index, Collection<? extends E> c) {
    	boolean ret=super.addAll(index, c);
    	
    	if (ret) {
    		java.util.Iterator<? extends E> iter=c.iterator();
    		
    		while (iter.hasNext()) {
    			iter.next().setParent(m_parent);
    		}
    	}
    	
    	return(ret);    	
    }

    /**
     * Removes from this list all the elements that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection that defines which elements will be removed from
     *          this list.
     * @return <tt>true</tt> if this list changed as a result of the call.
     * 
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
     * 		  is not supported by this list.
     * @throws ClassCastException if the types of one or more elements
     *            in this list are incompatible with the specified
     *            collection (optional).
     * @throws NullPointerException if this list contains one or more
     *            null elements and the specified collection does not support
     *            null elements (optional).
     * @throws NullPointerException if the specified collection is
     *            <tt>null</tt>.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
    	boolean ret=super.removeAll(c);
    	
    	if (ret) {
    		java.util.Iterator<?> iter=c.iterator();
    		
    		while (iter.hasNext()) {
    			Object val=iter.next();
    			if (val instanceof ModelObject) {
    				((ModelObject)val).setParent(null);
    			}
    		}
    	}
    	
    	return(ret);    	
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all the elements that are not contained in the specified
     * collection.
     *
     * @param c collection that defines which elements this set will retain.
     * 
     * @return <tt>true</tt> if this list changed as a result of the call.
     * 
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> method
     * 		  is not supported by this list.
     * @throws ClassCastException if the types of one or more elements
     *            in this list are incompatible with the specified
     *            collection (optional).
     * @throws NullPointerException if this list contains one or more
     *            null elements and the specified collection does not support
     *            null elements (optional).
     * @throws NullPointerException if the specified collection is
     *         <tt>null</tt>.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean retainAll(Collection<?> c) {
    	return(super.retainAll(c));
    }

    /**
     * Removes all of the elements from this list (optional operation).  This
     * list will be empty after this call returns (unless it throws an
     * exception).
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> method is
     * 		  not supported by this list.
     */
    public void clear() {
		java.util.Iterator<? extends E> iter=iterator();
		
		while (iter.hasNext()) {
			iter.next().setParent(null);
		}

		super.clear();
    }
    
    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * 
     * @throws UnsupportedOperationException if the <tt>set</tt> method is not
     *		  supported by this list.
     * @throws    ClassCastException if the class of the specified element
     * 		  prevents it from being added to this list.
     * @throws    NullPointerException if the specified element is null and
     *            this list does not support null elements.
     * @throws    IllegalArgumentException if some aspect of the specified
     *		  element prevents it from being added to this list.
     * @throws    IndexOutOfBoundsException if the index is out of range
     *		  (index &lt; 0 || index &gt;= size()).
     */
    public E set(int index, E element) {
    	E ret=super.set(index, element);
    	
    	if (element != null) {
    		element.setParent(m_parent);
    	}
    	
    	if (ret != null) {
    		ret.setParent(null);
    	}
    	
    	return(ret);
    }

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     * 
     * @throws UnsupportedOperationException if the <tt>add</tt> method is not
     *		  supported by this list.
     * @throws    ClassCastException if the class of the specified element
     * 		  prevents it from being added to this list.
     * @throws    NullPointerException if the specified element is null and
     *            this list does not support null elements.
     * @throws    IllegalArgumentException if some aspect of the specified
     *		  element prevents it from being added to this list.
     * @throws    IndexOutOfBoundsException if the index is out of range
     *		  (index &lt; 0 || index &gt; size()).
     */
    public void add(int index, E element) {
    	super.add(index, element);
    	
    	if (element != null) {
    		element.setParent(m_parent);
    	}
    }

    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * @param index the index of the element to removed.
     * @return the element previously at the specified position.
     * 
     * @throws UnsupportedOperationException if the <tt>remove</tt> method is
     *		  not supported by this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *            &lt; 0 || index &gt;= size()).
     */
    public E remove(int index) {
    	E ret=super.remove(index);
    	
    	if (ret != null) {
    		ret.setParent(null);
    	}
    	
    	return(ret);
    }
}
