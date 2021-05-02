package net.avuna.net.proxy;

import com.github.simplenet.Client;
import com.github.simplenet.Server;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.Avuna;
import net.avuna.config.Configurable;
import net.avuna.net.proxy.packets.AvunaPackets;
import net.avuna.net.proxy.packets.PacketHandler;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProxyServer implements Configurable<ProxyServerConfig> {

    public static final ProxyServer INSTANCE = new ProxyServer(new ProxyServerConfig(Avuna.getConfigDir().resolve("proxy-server.json"), true));

    private final Server server = new Server();
    private final AvunaPackets packets = new AvunaPackets();
    private final ProxyServerConfig config;

    public void start() {
        config.load();
        if(config.getAuthUsername().equals("") || config.getAuthPassword().equals("")) {
            throw new SecurityException("You must change your username and password in the config");
        }
        server.onConnect(c -> {
            authorize(c);
            handlePackets(c);
        });
        server.bind("0.0.0.0", getConfig().getPort());
    }

    private void handlePackets(Client client) {
        client.readByteAlways(opcode -> {
            PacketHandler handler = packets.getPackets().getOrDefault((int) opcode, null);
            if(handler != null) {
                handler.read(client);
            }
        });
    }

    private void authorize(Client client) {
        client.readString(username -> {
            client.readString(password -> {
                if(!isAuthorized(username, password)) {
                    client.close();
                }
            });
        });
    }

    private boolean isAuthorized(String username, String password) {
        return username.equals(config.getAuthUsername()) && password.equals(config.getAuthPassword());
    }

    public void stop() {
        server.close();
    }
}
