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
package org.scribble.protocol.export.monitor;

import java.util.UUID;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.export.ProtocolExporter;
import org.scribble.protocol.model.*;
import org.scribble.protocol.util.*;
import org.scribble.protocol.monitor.model.*;
import org.scribble.protocol.monitor.util.MonitorModelUtil;

public class MonitorProtocolExporter implements ProtocolExporter {

	public static final String MONITOR_ID = "monitor";
	
	/**
	 * This method returns the id of the exporter.
	 * 
	 * @return The exporter id
	 */
	public String getId() {
		return(MONITOR_ID);
	}
	
	/**
	 * This method returns the name of the exporter for use in
	 * user based selectors.
	 * 
	 * @return The name of the exporter
	 */
	public String getName() {
		return("Monitor");
	}
	
	/**
	 * This method exports the supplied protocol model, in the implementation
	 * specific format, to the specified output stream. If any issues occur
	 * during the export process, they will be reported to the journal.
	 * 
	 * @param model The protocol model to be exported
	 * @param journal The journal
	 * @param os The output stream
	 */
	public void export(ProtocolModel model, Journal journal, java.io.OutputStream os) {
		Description desc=generateDescription(model, journal);
		
		try {
			MonitorModelUtil.serialize(desc, os);
		} catch(Exception e) {
			journal.error("Export failed", null);
		}
	}
	
	/**
	 * This method generates the protocol model's monitoring description.
	 * 
	 * @param model The protocol model
	 * @param journal The journal for reporting errors
	 * @return The monitoring description
	 */
	public Description generateDescription(ProtocolModel model, Journal journal) {
		MonitorExportVisitor visitor=new MonitorExportVisitor(journal);
		
		model.visit(visitor);
		
		Description desc=visitor.getDescription();
		
		return(desc);
	}
	
	public static class MonitorExportVisitor extends DefaultVisitor {
		
		private Journal m_journal=null;
		
		// This list defines the nodes used to create the final representation of the
		// state machine
		private java.util.List<Node> m_nodes=
			new java.util.Vector<Node>();
		
		private java.util.Map<org.scribble.protocol.monitor.model.Choice,java.util.List<Path>> m_choicePaths=
			new java.util.HashMap<org.scribble.protocol.monitor.model.Choice,java.util.List<Path>>();
		
		private java.util.Map<org.scribble.protocol.monitor.model.Parallel,java.util.List<Path>> m_parallelPaths=
			new java.util.HashMap<org.scribble.protocol.monitor.model.Parallel,java.util.List<Path>>();
		
		private java.util.Map<String,Integer> m_recurPosition=
			new java.util.HashMap<String,Integer>();
		
		// This list represents a cache of nodes that are awaiting their 'nextIndex' field
		// to be set
		private java.util.List<Object> m_pendingNextIndex=
			new java.util.Vector<Object>();
		
		// This map contains a reference from an activity, to other relevant information required
		// later (e.g. nodes, etc).
		private java.util.Map<Activity,Object> m_nodeMap=
			new java.util.HashMap<Activity,Object>();
		
		private java.util.Map<ModelObject,java.util.List<Object>> m_nodeCache=
			new java.util.HashMap<ModelObject,java.util.List<Object>>();
		
		public MonitorExportVisitor(Journal journal) {
			m_journal = journal;
		}
		
		/**
		 * This method indicates the start of a
		 * block.
		 * 
		 * @param elem The block
		 * @return Whether to process the contents
		 */
		public boolean start(Block elem) {
			startActivity(elem);
			
			return(true);
		}
		
