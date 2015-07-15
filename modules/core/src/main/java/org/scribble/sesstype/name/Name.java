package org.scribble.sesstype.name;

import java.io.Serializable;
import java.util.Arrays;

import org.scribble.sesstype.kind.Kind;


public interface Name<K extends Kind> extends Serializable
{
	public K getKind();

	public int getElementCount();
	public boolean isEmpty();
	public boolean isPrefixed();

	public String[] getElements();
	public String[] getPrefixElements();
	public String getLastElement();

	static String[] compileElements(String[] cn, String n)
	{
		if (cn.length == 0)
		{
			return new String[] { n };
		}
		String[] elems = Arrays.copyOf(cn, cn.length + 1);
		elems[elems.length - 1] = n;
		return elems;
	}
}
