package org.scribble.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ResourceLocator implements IResourceLocator
{
	//public abstract Resource getResource(String path);

	//public static InputStreamResource getResourceByFullPath(String path)
	public static InputStreamResource getResourceByFullPath(Path path)
	{
		/*File f = new File(path);
		if (!f.isFile())
		{
			throw new RuntimeException("File couldn't be opened: " + path);
		}

		//return openResource(path, f);
		return new InputStreamResource(path, getFileInputStream(f));*/
		if (!Files.exists(path))
		{
			throw new RuntimeException("File couldn't be opened: " + path);
		}
		return new InputStreamResource(path, getFileInputStream(path));
	}
	
	protected static InputStream getFileInputStream(Path path)
	{
		try
		{
			return Files.newInputStream(path);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	//protected static Resource openResource(String path, File f)
	protected static FileInputStream getFileInputStream(File f)
	{
		try
		{
			// ret = new InputStreamResource(relativePath, new java.io.FileInputStream(f));
			//return new InputStreamResource(path, new FileInputStream(f)); // RAY
			return new FileInputStream(f);
		}
		catch (Exception e)
		{
			//LOG.log(Level.SEVERE, "Failed to create file input stream for '" + f + "'", e);
			throw new RuntimeException("Failed to create file input stream for '" + f + "'", e);
		}
		//return null;
	}

	/*protected static File openFile(String path, boolean check)
	{
		File f = new File(path);
		if (check && !f.isFile())
		{
			throw new RuntimeException("File couldn't be opened: " + path);
		}
		return f;
	}*/
}
