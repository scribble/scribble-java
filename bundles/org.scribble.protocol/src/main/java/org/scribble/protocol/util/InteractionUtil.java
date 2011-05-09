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

public class InteractionUtil {

	public static Role getFromRole(ModelObject act) {
		Role ret=null;
		
		if (act instanceof Interaction) {
			ret = ((Interaction)act).getFromRole();
		} else if (act instanceof When && act.getParent() instanceof Choice) {
			ret = ((Choice)act.getParent()).getFromRole();
		} else if (act instanceof Choice) {
			ret = ((Choice)act).getFromRole();
		}
		
		return(ret);
	}
	
	public static Role getToRole(ModelObject act) {
		Role ret=null;
		
		if (act instanceof Interaction) {
			// TODO: Need to handle multiple 'to' roles
			if (((Interaction)act).getToRoles().size() > 0) {
				ret = ((Interaction)act).getToRoles().get(0);
			}
		} else if (act instanceof When && act.getParent() instanceof Choice) {
			ret = ((Choice)act.getParent()).getToRole();
		} else if (act instanceof Choice) {
			ret = ((Choice)act).getToRole();
		}
		
		return(ret);
	}
	
	public static MessageSignature getMessageSignature(ModelObject act) {
		MessageSignature ret=null;
		
		if (act instanceof Interaction) {
			ret = ((Interaction)act).getMessageSignature();
		} else if (act instanceof When) {
			ret = ((When)act).getMessageSignature();
		}
		
		return(ret);
	}
	
	/**
	 * This method determines the initial interactions associated with the
	 * supplied model object.
	 * 
	 * @param scope The scope within which the search for initial interactions should be conducted
	 * @return The list of initial 'interaction' based activities
	 */
	public static java.util.List<ModelObject> getInitialInteractions(ModelObject scope) {
		java.util.List<ModelObject> ret=new java.util.Vector<ModelObject>();

		scope.visit(new InitialInteractionVisitor(ret));
		
		return(ret);
	}
	
	/**
	 * This method determines whether the supplied interaction is the first within
	 * the supplied scope.
	 * 
	 * @param scope The scope
	 * @param interaction The interaction
	 * @return Whether the interaction is an initial interaction in the supplied scope
	 */
	public static boolean isInitialInteraction(ModelObject scope, ModelObject interaction) {
		boolean ret=false;
		
		java.util.List<ModelObject> initial=getInitialInteractions(scope);
		
		for (int i=0; ret == false && i < initial.size(); i++) {
			ret = (initial.get(i) == interaction);
		}
		
		return(ret);
	}

	public static class InitialInteractionVisitor extends DefaultVisitor {
		
		public InitialInteractionVisitor(java.util.List<ModelObject> list) {
			m_interactions = list;
		}
		
		public boolean start(Protocol elem) {
			// If top level protocol, then continue, otherwise finish.
			// Sub protocols should only be processed as part of a 
			// 'run' activity.
			return((elem.getParent() instanceof Protocol) == false);
		}
		
		public boolean start(Block elem) {
			return(true);
		}
		
		public void end(Block elem) {
		}

		public boolean start(Choice elem) {
			return(m_record);
		}
		
		public void end(Choice elem) {
			// TODO: SCRIBBLE-61 for now assume anything following
			// choice cannot be initial interaction
			m_record = false;
		}
		
		public boolean start(When elem) {
			m_savedState.put(elem, m_record);
			
			// Only record if whenblock associated with choice that has a
			// from and/or to role
			Choice choice=(Choice)elem.getParent();
			
			if (m_record && ((choice.getFromRole() != null && choice.getToRole() == null) ||
					(choice.getFromRole() == null && choice.getToRole() != null)) &&
					elem.getMessageSignature().getTypeReferences().size() > 0) {
					
					//(choice.getFromRole() == null ||
							//choice.getToRole() == null)) { // &&
							//(choice.getFromRole() != null && choice.getToRole() != null)) { // &&
							//elem.getMessageSignature().getTypeReferences().size() > 0) {
				m_interactions.add(elem);
				
				m_record = false;
			}
			
			return(true);
		}
		
		public void end(When elem) {
			m_record = m_savedState.get(elem);
			
			// TODO: SCRIBBLE-61 Need to accumulate results from when paths
			// to determine if subsequent interactions should still be counted
			// as 'initial' - but then could end up with situation where
			// choice has initial interactions followed by another interaction
			// that could potentially be initial
		}
		
		public void accept(Interaction elem) {
			
			if (m_record) {
				m_interactions.add(elem);
				
				m_record = false;
			}
		}
		
		public void accept(Run elem) {
			Protocol protocol=RunUtil.getInnerProtocol(elem.enclosingProtocol(),
							elem.getProtocolReference()); //elem.getProtocol();
			
			if (protocol != null) {
				protocol.visit(this);
			}
		}

		private java.util.List<ModelObject> m_interactions=null;
		private boolean m_record=true;
		private java.util.Map<ModelObject,Boolean> m_savedState=
						new java.util.HashMap<ModelObject,Boolean>();
	};
	
}
