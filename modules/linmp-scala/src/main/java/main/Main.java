package main;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.scribble.main.ScribbleException;

import ast.ScribProtocolTranslator;

public class Main
{
	public static void main(String[] args) throws ScribbleException
	{
		Path mainmod = Paths.get(args[0]);
		String proto = "Proto";  // Hardcoded to look for protocol named "Proto"
		
		ScribProtocolTranslator sbp = new ScribProtocolTranslator();
		
		//System.out.println("Scribble:\n" + inlined + "\n");
		System.out.println("Translated:\n" + sbp.parse(mainmod, proto));
	}
}
