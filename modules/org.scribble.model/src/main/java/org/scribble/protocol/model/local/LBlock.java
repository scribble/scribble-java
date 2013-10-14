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
package org.scribble.protocol.model.local;

import org.scribble.protocol.model.ContainmentList;

/**
 * This class represents a group of activities.
 * 
 */
public class LBlock extends LActivity {
    
    private java.util.List<LActivity> _contents=
        new ContainmentList<LActivity>(this, LActivity.class);

    /**
     * This method returns the contents associated with
     * the block.
     * 
     * @return The contents
     */
    public java.util.List<LActivity> getContents() {
        return (_contents);
    }
    
    /**
     * This method adds an activity to the block.
     * 
     * @param act The activity
     * @return Whether the activity has been added
     */
    public boolean add(LActivity act) {
        return (_contents.add(act));
    }
    
    /**
     * This method removes an activity from the block.
     * 
     * @param act The activity
     * @return Whether the activity has been removed
     */
    public boolean remove(LActivity act) {
        return (_contents.remove(act));
    }
    
    /**
     * This method returns the number of activities
     * in the block.
     * 
     * @return The number of activities
     */
    public int size() {
        return (_contents.size());
    }
    
    /**
     * This method returns the activity at the specified
     * index.
     * 
     * @param index The index The index
     * @return The activity The activity
     * @throws IndexOutOfBoundsException Out of bounds 
     */
    public LActivity get(int index) throws IndexOutOfBoundsException {
        return (_contents.get(index));
    }
    
    /**
     * This method returns the index of the supplied activity.
     * 
     * @param act The activity
     * @return The index, or -1 if the activity is not found
     */
    public int indexOf(LActivity act) {
        return (_contents.indexOf(act));
    }
    
    /**
     * This method visits the model object using the supplied
     * visitor.
     * 
     * @param visitor The visitor
     */
    public void visit(LVisitor visitor) {
        
        if (visitor.start(this)) {
        
            for (int i=0; i < getContents().size(); i++) {
                getContents().get(i).visit(visitor);
            }
        }
        
        visitor.end(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LBlock that = (LBlock) o;

        return _contents.equals(that._contents);
    }

    @Override
    public int hashCode() {
        return _contents.hashCode();
    }

    @Override
    public String toString() {
        String result = "{\n";
        for (LActivity act : _contents) {
            result += act + "\n";
        }
        return result + "}";
    }

	/**
	 * {@inheritDoc}
	 */
    public void toText(StringBuffer buf, int level) {
		
		buf.append("{\n");
		
		for (LActivity act : _contents) {
			act.toText(buf, level+1);
		}
		
		indent(buf, level);
		buf.append("}");
	}
}
