package bsh;

public interface NameSourceListener
{
	public void nameSourceChanged(NameSource src);
	/**
	 * Provide feedback on the progress of mapping a namespace
	 * 
	 * @param msg
	 *            is an update about what's happening
	 * @perc is an integer in the range 0-100 indicating percentage done
	 *       public void nameSourceMapping( NameSource src, String msg, int
	 *       perc );
	 */
}
