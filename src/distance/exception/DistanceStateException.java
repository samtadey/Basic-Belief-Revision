/**
 * 
 */
package distance.exception;

/**
 * @author sam_t
 *
 */
public class DistanceStateException extends Exception {
	
	public DistanceStateException(String message) {
		super(message);
	}
	
	public DistanceStateException(String message, Throwable err) {
		super(message, err);
	}

}
