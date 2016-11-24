package smtp;

public class EraserThread extends Thread
{
	private boolean stop = true;

	public void run()
	{
		try
		{
			while (this.stop)
			{
				System.out.print("\010 ");
				Thread.sleep(1);
			}
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void stopMasking()
	{
		this.stop = false;
	}
}