		protected void startActivity(Activity act) {
			
			// Check if activity is block and parent is parallel
			if ((act instanceof Block && act.getParent() instanceof org.scribble.protocol.model.Parallel) ||
					(act.getParent() instanceof Block &&
							act.getParent().getParent() instanceof Unordered)) {
				
				// Create path node
				Path path=new Path();
				
				org.scribble.protocol.monitor.model.Parallel node=getParallelNode(act);

				java.util.List<Path> pathBuilderList=
							m_parallelPaths.get(node);
				
				if (pathBuilderList == null) {
					pathBuilderList = new java.util.Vector<Path>();
					m_parallelPaths.put(node, pathBuilderList);
				}
				
				pathBuilderList.add(path);
				
				m_pendingNextIndex.add(path);
			} else if (act instanceof Block && act.getParent() instanceof org.scribble.protocol.model.Choice) {
				
				// Create path node
				Path path=new Path();
				
				// Define id associated with the choice label
				//path.setId(ChoiceUtil.getLabel(elem.getMessageSignature()));
				
				org.scribble.protocol.monitor.model.Choice choiceBuilder=
					(org.scribble.protocol.monitor.model.Choice)m_nodeMap.get(act.getParent());

				java.util.List<Path> pathBuilderList=
							m_choicePaths.get(choiceBuilder);
				
				if (pathBuilderList == null) {
					pathBuilderList = new java.util.Vector<Path>();
					m_choicePaths.put(choiceBuilder, pathBuilderList);
				}
				
				pathBuilderList.add(path);
				
				m_pendingNextIndex.add(path);

				// Only create an interaction (message) node inside path if there is
				// expected message content
				/* CHANGE TO NEW CHOICE NOTATION SCRIBBLE-94
				if (elem.getMessageSignature().getTypeReferences().size() > 0) {
					Choice choice=(Choice)elem.getParent();
					
					java.util.List<Role> roles=new java.util.Vector<Role>();
					if (choice.getToRole() != null) {
						roles.add(choice.getToRole());
					}
					
					createInteraction(choice, elem.getMessageSignature(), choice.getRole(), roles,
									elem.getAnnotations());
				}
				*/
				
				// Create annotations
				for (org.scribble.common.model.Annotation pma : act.getAnnotations()) {
					org.scribble.protocol.monitor.model.Annotation pmma=
								new org.scribble.protocol.monitor.model.Annotation();
					
					if (pma.getId() != null) {
						pmma.setId(pma.getId());
					} else {
						pmma.setId(UUID.randomUUID().toString());
					}
					pmma.setValue(pma.toString());
					
					path.getAnnotation().add(pmma);
				}
			}

			// Ignore blocks when establishing the next index of preceding
			// activities. We only want 'nextIndex' fields to be updated to
			// point to concrete activities.
			if ((act instanceof Block) == false) {
				establishNextIndex();
			}
		}
		
		protected void endActivity(Activity act) {
			
			if (act.getParent() instanceof  org.scribble.protocol.model.Choice) {
				java.util.List<Object> cache=getCache(act.getParent());
				
				cache.addAll(m_pendingNextIndex);
			}
			
			// Check if block associated with a parallel or an activity in an
			// unordered construct
			if (act.getParent() instanceof  org.scribble.protocol.model.Choice ||
					act.getParent() instanceof  org.scribble.protocol.model.Parallel ||
					(act.getParent() instanceof Block &&
					act.getParent().getParent() instanceof Unordered)) {
				
				// Make sure pending 'nextIndex' nodes do not get
				// set - they just need to result in the trail
				// for that context being completed so that the
				// parent context is reactivated (once all other
				// paths have equally finished)
				m_pendingNextIndex.clear();
			}
		}
		
		protected org.scribble.protocol.monitor.model.Parallel getParallelNode(Activity act) {
			org.scribble.protocol.monitor.model.Parallel ret=null;
			
			if (act instanceof Block && act.getParent() instanceof org.scribble.protocol.model.Parallel) {
				ret = (org.scribble.protocol.monitor.model.Parallel)m_nodeMap.get(act.getParent());
			} else if (act.getParent() instanceof Block &&
						act.getParent().getParent() instanceof Unordered) {
				ret = (org.scribble.protocol.monitor.model.Parallel)m_nodeMap.get(act.getParent().getParent());
			}
		
			return(ret);
		}
				
		/**
		 * This method indicates the end of a
		 * block.
		 * 
		 * @param elem The block
		 */
		public void end(Block elem) {

			endActivity(elem);
			
			/*
			// Check if block associated with a parallel
			if (elem.getParent() instanceof Parallel) {
				
				// Make sure pending 'nextIndex' nodes do not get
				// set - they just need to result in the trail
				// for that context being completed so that the
				// parent context is reactivated (once all other
				// paths have equally finished)
				m_pendingNextIndex.clear();
			}
			*/
		}
		
		/**
		 * This method visits an import component.
		 * 
		 * @param elem The import
		 */
		public void accept(TypeImportList elem) {
		}
		
