/**
 * 
 */
package pt.ua.sd.Configuration;

import java.io.Serializable;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class Configs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1045228254506689226L;
	Integer seasonDuration, simulationDuration, gfactor, capacityEcoSystem,
			nCompanies, nBoats, boatsCapacity, boatsRadarSensibility, nShools,
			catchPercentage;

	public Integer getBoatsCapacity() {
		return boatsCapacity;
	}

	public void setBoatsCapacity(Integer boatsCapacity) {
		this.boatsCapacity = boatsCapacity;
	}

	public Integer getBoatsRadarSensibility() {
		return boatsRadarSensibility;
	}

	public void setBoatsRadarSensibility(Integer boatsRadarSensibility) {
		this.boatsRadarSensibility = boatsRadarSensibility;
	}

	public Integer getCapacityEcoSystem() {
		return capacityEcoSystem;
	}

	public void setCapacityEcoSystem(Integer capacityEcoSystem) {
		this.capacityEcoSystem = capacityEcoSystem;
	}

	public Integer getCatchPercentage() {
		return catchPercentage;
	}

	public void setCatchPercentage(Integer catchPercentage) {
		this.catchPercentage = catchPercentage;
	}

	public Integer getGfactor() {
		return gfactor;
	}

	public void setGfactor(Integer gfactor) {
		this.gfactor = gfactor;
	}

	public Integer getnBoats() {
		return nBoats;
	}

	public void setnBoats(Integer nBoats) {
		this.nBoats = nBoats;
	}

	public Integer getnCompanies() {
		return nCompanies;
	}

	public void setnCompanies(Integer nCompanies) {
		this.nCompanies = nCompanies;
	}

	public Integer getnShools() {
		return nShools;
	}

	public void setnShools(Integer nShools) {
		this.nShools = nShools;
	}

	public Integer getSeasonDuration() {
		return seasonDuration;
	}

	public void setSeasonDuration(Integer seasonDuration) {
		this.seasonDuration = seasonDuration;
	}

	public Integer getSimulationDuration() {
		return simulationDuration;
	}

	public void setSimulationDuration(Integer simulationDuration) {
		this.simulationDuration = simulationDuration;
	}

}
