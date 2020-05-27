package pers.li.client;

import lombok.extern.slf4j.Slf4j;
import pers.li.rpc.Peer;
import pers.li.transport.TransportClient;
import pers.li.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 表示选择哪个server去连接
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {

    /**
     * 存储所有连接好的client
     */
    private List<TransportClient> clients;

    public RandomTransportSelector(){
        clients = new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        //count取大于等于1的
        count = Math.max(count, 1);
        for (Peer peer : peers
        ) {
            for (int i = 0; i < count; i++) {
                TransportClient transportClient = ReflectionUtils.newInstance(clazz);
                transportClient.connect(peer);
                clients.add(transportClient);
            }
            log.info("connect server : {}", peer);
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient c :
                clients) {
            c.close();
        }
        clients.clear();
    }
}
