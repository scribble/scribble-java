/*
 * Copyright 2009-10 www.scribble.org
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

import org.scribble.protocol.model.*;

/**
 * This class provides utility functions related to the Role protocol
 * component.
 *
 */
public class RoleUtil {

	/**
	 * This method returns the roles defined within the parent scope of the
	 * supplied activity.
	 * 
	 * @param block The parent scope
	 * @return The set of roles
	 */
	public static java.util.Set<Role> getDeclaredRoles(Block block) {
		final java.util.Set<Role> roles=new java.util.HashSet<Role>();
		
		block.visit(new DefaultVisitor() {
			
			protected void addRole(Role r) {
				if (r != null && !roles.contains(r)) {
					roles.add(r);
				}
			}
			
			public void accept(Introduces elem) {
				for (Role r : elem.getRoles()) {
					addRole(r);
				}
			}
			
			public boolean start(Protocol elem) {
				return(false);
			}
		});
		
		return(roles);
	}
	
	public static Protocol getEnclosingProtocol(Role role) {
		Protocol ret=null;
		
		if (role.getParent() instanceof Introduces) {
			ret = ((Introduces)role.getParent()).enclosingProtocol();
		} else if (role.getParent() instanceof ParameterDefinition &&
				role.getParent().getParent() instanceof Protocol) {
			ret = (Protocol)role.getParent().getParent();
		}
		
		return(ret);
	}
	
	/**
	 * This method returns the set of roles that are associated with
	 * the supplied activity.
	 * 
	 * @param act The activity
	 * @return The set of roles associated with the activity and its
	 * 				sub components if a grouping construct
	 */
	public static java.util.Set<Role> getUsedRoles(final Activity act) {
		final java.util.Set<Role> roles=new java.util.HashSet<Role>();
		
		act.visit(new DefaultVisitor() {
			
			protected void addRole(Role r) {
				if (r != null && !roles.contains(r)) {
					roles.add(r);
				}
			}
			
			public void accept(Interaction elem) {
				addRole(elem.getFromRole());
				for (Role r : elem.getToRoles()) {
					addRole(r);
				}
			}
			
			public void accept(Inline elem) {
				java.util.Set<Role> inscope=getRolesInScope(elem);

				for (Parameter p : elem.getParameters()) {
					
					// Determine if the parameter is a role
					for (Role r : inscope) {
						if (r.getName().equals(p.getName())) {
							addRole(r);
							break;
						}
					}
				}
			}
			
			public void accept(Run elem) {
				java.util.Set<Role> inscope=getRolesInScope(elem);

				for (Parameter p : elem.getParameters()) {
					
					// Determine if the parameter is a role
					for (Role r : inscope) {
						if (r.getName().equals(p.getName())) {
							addRole(r);
							break;
						}
					}
				}
			}
			
			public boolean start(Choice elem) {
				addRole(elem.getRole());
				return(true);
			}
			
			public boolean start(DirectedChoice elem) {
				addRole(elem.getFromRole());
				for (Role r : elem.getToRoles()) {
					addRole(r);
				}
				return(true);
			}
			
			public boolean start(Protocol elem) {
				// If protocol was initial activity, then traverse, otherwise
				// don't enter nested protocol
				return(elem == act);
			}
		});
		
		return(roles);
	}
	
	/**
	 * This method determines the set of roles that are in the
	 * scope for the supplied activity.
	 * 
	 * @param activity The activity
	 * @return The set of roles in scope for the supplied activity
	 */
	public static java.util.Set<Role> getRolesInScope(Activity activity) {
		java.util.Set<Role> ret=new java.util.HashSet<Role>();
		
		if (activity != null) {
			
			// Identify enclosing protocol definition
			Protocol protocol=activity.enclosingProtocol();
			
			if (protocol != null) {
				RoleLocator visitor=new RoleLocator(protocol, activity, ret);
				
				protocol.visit(visitor);
			}
		}
		
		return(ret);
	}
	
