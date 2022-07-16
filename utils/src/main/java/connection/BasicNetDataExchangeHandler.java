package connection;

import connection.data_objects.NetDTO;
import exceptions.NetDataTransferException;
import exceptions.SerializerException;
import utils.Serializer;

import java.io.IOException;
import java.net.Socket;

public class BasicNetDataExchangeHandler implements NetDataExchangeHandler {

    @Override
    public void sendDTO(Socket socket, NetDTO dto) {
        try {
            byte[] data = Serializer.convertObjectToBytes(dto);
            socket.getOutputStream().write(data);
        } catch (IOException | SerializerException e) {
            e.printStackTrace();
            throw new NetDataTransferException();
        }
    }

    @Override
    public NetDTO getDTO(Socket socket) {
        try {
            byte[] data = socket.getInputStream().readAllBytes();
            return (NetDTO) Serializer.convertBytesToObject(data);
        } catch (IOException | SerializerException e) {
            e.printStackTrace();
            throw new NetDataTransferException();
        }
    }

}
