package connection;

import connection.data_objects.NetDTO;
import exceptions.NetDataTransferException;
import exceptions.SerializerException;
import utils.Serializer;

import java.io.IOException;
import java.net.Socket;

public class BasicNetDataExchangeHandler implements NetDataExchangeHandler {

    @Override
    public void sendDTO(Socket socket, NetDTO dto) throws NetDataTransferException {
        try {
            byte[] data = Serializer.convertObjectToBytes(dto);
            socket.getOutputStream().write(data);
        } catch (IOException | SerializerException e) {
            throw new NetDataTransferException(e);
        }
    }

    @Override
    public NetDTO receiveDTO(Socket socket) throws NetDataTransferException {
        try {
            byte[] data = socket.getInputStream().readAllBytes();
            return (NetDTO) Serializer.convertBytesToObject(data);
        } catch (IOException | SerializerException e) {
            throw new NetDataTransferException(e);
        }
    }

}
