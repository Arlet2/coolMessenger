package connection;

import chat_data_objects.Message;
import connection.data_exchanging.ServerProtocolHandler;
import connection.data_objects.NetDTO;

import java.net.Socket;

public class BasicSendService implements SendService {
    private final PackageService packageService;
    private final NetDataExchangeHandler exchangeHandler;

    public BasicSendService(ServerProtocolHandler protocolHandler) {
        this.packageService = protocolHandler.getPackageService();
        this.exchangeHandler = protocolHandler.getNetExchangeHandler();
    }

    public void sendMessage(Message message, Socket socket) {
        sendDTO(message, socket, NetDTO.DataCode.MESSAGE);
    }

    public void sendError(String msg, Socket socket) {
        sendDTO(msg, socket, NetDTO.DataCode.ERROR);
    }

    private void sendDTO(Object object, Socket socket, NetDTO.DataCode code) {
        NetDTO dto = packageService.packDataWithEncryption(object, code);

        exchangeHandler.sendDTO(socket, dto);
    }
}
