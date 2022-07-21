package connection;

import connection.data_objects.NetDTO;
import exceptions.NetDataTransferException;

import java.net.Socket;

public interface NetDataExchangeHandler {

    void sendDTO(Socket socket, NetDTO dto) throws NetDataTransferException;

    NetDTO receiveDTO(Socket socket) throws NetDataTransferException;

}
