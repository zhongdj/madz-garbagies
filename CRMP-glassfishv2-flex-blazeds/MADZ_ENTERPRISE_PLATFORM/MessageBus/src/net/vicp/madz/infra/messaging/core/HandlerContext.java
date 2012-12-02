package net.vicp.madz.infra.messaging.core;

/**
 * 
 * @author Barry Zhong
 * @version 1.0
 */
public class HandlerContext implements IHandlerContext {

	private boolean finished;

	public HandlerContext() {
	}

	/**
	 * isFinished
	 * 
	 * @return boolean
	 * @todo Implement this com.nsn.messaging.core.IHandlerContext method
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * setFinished
	 * 
	 * @param flag
	 *            boolean
	 * @return boolean
	 * @todo Implement this com.nsn.messaging.core.IHandlerContext method
	 */
	public void setFinished(boolean flag) {
		this.finished = flag;
	}
}
