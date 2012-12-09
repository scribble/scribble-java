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
package org.scribble.protocol.util;

import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Introduces;
import org.scribble.protocol.model.ModelObject;

/**
 * This class provides utility functions associated with activities.
 *
 */
public final class ActivityUtil {

    /**
     * Private constructor.
     */
    private ActivityUtil() {
    }
    
    /**
     * This method determines whether the activity is
     * a declaration.
     * 
     * @param act The activity
     * @return Whether the activity is a declaration
     */
    public static boolean isDeclaration(Activity act) {
        boolean ret=false;
        
        if (act instanceof Introduces) {
            ret = true;
        }
        
        return (ret);
    }
    
    /**
     * This method determines whether the activity is
     * a behavioural element.
     * 
     * @param act The activity
     * @return Whether the activity is a behavioural element
     */
    public static boolean isBehaviour(Activity act) {
        return (!isDeclaration(act));
    }
    
    /**
     * This method returns the innermost block that encloses all of the blocks supplied.
     * 
     * @param blockList The list of blocks to check
     * @return The block
     */
    public static Block getEnclosingBlock(java.util.List<Block> blockList) {
        Block ret=null;
        
        if (blockList.size() == 1) {
            ret = blockList.get(0);
            
        } else if (blockList.size() > 1) {
            // Find common parent block
            java.util.List<java.util.List<Block>> listOfBlocks=
                        new java.util.Vector<java.util.List<Block>>();
            
            for (Block block : blockList) {
                java.util.List<Block> lb=getBlockPath(block);
                
                if (lb != null && lb.size() > 0) {
                    listOfBlocks.add(lb);
                }
            }
            
            // Find common lowest level block
            int pos=-1;
            java.util.List<Block> refblocks=listOfBlocks.get(0);
            
            for (int j=0; j < refblocks.size(); j++) {
                boolean same=true;
                Block ref=refblocks.get(j);
                
                for (int i=1; same && i < listOfBlocks.size(); i++) {
                    java.util.List<Block> lb=listOfBlocks.get(i);
                    
                    if (lb.size() <= j || ref != lb.get(j)) {
                        same = false;
                    }
                }
                
                if (same) {
                    pos = j;
                }
            }
            
            if (pos != -1) {
                ret = refblocks.get(pos);
            }
        }
        
        return (ret);
    }
    
    /**
     * This method returns the list of blocks from the root to the supplied block.
     * 
     * @param b The block
     * @return The path from the root block to the supplied block
     */
    protected static java.util.List<Block> getBlockPath(Block b) {
        java.util.List<Block> ret=new java.util.Vector<Block>();
        ModelObject cur=b;
        
        while (cur instanceof Block) {
            ret.add(0, (Block)cur);
            
            do {
                cur = cur.getParent();                
            } while (cur != null && !(cur instanceof Block));
        }
        
        return (ret);
    }
}