	/**
	 * This method returns the innermost block that encloses all of the activities
	 * associated with the supplied role.
	 * 
	 * @param protocol The protocol
	 * @param role The role
	 * @return The block
	 */
	public static Block getEnclosingBlock(final Protocol protocol, final Role role) {
		Block ret=null;
		final java.util.List<Block> blocks=new java.util.Vector<Block>();
		
		// Find all blocks enclosing an activity associated with the supplied role
		protocol.visit(new DefaultVisitor() {
			
			public boolean start(Protocol elem) {
				
				if (protocol.getParameterDefinition(role.getName()) != null) {
					blocks.add(elem.getBlock());
				}
				
				// Don't visit contained protocols
				return(protocol == elem);
			}
			
			public void accept(org.scribble.protocol.model.Interaction elem) {
				if (role.equals(elem.getFromRole()) || elem.getToRoles().contains(role) ||
						((elem.getFromRole() == null || elem.getToRoles().size() == 0) &&
								role.equals(elem.enclosingProtocol().getRole()))) {
					blocks.add((Block)elem.getParent());
				}
			}
			
			public boolean start(Choice elem) {
				if (role.equals(elem.getRole()) /*|| (elem.getRole() == null &&
								role.equals(elem.enclosingProtocol().getRole()))*/) {
					blocks.add((Block)elem.getParent());
				}
				
				return(true);
			}
			
			public boolean start(DirectedChoice elem) {
				if (role.equals(elem.getFromRole()) || elem.getToRoles().contains(role) ||
						((elem.getFromRole() == null || elem.getToRoles().size() == 0) &&
								role.equals(elem.enclosingProtocol().getRole()))) {
					blocks.add((Block)elem.getParent());
				}
				
				return(true);
			}
		});
		
		if (blocks.size() == 0) {
			// Fall through as no suitable activities found
		} else if (blocks.size() == 1) {
			ret = blocks.get(0);
		} else {
			// Find common parent block
			java.util.List<java.util.List<Block>> listOfBlocks=
						new java.util.Vector<java.util.List<Block>>();
			
			for (Block block : blocks) {
				java.util.List<Block> lb=getBlockPath(block);
				
				if (lb != null && lb.size() > 0) {
					listOfBlocks.add(lb);
				}
			}
			
			// Find common lowest level block
			int pos=-1;
			java.util.List<Block> refblocks=listOfBlocks.get(0);
			
			for (int j=0; j < refblocks.size(); j++) {
				boolean f_same=true;
				Block ref=refblocks.get(j);
				
				for (int i=1; f_same && i < listOfBlocks.size(); i++) {
					java.util.List<Block> lb=listOfBlocks.get(i);
					
					if (lb.size() <= j || ref != lb.get(j)) {
						f_same = false;
					}
				}
				
				if (f_same) {
					pos = j;
				}
			}
			
			if (pos != -1) {
				ret = refblocks.get(pos);
			}
		}
		
		// Check whether block is contained within another construct that
		// must be preserved
		ret = checkContainingConstructs(ret);
		
		return(ret);
	}
	
