package ast.local;

import java.util.HashMap;
import java.util.Map;

import ast.AstFactory;
import ast.PayloadType;
import ast.name.MessageLab;
import ast.name.RecVar;
import ast.name.Role;

public class LocalTypeParser
{
	private final AstFactory factory = new AstFactory();
	
	public LocalType parse(String lt)
	{
		lt = lt.trim();
		if (lt.startsWith("mu "))
		{
			RecVar rv = this.factory.RecVar(lt.substring(3, lt.indexOf(".")).trim());
			LocalType body = parse(lt.substring(lt.indexOf(".") + 1, lt.length()));
			return this.factory.LocalRec(rv, body);
		}
		else if (lt.startsWith("#"))
		{
			return this.factory.RecVar(lt.substring(1, lt.length()));
		}
		else if (lt.equals("end"))
		{
			return this.factory.LocalEnd();
		}
		else if (lt.contains("!") || lt.contains("?"))
		{
			Map<MessageLab, LocalCase> cases = parseLocalCases(lt.substring(lt.indexOf("{") + 1, lt.lastIndexOf("}")));
			int o = lt.indexOf("!");
			int i = lt.indexOf("?");
			if (i == -1 || (o > -1 && o < i))
			{
				Role dest = this.factory.Role(lt.substring(0, o).trim());
				return this.factory.LocalSelect(dest, cases);
			}
			else
			{
				Role src = this.factory.Role(lt.substring(0, i).trim());
				return this.factory.LocalSelect(src, cases);
			}
		}
		else
		{
			return null;
		}
	}
	
	private Map<MessageLab, LocalCase> parseLocalCases(String s)
	{
		s = s.trim();
		String[] cs = s.split(",");
		Map<MessageLab, LocalCase> cases = new HashMap<>();
		for (String c : cs)
		{
			MessageLab lab = this.factory.MessageLab(c.substring(0, c.indexOf("(")).trim());
			String p = c.substring(c.indexOf("(") + 1, c.indexOf(")")).trim();
			PayloadType pay = null;
			if (!p.equals(""))
			{
				pay = parse(p);
				if (pay == null)
				{
					pay = this.factory.BaseType(p);
				}
			}
			LocalType cont = parse(c.substring(c.indexOf(":") + 1, c.length()).trim());
			cases.put(lab, this.factory.LocalCase(pay, cont));
		}
		return cases;
	}
}
