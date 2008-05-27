package org.openwms.domain.common;

public interface ITransportUnit {

	/**
	 * Get the actualLocation.
	 * 
	 * @return the actualLocation.
	 */
	public abstract ILocation getActualLocation();

	/**
	 * Set the actualLocation.
	 * 
	 * @param actualLocation
	 *            The actualLocation to set.
	 */
	public abstract void setActualLocation(Location actualLocation);

	/**
	 * Get the targetLocation.
	 * 
	 * @return the targetLocation.
	 */
	public abstract ILocation getTargetLocation();

	/**
	 * Set the targetLocation.
	 * 
	 * @param targetLocation
	 *            The targetLocation to set.
	 */
	public abstract void setTargetLocation(Location targetLocation);

	/**
	 * Get the id.
	 * 
	 * @return the id.
	 */
	public abstract long getId();

	/**
	 * Get the unitId of this <code>TransportUnit</code>.
	 * 
	 * @return the unitId.
	 */
	public abstract String getUnitId();

	/**
	 * Set the unitId of this <code>TransportUnit</code>.
	 * 
	 * @param unitId
	 *            The unitId to set.
	 */
	public abstract void setUnitId(String unitId);

}