	protected static Block checkContainingConstructs(Block block) {
		Block ret=block;
		ModelObject cur=ret;
		
		while (cur != null && cur.getParent() != null &&
					(cur.getParent() instanceof Protocol) == false) {
			if (cur.getParent().getClass() == RecBlock.class) {
				RecBlock rb=new RecBlock();
				
				rb.derivedFrom(cur.getParent());
				rb.setLabel(((RecBlock)cur.getParent()).getLabel());
				
				rb.setBlock(ret);
				
				ret = new Block();
				ret.add(rb);
			}
			
			cur = cur.getParent();
		}
		
		return(ret);
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
			} while (cur != null && (cur instanceof Block) == false);
		}
		
		return(ret);
	}

	public static class RoleLocator implements org.scribble.protocol.model.Visitor {
		
		public RoleLocator(Protocol protocol, Activity activity, 
								java.util.Set<Role> result) {
			m_protocol = protocol;
			m_activity = activity;
			m_result = result;
		}
		
		public boolean start(Block elem) {
			return(startBlock(elem));	
		}
		
		protected boolean startBlock(Block elem) {
			// Push new role list onto the stack
			m_roleStack.add(new java.util.Vector<Role>());
			
			return(true);
		}
		
		public void end(Block elem) {
			endBlock(elem);
		}
		
		protected void endBlock(Block elem) {
			// Pop top role list from the stack
			m_roleStack.remove(m_roleStack.size()-1);
		}
		
		public void end(Choice elem) {
		}

		public void end(DirectedChoice elem) {
		}

		public void end(OnMessage elem) {
		}

		public void end(Unordered elem) {
		}

		public void end(Parallel elem) {
		}

		public void end(Protocol elem) {
		}

		public void end(Repeat elem) {
		}

		public void end(RecBlock elem) {
		}

		public void end(Do elem) {
		}

		public void end(Interrupt elem) {
		}

		public boolean start(Choice elem) {
			checkActivity(elem);
			return(m_recurse);
		}

		public boolean start(DirectedChoice elem) {
			checkActivity(elem);
			return(m_recurse);
		}

		public boolean start(OnMessage elem) {
			return(m_recurse);
		}

		public boolean start(Unordered elem) {
			checkActivity(elem);
			return(m_recurse);
		}

		public boolean start(Parallel elem) {
			checkActivity(elem);
			return(m_recurse);
		}

		public boolean start(Protocol elem) {
			// If this is the enclosing protocol, then
			// recursively visit it - otherwise don't
			boolean ret=(m_protocol==elem);
			
			if (ret) {
				java.util.List<Role> rlist=new java.util.Vector<Role>();
				m_roleStack.add(rlist);
				
				if (elem.getRole() != null) {
					rlist.add(elem.getRole());
				}
				
				for (ParameterDefinition p : elem.getParameterDefinitions()) {
					if (p.getType() == null) {
						rlist.add(new Role(p.getName()));
					}
				}
				
			}
			
			return(ret);
		}

		public boolean start(Repeat elem) {
			checkActivity(elem);
			return(m_recurse);
		}

		public boolean start(RecBlock elem) {
			checkActivity(elem);
			return(m_recurse);
		}

		public boolean start(Do elem) {
			checkActivity(elem);
			return(m_recurse);
		}

		public boolean start(Interrupt elem) {
			return(m_recurse);
		}

		public void accept(Run elem) {
			checkActivity(elem);
		}

		public void accept(TypeImportList elem) {
		}

		public void accept(ProtocolImportList elem) {
		}

		public void accept(Interaction elem) {
			checkActivity(elem);
		}

		public void accept(Introduces elem) {
			java.util.List<Role> rlist=m_roleStack.get(m_roleStack.size()-1);
			
			rlist.addAll(elem.getRoles());
		}

		public void accept(Inline elem) {
			checkActivity(elem);
		}
		
		public void accept(Recursion elem) {
			checkActivity(elem);
		}
		
		public void accept(TypeImport elem) {			
		}
		
		public void accept(ProtocolImport elem) {
		}
		
		/**
		 * This method visits an end statement.
		 * 
		 * @param elem The end statement
		 */
		public void accept(End elem) {
			checkActivity(elem);
		}
		
		protected void checkActivity(Activity elem) {
			
			if (elem == m_activity) {
				// Record the current stack of roles in the result
				for (java.util.List<Role> plist : m_roleStack) {
					
					for (Role p : plist) {
						m_result.add(p);
					}
				}
				
				// Remainder of description should be traversed quickly
				m_recurse = false;
			}
		}
		
		private boolean m_recurse=true;
		private Protocol m_protocol=null;
		private Activity m_activity=null;
		private java.util.Set<Role> m_result=null;
		private java.util.List<java.util.List<Role>> m_roleStack=
					new java.util.Vector<java.util.List<Role>>();
	}
}
