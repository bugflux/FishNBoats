/**
 * 
 */
package pt.ua.sd.distribution;

import java.io.Serializable;

/**
 * A distribution config is actually a running configuration, and contains
 * information of how many boats, companies, shoals are, the size of the board,
 * etc...
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DistributionConfig implements Serializable {

	public static final int DISTRIBUTION_SERVER_PORT = 22149;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2088872405552159607L;

	protected int nboats = 5, ncompanies = 2, nshoals = 5;
	protected int boatPeriod = 111, shoalPeriod = 222;
	protected int height = 11, width = 11;
	protected int seasonMoves = 40;
	protected int boatCapacity = 100;
	protected int shoalSize = 2000;
	protected int growing_factor = 2;// 5;
	protected double eco_system = 0.001;// 0.001;
	protected double catchPercentage = 0.3;
	protected int minShoalDetectable = 100;
	protected int nCampaign = 3;
	protected String logFile = "log.txt";

	/**
	 * @return the nboats
	 */
	public int getNboats() {
		return nboats;
	}

	/**
	 * @param nboats
	 *            the nboats to set
	 */
	public void setNboats(int nboats) {
		this.nboats = nboats;
	}

	/**
	 * @return the ncompanies
	 */
	public int getNcompanies() {
		return ncompanies;
	}

	/**
	 * @param ncompanies
	 *            the ncompanies to set
	 */
	public void setNcompanies(int ncompanies) {
		this.ncompanies = ncompanies;
	}

	/**
	 * @return the nshoals
	 */
	public int getNshoals() {
		return nshoals;
	}

	/**
	 * @param nshoals
	 *            the nshoals to set
	 */
	public void setNshoals(int nshoals) {
		this.nshoals = nshoals;
	}

	/**
	 * @return the boatPeriod
	 */
	public int getBoatPeriod() {
		return boatPeriod;
	}

	/**
	 * @param boatPeriod
	 *            the boatPeriod to set
	 */
	public void setBoatPeriod(int boatPeriod) {
		this.boatPeriod = boatPeriod;
	}

	/**
	 * @return the shoalPeriod
	 */
	public int getShoalPeriod() {
		return shoalPeriod;
	}

	/**
	 * @param shoalPeriod
	 *            the shoalPeriod to set
	 */
	public void setShoalPeriod(int shoalPeriod) {
		this.shoalPeriod = shoalPeriod;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the seasonMoves
	 */
	public int getSeasonMoves() {
		return seasonMoves;
	}

	/**
	 * @param seasonMoves
	 *            the seasonMoves to set
	 */
	public void setSeasonMoves(int seasonMoves) {
		this.seasonMoves = seasonMoves;
	}

	/**
	 * @return the boatCapacity
	 */
	public int getBoatCapacity() {
		return boatCapacity;
	}

	/**
	 * @param boatCapacity
	 *            the boatCapacity to set
	 */
	public void setBoatCapacity(int boatCapacity) {
		this.boatCapacity = boatCapacity;
	}

	/**
	 * @return the shoalSize
	 */
	public int getShoalSize() {
		return shoalSize;
	}

	/**
	 * @param shoalSize
	 *            the shoalSize to set
	 */
	public void setShoalSize(int shoalSize) {
		this.shoalSize = shoalSize;
	}

	/**
	 * @return the growing_factor
	 */
	public int getGrowing_factor() {
		return growing_factor;
	}

	/**
	 * @param growing_factor
	 *            the growing_factor to set
	 */
	public void setGrowing_factor(int growing_factor) {
		this.growing_factor = growing_factor;
	}

	/**
	 * @return the eco_system
	 */
	public double getEco_system() {
		return eco_system;
	}

	/**
	 * @param eco_system
	 *            the eco_system to set
	 */
	public void setEco_system(double eco_system) {
		this.eco_system = eco_system;
	}

	/**
	 * @return the catchPercentage
	 */
	public double getCatchPercentage() {
		return catchPercentage;
	}

	/**
	 * @param catchPercentage
	 *            the catchPercentage to set
	 */
	public void setCatchPercentage(double catchPercentage) {
		this.catchPercentage = catchPercentage;
	}

	/**
	 * @return the minShoalDetectable
	 */
	public int getMinShoalDetectable() {
		return minShoalDetectable;
	}

	/**
	 * @param minShoalDetectable
	 *            the minShoalDetectable to set
	 */
	public void setMinShoalDetectable(int minShoalDetectable) {
		this.minShoalDetectable = minShoalDetectable;
	}

	/**
	 * @return the nCampaign
	 */
	public int getnCampaign() {
		return nCampaign;
	}

	/**
	 * @param nCampaign
	 *            the nCampaign to set
	 */
	public void setnCampaign(int nCampaign) {
		this.nCampaign = nCampaign;
	}

	/**
	 * @return the logFile
	 */
	public String getLogFile() {
		return logFile;
	}

	/**
	 * @param logFile
	 *            the logFile to set
	 */
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}
}
