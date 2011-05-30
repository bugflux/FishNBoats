/**
 * 
 */
package pt.ua.sd.distribution;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.ua.sd.distribution.StartMessage.Entity;
import pt.ua.sd.gui.IRemoteObserver;
import pt.ua.sd.gui.RemoteObserver;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.ProtocolEndPoint;

/**
 * This entity accepts a valid {@link StartMessage} and sends it to the
 * respective machines that ought the entities assigned to them.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DistributionClient {

    public static RemoteObserver observable;

    public static void setObservable(RemoteObserver observable) {
        DistributionClient.observable = observable;
    }

    private static void registerObservable(InetSocketAddress rmiAddr) {
        if(observable==null)
            return;
        Registry realRegistry = null;
        IRmiRegistry remoteRegistry = null;
        
        try {
            System.out.println("Getting registry at " + rmiAddr.getAddress().getHostAddress());
            realRegistry = LocateRegistry.getRegistry(rmiAddr.getAddress().getHostAddress(), rmiAddr.getPort());
            remoteRegistry = (IRmiRegistry) realRegistry.lookup(IRmiRegistry.class.toString());
            
            IRemoteObserver robstub = (IRemoteObserver) UnicastRemoteObject.exportObject(observable, rmiAddr.getPort()+2);
            remoteRegistry.bind(IRemoteObserver.class.toString(), robstub);

            System.out.println("Registado obtido o observable");
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(DistributionClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(DistributionClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(DistributionClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException e) {
            // do nothing with this exception, it just means the
            // registry could not be created, and that means that this
            // message is to create it for the first time, probably!
        }

    }

    public boolean stopEntities(InetAddress entities[]) {
        try {
            AbortMessage abort = new AbortMessage();
            for (InetAddress entity : entities) {
                Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(new Socket(entity,
                        DistributionConfig.DISTRIBUTION_SERVER_PORT), abort));
            }
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

    public static void main(String args[]) {

        StartMessage start;
        Socket s;
        DistributionConfig config = new DistributionConfig();

        String rmiserveraddress = "192.168.8.171";
        int rmiserverport = 22145; // this and the next port will be used!
        String mlogserveaddress = "192.168.8.171";
        int mlogserveport = 22140;
        String moceanseraddress = "172.16.6.49";
        int moceanserport = 22141;
        String mshoalseraddress = "172.16.6.49";
        int mshoalserport = 22142;
        String mdiropersaddress = "172.16.6.49";
        int mdiropersport = 22143;
        String mboatservaddress = "172.16.6.49";
        int mboatservport = 22144;
        String tboatservaddress = "192.168.8.181";
        String tshoalseraddress = "192.168.8.181";
        String tdiropersaddress = "192.168.8.181";
        
        if (args.length != 0) {
            try {
                config.width = Integer.valueOf(args[0]);
                config.height = Integer.valueOf(args[1]);
                config.nCampaign = Integer.valueOf(args[2]);
                config.ncompanies = Integer.valueOf(args[3]);
                config.nboats = Integer.valueOf(args[4]);
                config.boatCapacity = Integer.valueOf(args[5]);
                config.boatPeriod = Integer.valueOf(args[6]);
                config.nshoals = Integer.valueOf(args[7]);
                config.shoalSize = Integer.valueOf(args[8]);
                config.minShoalDetectable = Integer.valueOf(args[9]);
                config.catchPercentage = (double) Integer.valueOf(args[10]) / 100.0;
                config.growing_factor = Integer.valueOf(args[11]);
                config.eco_system = Double.valueOf(args[12]);
                config.seasonMoves = Integer.valueOf(args[13]);
                config.shoalPeriod = Integer.valueOf(args[14]);
                config.logFile = args[15];

                rmiserveraddress = args[16];
                rmiserverport = Integer.valueOf(args[17]); // this and the next port will be used!
                mlogserveaddress = args[18];
                mlogserveport = Integer.valueOf(args[19]);
                moceanseraddress = args[20];
                moceanserport = Integer.valueOf(args[21]);
                mshoalseraddress = args[22];
                mshoalserport = Integer.valueOf(args[23]);
                mdiropersaddress = args[24];
                mdiropersport = Integer.valueOf(args[25]);
                mboatservaddress = args[26];
                mboatservport = Integer.valueOf(args[27]);
                tboatservaddress = args[28];
                tshoalseraddress = args[29];
                tdiropersaddress = args[30];
            } catch (Throwable t) {
                System.out.println("Invalid arguments!");
                System.exit(-1);
            }
        }

        try {
            // start rmi server
            InetSocketAddress rmi = new InetSocketAddress(rmiserveraddress, rmiserverport);
            start = new StartMessage(config, Entity.RmiServer, 0, rmi);
            s = new Socket(rmiserveraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            registerObservable(rmi);

            // start mlog
            start = new StartMessage(config, Entity.MLog, mlogserveport, rmi);
            s = new Socket(mlogserveaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            // start mocean
            start = new StartMessage(config, Entity.MOcean, moceanserport, rmi);
            s = new Socket(moceanseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            // start mshoal
            start = new StartMessage(config, Entity.MShoal, mshoalserport, rmi);
            s = new Socket(mshoalseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            // start mdiroper
            start = new StartMessage(config, Entity.MDirOper, mdiropersport, rmi);
            s = new Socket(mdiropersaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            // start mboat
            start = new StartMessage(config, Entity.MBoat, mboatservport, rmi);
            s = new Socket(mboatservaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            // start tboat
            start = new StartMessage(config, Entity.TBoat, 0, rmi);
            s = new Socket(tboatservaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            // start tshoal
            start = new StartMessage(config, Entity.TShoal, 0, rmi);
            s = new Socket(tshoalseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

            // start tdiroper
            start = new StartMessage(config, Entity.TDirOper, 0, rmi);
            s = new Socket(tdiropersaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
            Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
