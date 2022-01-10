package herd;

import java.io.FileNotFoundException;

/**
 * This class implements a custom exception.
 * @author Izhar Ali
 *
 */
public class StatisticDataNotFoundException extends FileNotFoundException  {

	private static final long serialVersionUID = -2522594321374814263L;

	/**
	 * Throw a custom exception.
	 * @param message the exception message.
	 */
	public StatisticDataNotFoundException(String message) {
		super(message);
	}
}
