package org.scribble2.model.name;

import java.util.Arrays;

import org.scribble2.model.ModelNode;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.KindedName;
import org.scribble2.sesstype.name.KindedNamed;

public class KindedNameNode<K extends Kind> extends ModelNodeBase implements KindedNamed<K>
{
	public final K kind;

	//public final List<PrimitiveNameNode> names;
	protected final String[] elems;

	//public CompoundNameNodes(List<PrimitiveNameNode> names)
	public KindedNameNode(K kind, String... elems)
	{
		this.kind = kind;
		
		//this.names = new LinkedList<>(names);
		this.elems = elems;
	}
	
	@Override
	public KindedName<K> toName()
	{
		return new KindedName<K>(this.kind, this.elems);
	}
	
	/*public CompoundNameNodes(String name)
	{
		// Factor out
		List<PrimitiveNameNode> pnns = new LinkedList<>();
		for (String n : Arrays.asList(name.split("\\.")))
		{
			pnns.add(new PrimitiveNameNode(null, n));
		}
		this.names = pnns;
	}*/
	
	public String[] getElements()
	{
		return this.elems;
	}

	public int getElementCount()
	{
		return this.elems.length;
	}
	
	public boolean isEmpty()
	{
		return this.elems.length == 0;
	}
	
	protected boolean isPrefixed()
	{
		return this.elems.length > 1;
	}
	
	//protected abstract CompoundNameNodes getPrefix();
	/*protected CompoundNameNodes getPrefix()
	{
		return new CompoundNameNodes(getPrefixElements());
	}*/
	
	protected String getLastElement()
	{
		return this.elems[this.elems.length - 1];
	}
	
	// Usage should be guarded by isPrefixed
	protected String[] getPrefixElements()
	{
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}

	/*@Override
	public Name toName()
	{
		return new CompoundName(Kind.AMBIGUOUS, toStringArray());
	}*/

	// FIXME:
	@Override
	public String toString()
	{
		return toName().toString();
		//return Arrays.toString(this.elems);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || this.getClass() != o.getClass())
		{
			return false;
		}
		//return this.elems.equals(((CompoundNameNode) o).elems);
		return this.kind.equals(KindedNameNode.class.cast(o).kind) && this.elems.equals(KindedNameNode.class.cast(o).elems);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * hash + this.kind.hashCode();
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
	
	protected String[] toStringArray()
	{
		String[] names = new String[this.elems.length];
		for (int i = 0; i < this.elems.length; i++)
		{
			names[i] = this.elems[i];
		}
		return names;
	}

	@Override
	protected KindedNameNode<K> copy()
	{
		return new KindedNameNode<K>(this.kind, this.elems);
	}
	
	/*protected static String[] getIdentifiers(PrimitiveNameNode[] ns)
	{
		String[] ids = new String[ns.length];
		for (int i = 0; i < ns.length; i++)
		{
			ids[i] = ns[i].identifier;
		}
		return ids;
	}*/
	
	/*@Override
	public KindedNameNode<? extends Kind> del(ModelDelegate del)
	{
		@SuppressWarnings("unchecked")
		KindedNameNode<? extends Kind> n = (KindedNameNode<? extends Kind>) super.del(del);
		return n;
	}*/

	@Deprecated
	public static <K extends Kind> KindedNameNode<K> castKindedNameNode(K kind, ModelNode n)
	{
		if (!(n instanceof KindedNameNode))
		{
			throw new RuntimeException("Kinded name node " + kind + " cast error: " + n);
		}
		@SuppressWarnings("unchecked")
		KindedNameNode<? extends Kind> tmp1 = (KindedNameNode<? extends Kind>) n;
		if (!tmp1.kind.equals(kind))
		{
			throw new RuntimeException("Kinded name node " + kind + " cast error: " + n);
		}
		@SuppressWarnings("unchecked")
		KindedNameNode<K> tmp2 = (KindedNameNode<K>) n;
		return tmp2;
	}

	public static <T extends KindedNameNode<K>, K extends Kind> T castKindedNameNode(T cast, ModelNode n)
	{
		if (!(n.getClass().equals(cast.getClass())))
		{
			throw new RuntimeException("KindedNameNode " + cast + " class cast error: " + n);
		}
		@SuppressWarnings("unchecked")
		T tmp = (T) n;
		if (!tmp.kind.equals(cast.kind))
		{
			throw new RuntimeException("KindedNameNode " + cast.kind + " kind cast error: " + tmp.kind);
		}
		return tmp;
	}
}
