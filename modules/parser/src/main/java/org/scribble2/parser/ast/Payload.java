package scribble2.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;

import scribble2.main.ScribbleException;
import scribble2.visit.NodeVisitor;
import scribble2.visit.Projector;
import scribble2.visit.env.ProjectionEnv;

public class Payload extends AbstractNode
{
	//public static final Payload EMPTY_PAYLOAD = new Payload(null, Collections.<PayloadElement> emptyList());

	public final List<PayloadElement> payloadelems;

	public Payload(CommonTree ct, List<PayloadElement> payloadelems)
	{
		super(ct);
		this.payloadelems = new LinkedList<>(payloadelems);
	}

	// Basically a copy without the AST
	@Override
	public Payload leaveProjection(Projector proj) //throws ScribbleException
	{
		List<PayloadElement> payloadelems = 
				this.payloadelems.stream().map((pe) -> (PayloadElement) ((ProjectionEnv) pe.getEnv()).getProjection()).collect(Collectors.toList());	
		Payload projection = new Payload(null, payloadelems);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	@Override
	public Payload visitChildren(NodeVisitor nv) throws ScribbleException
	{
		if (isEmpty())
		{
			return this;
		}
		List<PayloadElement> pes = visitChildListWithClassCheck(this, this.payloadelems, nv);
		return new Payload(this.ct, pes);
	}

	public boolean isEmpty()
	{
		return this.payloadelems.isEmpty();
	}

	@Override
	public String toString()
	{
		String s = "(";
		if (!isEmpty())
		{
			s += this.payloadelems.get(0).toString();
			for (PayloadElement pe : this.payloadelems.subList(1, this.payloadelems.size()))
			{
				s += ", " + pe;
			}
		}
		return s + ")";
	}
}
