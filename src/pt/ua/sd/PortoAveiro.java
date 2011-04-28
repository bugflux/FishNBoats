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
import pt.ua.sd.boat.network.BoatClient;
import pt.ua.sd.boat.network.BoatProtocolRunnable;
import pt.ua.sd.boat.network.BoatServer;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.diroper.MDirOper;
import pt.ua.sd.diroper.TDirOper;
import pt.ua.sd.diroper.network.DirOperClient;
import pt.ua.sd.diroper.network.DirOperProtocolRunnable;
import pt.ua.sd.diroper.network.DirOperServer;
import pt.ua.sd.log.MLog;
import pt.ua.sd.log.TLogFlusher;
import pt.ua.sd.log.network.LogClient;
import pt.ua.sd.log.network.LogProtocolRunnable;
import pt.ua.sd.log.network.LogServer;
import pt.ua.sd.ocean.MOcean;
import pt.ua.sd.ocean.network.OceanClient;
import pt.ua.sd.ocean.network.OceanProtocolRunnable;
import pt.ua.sd.ocean.network.OceanServer;
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

        //File logFile = null;
        String logFile = null;
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
               // logFile = new File(args[15]);
                logFile = args[15];
            } catch (Throwable t) {
                System.out.println("Invalid arguments!");
                System.exit(-1);
            }
        }

        final LogServer slogger = new LogServer(9081, new LogProtocolRunnable(),logFile);
        
        new Thread(){

            @Override
            public void run() {
                slogger.startServer();
            }
        
        }.start();
        
        LogClient clogger = new LogClient("127.0.0.1", 9081);
        
        /*TLogFlusher logFlusher = null;
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
        logFlusher.start();*/
        // Ocean
        Point wharf = new Point(0, 0);
        Point reproducingZone = new Point(width - 1, height - 1);
        MOcean oceano = new MOcean(height, width, maxShoalPerSquare,
                maxBoatsPerSquare, wharf, reproducingZone,clogger);
        
        final OceanServer oceanServer = new OceanServer(9091, new OceanProtocolRunnable(oceano));
        new Thread() {

            @Override
            public void run() {
                oceanServer.startServer();
            }
        }.start();
        
        OceanClient oceanClient = new OceanClient(9091, "127.0.0.1") ;
        
        
        // DirOper
        MDirOper mDirOpers[] = new MDirOper[ncompanies];
        DirOperClient cDirOpers[]= new DirOperClient[ncompanies];
        TDirOper tDirOpers[] = new TDirOper[ncompanies];
        DirOperStats sDirOpers[] = new DirOperStats[ncompanies];
        
        final DirOperServer dOperServer = new DirOperServer(9092, new DirOperProtocolRunnable(mDirOpers));
        new Thread() {

            @Override
            public void run() {
                dOperServer.startServer();
            }
        }.start();
        
        
        
        // Shoal
        MShoal mShoals[] = new MShoal[nshoals];
        final ShoalServer sShoal = new ShoalServer(9090, new ShoalProtocolRunnable(mShoals));
        new Thread() {

            @Override
            public void run() {
                sShoal.startServer();
            }
        }.start();
        
        ShoalClient cShoals[] = new ShoalClient[nshoals];
        
        ShoalStats sShoals[] = new ShoalStats[nshoals];
        TShoal tShoals[] = new TShoal[nshoals];


        // Boat
        MBoat mBoats[][] = new MBoat[ncompanies][nboats];
        BoatClient cBoats[][] = new BoatClient[ncompanies][nboats];
        BoatStats sBoats[][] = new BoatStats[ncompanies][nboats];
        TBoat tBoats[][] = new TBoat[ncompanies][nboats];
        final BoatServer sBoat = new BoatServer(9080, new BoatProtocolRunnable(mBoats));
        new Thread() {

            @Override
            public void run() {
                sBoat.startServer();
            }
        }.start();
        // Stats and Monitors
        for (int r = 0; r < nshoals; r++) {
            sShoals[r] = new ShoalStats(new ShoalId(r),
                    INTERNAL_STATE_SCHOOL.spawning, new Point(reproducingZone),
                    shoalSize, minShoalDetectable);
            mShoals[r] = new MShoal(sShoals[r].getId(), ncompanies,clogger);
            cShoals[r] = new ShoalClient(sShoals[r].getId(),9090,"127.0.0.1");
            
            oceano.addShoal(sShoals[r], cShoals[r], reproducingZone);
            
            tShoals[r] = new TShoal((ShoalStats) sShoals[r].clone(),
                    shoalPeriod, seasonMoves, nCampaign, cShoals[r], oceanClient,
                    cDirOpers, growing_factor, eco_system, catchPercentage);
            
        }

        for (int r = 0; r < ncompanies; r++) {
            
            sDirOpers[r] = new DirOperStats(
                    INTERNAL_STATE_DIROPER.starting_a_campaign,
                    new DirOperId(r));
            oceano.addDirOper(sDirOpers[r]);
            mDirOpers[r] = new MDirOper(sDirOpers[r].getId(), nshoals, nboats,clogger);
            cDirOpers[r]= new DirOperClient(sDirOpers[r].getId(), "127.0.0.1", 9092);
            tDirOpers[r] = new TDirOper(clogger, oceanClient, cDirOpers[r],
                    cBoats[r], cShoals, (DirOperStats) sDirOpers[r].clone());

            
            for (int s = 0; s < nboats; s++) {
                sBoats[r][s] = new BoatStats(new BoatId(r, s),
                        INTERNAL_STATE_BOAT.at_the_wharf, new Point(wharf),
                        boatCapacity);
                oceano.addBoat(sBoats[r][s], wharf);
                mBoats[r][s] = new MBoat(sBoats[r][s].getId(),clogger);
                cBoats[r][s] = new BoatClient(sDirOpers[r].getId(), sBoats[r][s].getId(), "127.0.0.1", 9080);
                tBoats[r][s] = new TBoat((BoatStats) sBoats[r][s].clone(),
                        boatPeriod, cDirOpers[r], oceanClient, cBoats[r][s]);
                
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

        //logFlusher.start();

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
            //TODO:terminar o logger
            //logFlusher.interrupt();
            //logFlusher.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All joined");
    }
}
