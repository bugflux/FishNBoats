package pt.ua.sd.gui;

import java.awt.Point;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.boat.MBoat;
import pt.ua.sd.boat.TBoat;
import pt.ua.sd.configuration.Configs;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.diroper.MDirOper;
import pt.ua.sd.diroper.TDirOper;
import pt.ua.sd.log.MLog;
import pt.ua.sd.log.TLogFlusher;
import pt.ua.sd.ocean.MOcean;
import pt.ua.sd.shoal.MShoal;
import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;
import pt.ua.sd.shoal.TShoal;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class PortoAveiro {
	private static PortoAveiro instance;

	private PortoAveiro(){
	}
	
	public static PortoAveiro getInstance() {
		if (instance == null)
			instance = new PortoAveiro();
		return instance;
	}

	/**
	 * @param args
	 */
	public void startSimulation(Configs confs) {
//		final int nboats = 5, ncompanies = 2, nshoals = 5;
//		final int boatPeriod = 100, shoalPeriod = 200;
//		final int height = 11, width = 11;
//		final int maxShoalPerSquare = 1, maxBoatsPerSquare = 3;
//		final int seasonMoves = 40;
//		final int boatCapacity = 1000;
//		final int initialFish = 2000;
//		final int shoalSize = initialFish;
//		final int growing_factor = 2;// 5;
//		final double eco_system = 0.001;// 0.001;
//		final double catchPercentage = 0.3;
//		final int minShoalDetectable = 100;
//		final int nCampaign = 3;


		final int nboats = confs.getnBoats(), ncompanies = confs.getnCompanies(), nshoals = confs.getnSchools();
		final int boatPeriod = confs.getSimulationSpeed()/2, shoalPeriod = confs.getSimulationSpeed();
		final int height = 11, width = 11;
		final int maxShoalPerSquare = 1, maxBoatsPerSquare = 3;
		final int seasonMoves = confs.getSeasonDuration();
		final int boatCapacity = confs.getBoatsCapacity();
		final int initialFish = confs.getShoalInicialSize();
		final int shoalSize = initialFish;
		final int growing_factor = confs.getGfactor().intValue();// 5;
		final double eco_system = confs.getCapacityEcoSystem();// 0.001;
		final double catchPercentage = confs.getCatchPercentage()/100;
		final int minShoalDetectable = confs.getBoatsRadarSensibility();
		final int nCampaign = confs.getSimulationDuration();



		// TODO: read these ^ from a graphical interface

		// Logger
		MLog logger = MLog.getInstance();
		TLogFlusher loggFlusher = new TLogFlusher(System.out);

		// Ocean
		Point wharf = new Point(0, 0);
		Point reproducingZone = new Point(width - 1, height - 1);
		MOcean oceano = new MOcean(height, width, maxShoalPerSquare,
				maxBoatsPerSquare, wharf, reproducingZone);

		// DirOper
		MDirOper mDirOpers[] = new MDirOper[ncompanies];
		TDirOper tDirOpers[] = new TDirOper[ncompanies];
		DirOperStats sDirOpers[] = new DirOperStats[ncompanies];

		// Shoal
		MShoal mShoals[] = new MShoal[nshoals];
		ShoalStats sShoals[] = new ShoalStats[nshoals];
		TShoal tShoals[] = new TShoal[nshoals];

		// Boat
		MBoat mBoats[][] = new MBoat[ncompanies][nboats];
		BoatStats sBoats[][] = new BoatStats[ncompanies][nboats];
		TBoat tBoats[][] = new TBoat[ncompanies][nboats];

		// Stats and Monitors
		for (int r = 0; r < nshoals; r++) {
			sShoals[r] = new ShoalStats(new ShoalId(r),
					INTERNAL_STATE_SCHOOL.spawning, new Point(reproducingZone),
					shoalSize, minShoalDetectable);
			mShoals[r] = new MShoal(sShoals[r].getId(), ncompanies);
			tShoals[r] = new TShoal((ShoalStats) sShoals[r].clone(),
					shoalPeriod, seasonMoves, nCampaign, mShoals[r], oceano,
					mDirOpers, growing_factor, eco_system, catchPercentage);
			oceano.addShoal(sShoals[r], mShoals[r], reproducingZone);
		}

		for (int r = 0; r < ncompanies; r++) {
			sDirOpers[r] = new DirOperStats(
					INTERNAL_STATE_DIROPER.starting_a_campaign,
					new DirOperId(r));
			mDirOpers[r] = new MDirOper(sDirOpers[r].getId(), nshoals, nboats);
			tDirOpers[r] = new TDirOper(logger, oceano, mDirOpers[r],
					mBoats[r], mShoals, (DirOperStats) sDirOpers[r].clone());

			for (int s = 0; s < nboats; s++) {
				sBoats[r][s] = new BoatStats(new BoatId(r, s),
						INTERNAL_STATE_BOAT.at_the_wharf, new Point(wharf),
						boatCapacity);
				mBoats[r][s] = new MBoat(sBoats[r][s].getId());
				tBoats[r][s] = new TBoat((BoatStats) sBoats[r][s].clone(),
						boatPeriod, mDirOpers[r], oceano, mBoats[r][s]);
				oceano.addBoat(sBoats[r][s], wharf);
			}
		}

		// threads
		for (int r = 0; r < nshoals; r++) {
			tShoals[r].start();
		}

		for (int r = 0; r < ncompanies; r++) {
			for (int s = 0; s < nboats; s++) {
				tBoats[r][s].start();
			}
		}

		for (int r = 0; r < ncompanies; r++) {
			tDirOpers[r].start();
		}

		loggFlusher.start();

		// join
		try {
			for (int r = 0; r < nshoals; r++) {
				tShoals[r].join();
			}

			for (int r = 0; r < ncompanies; r++) {
				for (int s = 0; s < nboats; s++) {
					tBoats[r][s].join();
				}
			}

			for (int r = 0; r < ncompanies; r++) {
				tDirOpers[r].join();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.println("All joined");
	}
}