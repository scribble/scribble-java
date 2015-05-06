package org.scribble2.model.name;

import org.scribble2.model.ModelNode;
import org.scribble2.sesstype.kind.Kind;

public class SimpleKindedNameNode<K extends Kind> extends KindedNameNode<K>
{
	public final String identifier;
	
	public SimpleKindedNameNode(K kind, String elem)
	{
		super(kind, elem);
		this.identifier = elem;
	}
	
	/*public String getElement()
	{
		return this.getElements()[0];
	}*/
	
	@Override
	protected SimpleKindedNameNode<K> copy()
	{
		//return new SimpleKindedNameNode<K>(this.kind, getElement());
		return new SimpleKindedNameNode<K>(this.kind, this.identifier);
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

	@Deprecated
	public static <K extends Kind> SimpleKindedNameNode<K> castSimpleKindedNameNode(K kind, ModelNode n)
	{
		return (SimpleKindedNameNode<K>) KindedNameNode.castKindedNameNode(kind, n);
	}
}
