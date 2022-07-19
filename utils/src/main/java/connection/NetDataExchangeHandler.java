package connection;

import connection.data_objects.NetDTO;

import java.net.Socket;

public interface NetDataExchangeHandler {

    void sendDTO(Socket socket, NetDTO dto);

    NetDTO receiveDTO(Socket socket);

}
