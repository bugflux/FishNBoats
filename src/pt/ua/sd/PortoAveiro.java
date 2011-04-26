package pt.ua.sd;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.boat.MBoat;
import pt.ua.sd.boat.TBoat;
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
import pt.ua.sd.shoal.network.ShoalClient;
import pt.ua.sd.shoal.network.ShoalProtocolRunnable;
import pt.ua.sd.shoal.network.ShoalServer;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class PortoAveiro {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int nboats = 5, ncompanies = 2, nshoals = 5;
		int boatPeriod = 111, shoalPeriod = 222;
		int height = 11, width = 11;
		int seasonMoves = 40;
		int boatCapacity = 100;
		int shoalSize = 2000;
		int growing_factor = 2;// 5;
		double eco_system = 0.001;// 0.001;
		double catchPercentage = 0.3;
		int minShoalDetectable = 100;
		int nCampaign = 3;

		int maxShoalPerSquare = 1, maxBoatsPerSquare = 3;

		File logFile = null;

		if (args.length != 0) {
			try {
				width = Integer.valueOf(args[0]);
				height = Integer.valueOf(args[1]);
				nCampaign = Integer.valueOf(args[2]);
				ncompanies = Integer.valueOf(args[3]);
				nboats = Integer.valueOf(args[4]);
				boatCapacity = Integer.valueOf(args[5]);
				boatPeriod = Integer.valueOf(args[6]);
				nshoals = Integer.valueOf(args[7]);
				shoalSize = Integer.valueOf(args[8]);
				minShoalDetectable = Integer.valueOf(args[9]);
				catchPercentage = (double) Integer.valueOf(args[10]) / 100.0;
				growing_factor = Integer.valueOf(args[11]);
				eco_system = Double.valueOf(args[12]);
				seasonMoves = Integer.valueOf(args[13]);
				shoalPeriod = Integer.valueOf(args[14]);
				logFile = new File(args[15]);
			} catch (Throwable t) {
				System.out.println("Invalid arguments!");
				System.exit(-1);
			}
		}

		// Logger
		MLog logger = MLog.getInstance();
		TLogFlusher logFlusher = null;
		if (logFile == null) {
			logFlusher = new TLogFlusher(System.out);
		} else {
			try {
				logFlusher = new TLogFlusher(new FileOutputStream(logFile));
			} catch (FileNotFoundException e) {
				System.out.println("Error opening file for logging");
				System.exit(-1);
			}
		}

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
		ShoalClient cShoals[] = new  ShoalClient[nshoals];
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

			oceano.addDirOper(sDirOpers[r]);
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

		logFlusher.start();

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

			logFlusher.interrupt();
			logFlusher.join();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.println("All joined");
	}
}
