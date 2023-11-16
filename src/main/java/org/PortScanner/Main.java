package org.PortScanner;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<Integer> openPorts = portScan("127.0.0.1");
        openPorts.forEach(port -> Logger.log(Color.GREEN_BOLD.getColor()+"Port"+  Color.CYAN_BOLD.getColor()+   port+"  is open"));
    }
    public static List<Integer> portScan(String ip){
        ConcurrentLinkedDeque<Integer>openPorts=new ConcurrentLinkedDeque<>();
        Logger.log("Start scanning " + ip + "...", Color.CYAN);
        ExecutorService service= Executors.newFixedThreadPool(200);
        Logger.log(Color.YELLOW.getColor()+"[LOADING PORTS.............]");
        AtomicInteger port=new AtomicInteger(0);
        while (port.get()<65535){
            final int currentPort=port.getAndIncrement();
            service.submit(()->{
                try {
                    Socket socket=new Socket();

                    socket.connect(new InetSocketAddress(ip,currentPort),500);
                    socket.close();
                    openPorts.add(currentPort);
                    Logger.log(Color.GREEN.getColor()+ip+",port open:"+Color.MAGENTA.getColor()+currentPort);

                } catch (IOException e) {
                }
            });
        }
        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer>openPortList=new ArrayList<>();
        while (!openPorts.isEmpty()){
            openPortList.add(openPorts.poll());
        }
        return openPortList;
    }
}
