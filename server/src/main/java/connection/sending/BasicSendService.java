package connection;

import chat_data_objects.ChatData;
import chat_data_objects.Message;
import connection.data_exchanging.ServerProtocolHandler;
import connection.data_objects.NetDTO;
import exceptions.NetDataTransferException;

import java.net.Socket;

public class BasicSendService implements SendService {
    private final PackageService packageService;
    private final NetDataExchangeHandler exchangeHandler;

    public BasicSendService(ServerProtocolHandler protocolHandler) {
        this.packageService = protocolHandler.getPackageService();
        this.exchangeHandler = protocolHandler.getNetExchangeHandler();
    }

    @Override
    public void sendChatContent(ChatData chatData, Socket socket) throws NetDataTransferException {
        sendDTO(chatData, socket, NetDTO.DataCode.CHAT_CONTENT);
    }

    @Override
    public void sendError(String msg, Socket socket) throws NetDataTransferException {
        sendDTO(msg, socket, NetDTO.DataCode.ERROR);
    }

    private void sendDTO(Object object, Socket socket, NetDTO.DataCode code) throws NetDataTransferException {
        NetDTO dto = packageService.packDataWithEncryption(object, code);

        exchangeHandler.sendDTO(socket, dto);
    }
}