		/**
		 * This method visits an import component.
		 * 
		 * @param elem The import
		 */
		public void accept(ProtocolImportList elem) {
		}
		
		/**
		 * This method visits the introduces construct.
		 * 
		 * @param elem The introduces construct
		 */
		public void accept(Introduces elem) {
		}
		
		/**
		 * This method visits an interaction component.
		 * 
		 * @param elem The interaction
		 */
		public void accept(Interaction elem) {
			startActivity(elem);
			
			createInteraction(elem.getMessageSignature(), elem.getFromRole(), elem.getToRoles(),
								elem.getAnnotations());
			
			endActivity(elem);
		}

		protected void createInteraction(MessageSignature ms, Role fromRole,
								java.util.List<Role> toRoles,
								java.util.List<org.scribble.common.model.Annotation> annotations) {
			
			// TODO: Think about how best to set the next index. Can only do this
			// if the next node is within the same scope. If (for example) the
			// last node in a sub-protocol, which happens to be called from
			// multiple locations, then we need to create a sub-conversation
			// context with the positions - and when the trail ends, it should
			// complete the sub-conversation context, causing the parent
			// context to be notified with the appropriate continuation point.
			
			MessageNode node=null;
			
			if (toRoles.size() > 0) {
				node = new SendMessage();
				
				String roles="";
				for (int i=0; i < toRoles.size(); i++) {
					if (i > 0) {
						roles += ",";
					}
					roles += toRoles.get(i).getName();
				}
				
				node.setOtherRole(roles);
			} else {
				node = new ReceiveMessage();
				
				node.setOtherRole(fromRole.getName());
			}
			
			if (ms.getOperation() != null) {
				node.setOperator(ms.getOperation());
			}
			
			for (TypeReference tref : ms.getTypeReferences()) {
				String type=tref.getName();
				
				// Get type import associated with the reference
				TypeImport ti=TypesUtil.getTypeImport(tref);
				
				if (ti != null && ti.getDataType() != null) {
					type = ti.getDataType().getDetails();
					
				} else if (TypesUtil.isConcreteTypesDefined(tref.getModel())) {
					// Check if any concrete types have bene defined
					// If so, report failure to resolve concrete type
					m_journal.error("Concrete type not found for '"+
							type+"'", tref.getProperties());
				}
							
				MessageType mt=new MessageType();
				mt.setValue(type);
				node.getMessageType().add(mt);
			}
			
			// Export annotations
			createAnnotations(node, annotations);
			
			m_nodes.add(node);
						
			m_pendingNextIndex.add(node);
		}
		
		protected void createAnnotations(Node node,
				java.util.List<org.scribble.common.model.Annotation> annotations) {
			
			for (org.scribble.common.model.Annotation pma : annotations) {
				org.scribble.protocol.monitor.model.Annotation pmma=
							new org.scribble.protocol.monitor.model.Annotation();
				
				if (pma.getId() != null) {
					pmma.setId(pma.getId());
				} else {
					pmma.setId(UUID.randomUUID().toString());
				}
				pmma.setValue(pma.toString());
				
				node.getAnnotation().add(pmma);
			}
		}
		
		protected int getCurrentIndex() {
			return(m_nodes.size());
		}
		
		protected void establishNextIndex() {
			// Establish next index association with this node
			establishNextIndex(getCurrentIndex());
		}
		
		protected void establishNextIndex(int pos) {
			for (Object b : m_pendingNextIndex) {
				
				if (b instanceof Node) {
					((Node)b).setNextIndex(pos);
				} else if (b instanceof Path) {
					((Path)b).setNextIndex(pos);
				}
			}
			
			m_pendingNextIndex.clear();
		}
		
