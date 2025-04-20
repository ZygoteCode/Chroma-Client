package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.multiplayer.ServerData;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ServerFinderHelper
{
    public static CopyOnWriteArrayList<ServerData> ips = new CopyOnWriteArrayList();
    public static int ipsScanned;
    public static int ipsWorking;
    public static int ipsRedServer;
    static CopyOnWriteArrayList<ServerData> scannedIps = new CopyOnWriteArrayList();
    private static boolean isReady = false;
    private static boolean isScanning = false;

    public static boolean start(String fullIp) {
        String ip = fullIp.split(":")[0];
        int port = 25565;

        if (fullIp.split(":").length == 2) {
            try {
                port = Integer.valueOf(fullIp.split(":")[1]);
            } catch (Exception exception) {
                // empty catch block
            }
        }

        if (ServerFinderHelper.validIP(ip)) {
            ServerFinderHelper.startScann(String.valueOf(ip) + ":" + port);
            return true;
        }

        try {
            InetAddress inet = InetAddress.getByName(ip);
            ServerFinderHelper.startScann(String.valueOf(inet.getHostAddress()) + ":" + port);
            return true;
        } catch (UnknownHostException e1) {
            return false;
        }
    }

    public static CopyOnWriteArrayList<ServerData> getScannedIps() {
        return scannedIps;
    }

    public static void initOrStop() {
        ips.clear();
        scannedIps.clear();
        isReady = false;
        isScanning = false;
        ipsScanned = 0;
        ipsWorking = 0;
        ipsRedServer = 0;
    }

    public static boolean isScannComplet()
    {
        return isReady;
    }

    public static boolean isScanning()
    {
        return isScanning;
    }

    private static void startScann(String ip)
    {
        ip = ServerFinderHelper.getLastIp(ip);
        ipsScanned = 0;
        ipsWorking = 0;
        isReady = false;

        if (!validIP(ip))
        {
            return;
        }

        int i = 0;

        while (i < 256)
        {
            ips.add(new ServerData(ip, ip, true));
            ip = ServerFinderHelper.getNextIP(ip);
            ++i;
        }

        i = 0;

        while (i < 30)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while (ServerFinderHelper.ips.size() > 0)
                    {
                        ServerData data;
                        block3:
                        {
                            ServerFinderHelper.access$0(true);
                            data = ServerFinderHelper.ips.get(0);
                            ServerFinderHelper.ips.remove(0);

                            try
                            {
                                Socket s = new Socket();
                                s.setTcpNoDelay(true);
                                s.connect(new InetSocketAddress(data.serverIP, 25565), 2500);
                                s.close();
                                ++ServerFinderHelper.ipsScanned;
                                ++ServerFinderHelper.ipsWorking;

                                if (!data.gameVersion.equalsIgnoreCase("1.8"))
                                {
                                    break block3;
                                }

                                ++ServerFinderHelper.ipsRedServer;
                            }
                            catch (Exception e)
                            {
                                ++ServerFinderHelper.ipsScanned;
                                continue;
                            }
                        }
                        ServerFinderHelper.scannedIps.add(data);

                        if (!ServerFinderHelper.ips.isEmpty())
                        {
                            continue;
                        }

                        ServerFinderHelper.ips.clear();
                        ServerFinderHelper.access$1(true);
                        ServerFinderHelper.access$0(false);
                        return;
                    }
                }
            }).start();
            ++i;
        }
    }

    private static String getNextIP(String ip)
    {
        String s = ip;
        int[] split = new int[4];
        int i = 0;

        while (i < 4)
        {
            split[i] = Integer.parseInt(s.split("\\.")[i]);
            ++i;
        }

        i = 3;

        while (i >= 0)
        {
            int[] arrn = split;
            int n = i;
            arrn[n] = arrn[n] + 1;

            if (split[i] != 255)
            {
                break;
            }

            split[i] = 1;
            --i;
        }

        s = String.valueOf(split[0]) + "." + split[1] + "." + split[2] + "." + split[3];
        return s;
    }

    private static String getLastIp(String ip)
    {
        if (validIP(ip = ip.split(":")[0]))
        {
            int[] split = new int[]{Integer.valueOf(ip.split("\\.")[0]), Integer.valueOf(ip.split("\\.")[1]), Integer.valueOf(ip.split("\\.")[2]), 1};
            return String.valueOf(split[0]) + "." + split[1] + "." + split[2] + "." + split[3];
        }

        return ip;
    }

    static void access$0(boolean bl)
    {
        isScanning = bl;
    }

    static void access$1(boolean bl)
    {
        isReady = bl;
    }

    public static boolean validIP(String ip)
    {
        if (ip == null || ip.isEmpty())
        {
            return false;
        }

        if ((ip = ip.trim()).length() < 6 & ip.length() > 15)
        {
            return false;
        }

        try
        {
            Pattern pattern = Pattern.compile((String) "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher((CharSequence) ip);
            return matcher.matches();
        }
        catch (PatternSyntaxException ex)
        {
            return false;
        }
    }
}