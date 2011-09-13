/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.protocol.conformance.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.DirectedChoice;
import org.scribble.protocol.model.Do;
import org.scribble.protocol.model.Interrupt;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.OnMessage;
import org.scribble.protocol.model.Parallel;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.Run;
import org.scribble.protocol.model.Unordered;
import org.scribble.protocol.model.DefaultVisitor;

public class ProtocolNormalizer {
	
	private static final Logger logger=Logger.getLogger(ProtocolNormalizer.class.getName());

	public static ProtocolModel normalize(ProtocolModel pm) {
		pm.visit(new NormalizingVisitor());
		
		return(pm);
	}
	
	private static class NormalizingVisitor extends DefaultVisitor {

/* Don't reorder for now - issue is receives from same role should retain their order,
 * and also original positions need to be preserved from reporting purposes
		@Override
		public boolean start(Block elem) {
			
			// Check for multiple consecutive receives, and order them
			java.util.List<Interaction> receives=new java.util.Vector<Interaction>();
			
			for (int i=0; i < elem.size(); i++) {
				Activity act=elem.get(i);
				
				if (act instanceof Interaction &&
						((Interaction)act).getToRoles().size() == 0) {
					receives.add((Interaction)act);
				} else {
					if (receives.size() > 1) {
						// Need to check ordering
						reorderReceives(elem, i-receives.size(), receives);
					}
					
					receives.clear();
				}
			}
			
			if (receives.size() > 1) {
				// Need to check ordering
				reorderReceives(elem, elem.size()-receives.size(), receives);
			}
			
			return true;
		}
		
		protected void reorderReceives(Block elem, int index, java.util.List<Interaction> receives) {
			Collections.sort(receives, new Comparator<Interaction>() {
				public int compare(Interaction arg0, Interaction arg1) {
					return(arg0.getMessageSignature().toString().compareTo(
								arg1.getMessageSignature().toString()));
				}				
			});
			
			for (int i=0; i < receives.size(); i++) {
				elem.getContents().set(index+i, receives.get(i));
			}
		}
*/
		
		@Override
		public boolean start(Choice elem) {
			
			// Sort paths based on textual description for now
			Collections.sort(elem.getBlocks(), new Comparator<Block>() {
				public int compare(Block arg0, Block arg1) {
					return(arg0.toString().compareTo(
								arg1.toString()));
				}				
			});
			
			return true;
		}

		@Override
		public boolean start(DirectedChoice elem) {
			// Sort paths based on textual description for now
			Collections.sort(elem.getOnMessages(), new Comparator<OnMessage>() {
				public int compare(OnMessage arg0, OnMessage arg1) {
					return(arg0.getMessageSignature().toString().compareTo(
								arg1.getMessageSignature().toString()));
				}				
			});
			
			return true;
		}

		@Override
		public boolean start(Parallel elem) {
			// Sort paths based on textual description for now
			Collections.sort(elem.getBlocks(), new Comparator<Block>() {
				public int compare(Block arg0, Block arg1) {
					return(arg0.toString().compareTo(
								arg1.toString()));
				}				
			});
			return true;
		}

		@Override
		public boolean start(Protocol elem) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean start(Unordered elem) {
			// Replace with a parallel construct
			Parallel p=new Parallel();
			p.derivedFrom(elem);
			
			for (int i=elem.getBlock().size()-1; i >= 0; i--) {
				Activity act=elem.getBlock().getContents().remove(i);
				
				Block b=new Block();
				b.add(act);
				
				p.getBlocks().add(0, b);
			}
			
			start(p);
			
			ModelObject parent=elem.getParent();
			if (parent instanceof Block) {
				int pos=((Block)parent).indexOf(elem);
				if (pos == -1) {
					logger.severe("Could not find unordered construct in parent block");
				} else {
					((Block)parent).getContents().set(pos, p);
				}
			} else {
				logger.severe("Unordered construct's parent was not a block");
			}
			
			return true;
		}

		@Override
		public boolean start(Do elem) {
			// Sort paths based on textual description for now
			Collections.sort(elem.getInterrupts(), new Comparator<Interrupt>() {
				public int compare(Interrupt arg0, Interrupt arg1) {
					return(arg0.toString().compareTo(
								arg1.toString()));
				}				
			});
			return true;
		}

		@Override
		public void accept(Run elem) {
			// TODO: Need to expand the 'run' protocol (if nested), only
			// leaving run construct if external protocol, or would result
			// in recursive definition
			
		}
	}
}