		/**
		 * This method indicates the start of a
		 * protocol.
		 * 
		 * @param elem The protocol
		 * @return Whether to process the contents
		 */
		@Override
		public boolean start(org.scribble.protocol.model.Protocol elem) {
			// Clear pending next index
			m_pendingNextIndex.clear();
			
			// Cache the current node position associated with the protocol
			m_nodeMap.put(elem, new Integer(m_nodes.size()));
			
			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * protocol.
		 * 
		 * @param elem The protocol
		 */
		@Override
		public void end(org.scribble.protocol.model.Protocol elem) {
			// Clear pending next index
			m_pendingNextIndex.clear();
			
			// Check if position has moved, and if so, load the nodes associated
			// with the run activities for this protocol
			Integer pos=(Integer)m_nodeMap.get(elem);
			
			if (pos != null && pos.intValue() < m_nodes.size()) {
				
				java.util.List<Object> objs=m_nodeCache.get(elem);
				
				for (int i=0; objs != null && i < objs.size(); i++) {
					if (objs.get(i) instanceof Decision) {
						((Decision)objs.get(i)).setInnerIndex(pos.intValue());
					} else if (objs.get(i) instanceof Scope) {
						((Scope)objs.get(i)).setInnerIndex(pos.intValue());						
					}
				}
			}
		}
		
		/**
		 * This method indicates the start of a
		 * choice.
		 * 
		 * @param elem The choice
		 * @return Whether to process the contents
		 */
		public boolean start(org.scribble.protocol.model.Choice elem) {

			startActivity(elem);
			
			org.scribble.protocol.monitor.model.Choice node=
					new org.scribble.protocol.monitor.model.Choice();
			
			m_nodes.add(node);
						
			// Cache the node associated with the choice
			m_nodeMap.put(elem, node);

			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * choice.
		 * 
		 * @param elem The choice
		 */
		public void end(org.scribble.protocol.model.Choice elem) {
			
			java.util.List<Object> cache=getCache(elem);

			m_pendingNextIndex.addAll(cache);
			
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * directed choice.
		 * 
		 * @param elem The choice
		 * @return Whether to process the contents
		 */
		public boolean start(org.scribble.protocol.model.DirectedChoice elem) {

			startActivity(elem);
			
			org.scribble.protocol.monitor.model.Choice node=
					new org.scribble.protocol.monitor.model.Choice();
			
			m_nodes.add(node);
						
			// Cache the node associated with the choice
			m_nodeMap.put(elem, node);

			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * directed choice.
		 * 
		 * @param elem The choice
		 */
		public void end(org.scribble.protocol.model.DirectedChoice elem) {
			
			java.util.List<Object> cache=getCache(elem);

			m_pendingNextIndex.addAll(cache);
			
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * directed choice.
		 * 
		 * @param elem The choice
		 * @return Whether to process the contents
		 */
		public boolean start(org.scribble.protocol.model.OnMessage elem) {

			DirectedChoice dc=(DirectedChoice)elem.getParent();
			
			// Create path node
			Path path=new Path();
			
			// Define id associated with the choice label
			//path.setId(ChoiceUtil.getLabel(elem.getMessageSignature()));
			
			org.scribble.protocol.monitor.model.Choice choiceBuilder=
				(org.scribble.protocol.monitor.model.Choice)m_nodeMap.get(elem.getParent());

			java.util.List<Path> pathBuilderList=
						m_choicePaths.get(choiceBuilder);
			
			if (pathBuilderList == null) {
				pathBuilderList = new java.util.Vector<Path>();
				m_choicePaths.put(choiceBuilder, pathBuilderList);
			}
			
			pathBuilderList.add(path);
			
			m_pendingNextIndex.add(path);

			// Create annotations
			for (org.scribble.common.model.Annotation pma : elem.getAnnotations()) {
				org.scribble.protocol.monitor.model.Annotation pmma=
							new org.scribble.protocol.monitor.model.Annotation();
				
				if (pma.getId() != null) {
					pmma.setId(pma.getId());
				} else {
					pmma.setId(UUID.randomUUID().toString());
				}
				pmma.setValue(pma.toString());
				
				path.getAnnotation().add(pmma);
			}

			establishNextIndex();

			// Create interaction associated with message signature
			createInteraction(elem.getMessageSignature(), dc.getFromRole(), dc.getToRoles(),
					elem.getAnnotations());

			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * directed choice.
		 * 
		 * @param elem The choice
		 */
		public void end(org.scribble.protocol.model.OnMessage elem) {
			// Transfer outstanding nodes in 'pendingNextIndex' to the
			// cache associated with the choice
			java.util.List<Object> cache=getCache(elem.getParent());

			cache.addAll(m_pendingNextIndex);

			m_pendingNextIndex.clear();
		}
		
		protected java.util.List<Object> getCache(ModelObject elem) {
			java.util.List<Object> ret=m_nodeCache.get(elem);
			
			if (ret == null) {
				ret = new java.util.Vector<Object>();
				m_nodeCache.put(elem, ret);
			}
			
			return(ret);
		}
		
		/**
		 * This method visits a run component.
		 * 
		 * @param elem The run
		 */
		public boolean start(org.scribble.protocol.model.Run elem) {
			
			startActivity(elem);

			org.scribble.protocol.monitor.model.Scope node=
						new org.scribble.protocol.monitor.model.Scope();
			
			m_nodes.add(node);
						
			// Cache the node associated with the run
			m_nodeMap.put(elem, node);
			
			// Check if calling a local protocol
			org.scribble.protocol.model.Protocol inner=
					RunUtil.getInnerProtocol(elem.enclosingProtocol(), elem.getProtocolReference());
			
			if (inner != null) {
				java.util.List<Object> cache=m_nodeCache.get(inner);
				
				if (cache == null) {
					cache = new java.util.Vector<Object>();
					m_nodeCache.put(inner, cache);
				}
				
				cache.add(node);
			}
			
			/*
			if (elem.isInline()) {
				m_pendingNextIndex.add(node);
			}
			*/
			
			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * run.
		 * 
		 * @param elem The run
		 */
		public void end(Run elem) {
			
			Node node=
				(Node)m_nodeMap.get(elem);
			
			if (elem.isInline()) {
				m_pendingNextIndex.clear();
				
				if (node instanceof Scope) {
					((Scope)node).setInnerIndex(m_nodes.indexOf(node)+1);
				}
			}
			
			m_pendingNextIndex.add(node);
			
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * parallel.
		 * 
		 * @param elem The parallel
		 * @return Whether to process the contents
		 */
		public boolean start(org.scribble.protocol.model.Parallel elem) {
			
			startActivity(elem);

			org.scribble.protocol.monitor.model.Parallel node=
						new org.scribble.protocol.monitor.model.Parallel();
			
			m_nodes.add(node);
						
			// Cache the node associated with the parallel
			m_nodeMap.put(elem, node);

			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * parallel.
		 * 
		 * @param elem The parallel
		 */
		public void end(org.scribble.protocol.model.Parallel elem) {
			Node node=
				(Node)m_nodeMap.get(elem);
			
			m_pendingNextIndex.add(node);
			
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * Unordered.
		 * 
		 * @param elem The Unordered
		 * @return Whether to process the contents
		 */
		public boolean start(Unordered elem) {
			
			startActivity(elem);

			org.scribble.protocol.monitor.model.Parallel node=
						new org.scribble.protocol.monitor.model.Parallel();
			
			m_nodes.add(node);
						
			// Cache the node associated with the parallel
			m_nodeMap.put(elem, node);

			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * Unordered.
		 * 
		 * @param elem The Unordered
		 */
		public void end(Unordered elem) {
			Node node=
				(Node)m_nodeMap.get(elem);
			
			m_pendingNextIndex.add(node);
			
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * repeat.
		 * 
		 * @param elem The repeat
		 * @return Whether to process the contents
		 */
		public boolean start(Repeat elem) {		

			startActivity(elem);

			Decision node=new Decision();
			
			m_nodes.add(node);
						
			// Cache the node associated with the choice
			m_nodeMap.put(elem, node);

			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * repeat.
		 * 
		 * @param elem The repeat
		 */
		public void end(Repeat elem) {	
			Node node=
				(Node)m_nodeMap.get(elem);
			
			// Set the current 'pending next index' entries to the
			// repeat node
			establishNextIndex(m_nodes.indexOf(node));
			
			if (node instanceof Decision) {
				((Decision)node).setInnerIndex(m_nodes.indexOf(node)+1);
			}
			
			m_pendingNextIndex.add(node);
			
			endActivity(elem);
		}
		
		/**
		 * This method visits a recursion component.
		 * 
		 * @param elem The recursion
		 */
		public void accept(Recursion elem) {

			startActivity(elem);
			
			Call node=new Call();
	
			if (m_recurPosition.containsKey(elem.getLabel())) {
				//establishNextIndex(m_recurPosition.get(elem.getLabel()));
				
				node.setCallIndex(m_recurPosition.get(elem.getLabel()));
			} else {
				// TODO: Error reporting
			}

			m_nodes.add(node);					

			m_pendingNextIndex.add(node);
			
			// TODO: Not sure how this should be handled when in unordered construct??
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * labelled block.
		 * 
		 * @param elem The labelled block
		 * @return Whether to process the contents
		 */
		public boolean start(RecBlock elem) {
			
			startActivity(elem);
			
			// Register current node against labelled block label
			m_recurPosition.put(elem.getLabel(), m_nodes.size());

			org.scribble.protocol.monitor.model.Scope node=
				new org.scribble.protocol.monitor.model.Scope();
			
			m_nodes.add(node);
						
			// Cache the node associated with the run
			m_nodeMap.put(elem, node);
			
			m_pendingNextIndex.add(node);
			
			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * labelled block.
		 * 
		 * @param elem The labelled block
		 */
		public void end(RecBlock elem) {
			Node node=
				(Node)m_nodeMap.get(elem);
			
			m_pendingNextIndex.clear();
			
			if (node instanceof Scope) {
				((Scope)node).setInnerIndex(m_nodes.indexOf(node)+1);
			}

			m_pendingNextIndex.add(node);
			
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * try escape.
		 * 
		 * @param elem The try escape
		 * @return Whether to process the contents
		 */
		public boolean start(org.scribble.protocol.model.Try elem) {
			
			startActivity(elem);

			org.scribble.protocol.monitor.model.Try node=
					new org.scribble.protocol.monitor.model.Try();
			
			m_nodes.add(node);
						
			// Cache the node associated with the try
			m_nodeMap.put(elem, node);
			
			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * try escape.
		 * 
		 * @param elem The try escape
		 */
		public void end(org.scribble.protocol.model.Try elem) {
			Node node=
				(Node)m_nodeMap.get(elem);
			
			m_pendingNextIndex.clear();
			
			if (node instanceof Scope) {
				((Scope)node).setInnerIndex(m_nodes.indexOf(node)+1);
			}

			m_pendingNextIndex.add(node);
			
			endActivity(elem);
		}
		
		/**
		 * This method indicates the start of a
		 * try escape.
		 * 
		 * @param elem The try escape
		 * @return Whether to process the contents
		 */
		public boolean start(Catch elem) {
			org.scribble.protocol.monitor.model.Try node=
					(org.scribble.protocol.monitor.model.Try)m_nodeMap.get(elem.getParent());
			
			m_pendingNextIndex.clear();
			
			java.util.List<Object> store=new java.util.Vector<Object>();
			
			for (Interaction interaction : elem.getInteractions()) {
				// Set the tryNode to establish a catch block 
				node.getCatchIndex().add(getCurrentIndex());
				
				accept(interaction);
				
				store.addAll(m_pendingNextIndex);
				m_pendingNextIndex.clear();
			}
			
			m_pendingNextIndex.addAll(store);

			return(true);
		}
		
		/**
		 * This method indicates the end of a
		 * try escape.
		 * 
		 * @param elem The try escape
		 */
		public void end(Catch elem) {
			// End of catch block scope, so clear the 'pending next index' nodes
			m_pendingNextIndex.clear();
		}
		
		/**
		 * This method returns the protocol description exported from the visited ProtocolModel.
		 * 
		 * @return The protocol description
		 */
		public org.scribble.protocol.monitor.model.Description getDescription() {
			Description ret=new Description();
			
			// Initialise the choice paths. This is done here, rather than in the
			// endWhen or endChoice, as the nextIndex on a when path may not be
			// initialised until outside the choice.
			for (org.scribble.protocol.monitor.model.Choice choice : m_choicePaths.keySet()) {
				java.util.List<Path> pathBuilders=m_choicePaths.get(choice);
				
				for (Path pathBuilder : pathBuilders) {
					choice.getPath().add(pathBuilder);
				}
			}

			// Same reason as for choice
			for (org.scribble.protocol.monitor.model.Parallel parallel : m_parallelPaths.keySet()) {
				java.util.List<Path> pathBuilders=m_parallelPaths.get(parallel);
				
				for (Path pathBuilder : pathBuilders) {
					parallel.getPath().add(pathBuilder);
				}
			}

			for (Node b : m_nodes) {
				ret.getNode().add(b);
			}
			
			return(ret);
		}
	}
}
