/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.del.global;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.DependencyMap;
import org.scribble.ast.context.global.GProtocolDeclContext;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.core.job.JobArgs;
import org.scribble.core.lang.ProtocolMod;
import org.scribble.core.lang.global.GProtocol;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.name.GProtocolName;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.global.GSeq;
import org.scribble.del.ModuleDel;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.util.ScribException;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.context.env.ProjectionEnv;
import org.scribble.visit.util.RoleCollector;
import org.scribble.visit.validation.GProtocolValidator;

public class GProtocolDeclDel extends ProtocolDeclDel<Global> implements GDel
{
	public GProtocolDeclDel()
	{

	}
	
	@Override
	public GProtocol translate(ScribNode n, GTypeTranslator t)
			throws ScribException
	{
		GProtocolDecl source = (GProtocolDecl) n;
		Module m = (Module) n.getParent();
		List<ProtocolMod> mods = source.getModifierListChild().getModList().stream()
				.map(x -> ProtocolMod.fromAst(x)).collect(Collectors.toList());
		GProtocolName fullname = new GProtocolName(m.getFullModuleName(),
				source.getHeaderChild().getDeclName());
		List<Role> rs = source.getRoles();
		List<MemberName<? extends NonRoleParamKind>> ps = source.getHeaderChild()
				.getParamDeclListChild().getParameters();  // CHECKME: make more uniform with source::getRoles ?
		GSeq body = (GSeq) source.getDefChild().getBlockChild().visitWith(t);
		return new GProtocol(source, mods, fullname, rs, ps, body);
	}
	
	@Override
	public GProtocolDeclContext getProtocolDeclContext()
	{
		return (GProtocolDeclContext) super.getProtocolDeclContext();
	}

	@Override
	protected GProtocolDeclDel copy()
	{
		return new GProtocolDeclDel();
	}

	@Override
	protected void addSelfDependency(ProtocolDeclContextBuilder builder,
			ProtocolName<?> proto, Role role)
	{
		builder.addGlobalProtocolDependency(role, (GProtocolName) proto, role);
	}
	
	@Override
	public GProtocolDecl
			leaveProtocolDeclContextBuilding(ScribNode parent, ScribNode child,
					ProtocolDeclContextBuilder builder, ScribNode visited)
					throws ScribException
	{
		GProtocolDecl gpd = (GProtocolDecl) visited;
		GProtocolDeclContext gcontext = new GProtocolDeclContext(
				builder.getGlobalProtocolDependencyMap());
		GProtocolDeclDel del = (GProtocolDeclDel) setProtocolDeclContext(gcontext);
		return (GProtocolDecl) gpd.del(del);
	}

	@Override
	public ScribNode leaveRoleCollection(ScribNode parent, ScribNode child,
			RoleCollector coll, ScribNode visited) throws ScribException
	{
		GProtocolDecl gpd = (GProtocolDecl) visited;

		// Need to do here (e.g. RoleDeclList too early, def not visited yet)
		// Currently only done for global, local does roledecl fixing after role collection -- should separate this check to a later pass after context building
		// Maybe relax to check only occs.size() > 1
		List<Role> decls = gpd.getHeaderChild().getRoleDeclListChild().getRoles();
		Set<Role> occs = coll.getNames();
		if (occs.size() != decls.size()) 
		{
			decls.removeAll(occs);
			throw new ScribException(
					gpd.getHeaderChild().getRoleDeclListChild().getSource(),
					"Unused role decl(s) in " + gpd.getHeaderChild().getDeclName() + ": "
							+ decls);
		}

		return super.leaveRoleCollection(parent, child, coll, gpd);
	}

	@Override
	public GProtocolDecl leaveProjection(ScribNode parent, ScribNode child,
			Projector proj, ScribNode visited) throws ScribException
	{
		AstFactory af = proj.lang.config.af;

		Module root = proj.lang.getContext().getModule(proj.getModuleContext().root);
		GProtocolDecl gpd = (GProtocolDecl) visited;
		GProtocolHeader gph = gpd.getHeaderChild();
		Role self = proj.peekSelf();

		LProtocolNameNode pn = Projector.makeProjectedSimpleNameNode(af,
				gph.getSource(), gph.getDeclName(), self);
		RoleDeclList roledecls = gph.getRoleDeclListChild().project(af, self);
		NonRoleParamDeclList paramdecls = gph.getParamDeclListChild().project(af,
				self);
		//LProtocolHeader hdr = af.LProtocolHeader(gpd.header.getSource(), pn, roledecls, paramdecls);  // FIXME: make a header del and move there?
		LProtocolHeader hdr = gph.project(af, self, pn, roledecls, paramdecls);
		LProtocolDef def = (LProtocolDef) ((ProjectionEnv) gpd.getDefChild().del()
				.env()).getProjection();
		LProtocolDecl lpd = gpd.project(af, root, self, hdr, def);  // CHECKME: is root (always) the correct module? (wrt. LProjectionDeclDel?)
		
		Map<GProtocolName, Set<Role>> deps = ((GProtocolDeclDel) gpd.del())
				.getGlobalProtocolDependencies(self);
		Module projected = ((ModuleDel) root.del()).createModuleForProjection(proj,
				root, gpd, lpd, deps);
		proj.addProjection(gpd.getFullMemberName(root), self, projected);
		return gpd;
	}

	protected Map<GProtocolName, Set<Role>> getGlobalProtocolDependencies(Role self)
	{
		DependencyMap<GProtocolName> deps = getProtocolDeclContext().getDependencyMap();
		return deps.getDependencies().get(self);
	}
	
	@Override
	public void enterValidation(ScribNode parent, ScribNode child,
			GProtocolValidator checker) throws ScribException
	{
		GProtocolDecl gpd = (GProtocolDecl) child;
		if (gpd.isAux())
		{
			return;
		}

		GProtocolName fullname = gpd.getFullMemberName((Module) parent);
		if (checker.job.config.args.get(JobArgs.spin))
		{
			if (checker.job.config.args.get(JobArgs.fair))
			{
				throw new RuntimeException(
						"[TODO]: -spin currently does not support fair ouput choices.");
			}
			GProtocol.validateBySpin(checker.job, fullname);
		}
		else
		{
			GProtocol.validateByScribble(checker.job, fullname, true);
			if (!checker.job.config.args.get(JobArgs.fair))
			{
				checker.lang.debugPrintln(
						"(" + fullname + ") Validating with \"unfair\" output choices.. ");
				GProtocol.validateByScribble(checker.job, fullname, false);  // TODO: only need to check progress, not full validation
			}
		}
	}
}

