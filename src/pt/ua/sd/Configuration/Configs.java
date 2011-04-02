/**
 * 
 */
package pt.ua.sd.configuration;

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
	Integer seasonDuration, simulationDuration, 
			nCompanies, nBoats, boatsCapacity, boatsRadarSensibility, nSchools, shoalInicialSize, simulationSpeed;

	Double gfactor,catchPercentage,capacityEcoSystem;

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

	public Double getCapacityEcoSystem() {
		return capacityEcoSystem;
	}

	public void setCapacityEcoSystem(Double capacityEcoSystem) {
		this.capacityEcoSystem = capacityEcoSystem;
	}

	public Double getCatchPercentage() {
		return catchPercentage;
	}

	public void setCatchPercentage(Double catchPercentage) {
		this.catchPercentage = catchPercentage;
	}

	public Double getGfactor() {
		return gfactor;
	}

	public void setGfactor(Double gfactor) {
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

	public Integer getnSchools() {
		return nSchools;
	}

	public void setnSchools(Integer nShools) {
		this.nSchools = nShools;
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

	public Integer getShoalInicialSize() {
		return shoalInicialSize;
	}

	public void setShoalInicialSize(Integer shoalInicialSize) {
		this.shoalInicialSize = shoalInicialSize;
	}

	public Integer getSimulationSpeed() {
		return simulationSpeed;
	}

	public void setSimulationSpeed(Integer simulationSpeed) {
		this.simulationSpeed = simulationSpeed;
	}

	

}
