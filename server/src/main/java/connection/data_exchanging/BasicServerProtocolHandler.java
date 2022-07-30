package connection.data_exchanging;

import connection.*;
import connection.data_objects.AuthDataObject;
import connection.data_objects.NetDTO;
import encryptors.SimpleEncryptor;
import exceptions.NetDataTransferException;

import java.net.Socket;

public class BasicServerProtocolHandler implements ServerProtocolHandler {
    public static final int PROTOCOL_NUMBER = 10001;

    private final NetDataExchangeHandler netDataExchangeHandler = new BasicNetDataExchangeHandler();
    private final PackageService packageService = new BasicPackageService(SimpleEncryptor.ENCRYPTION_PROTOCOL);

    @Override
    public AuthDataObject createSession(Socket socket) throws NetDataTransferException {
        NetDTO dto = netDataExchangeHandler.receiveDTO(socket);
        if (!dto.getCode().equals(NetDTO.DataCode.AUTH_INFO))
            throw new NetDataTransferException("Unexpected answer from client");
        return (AuthDataObject) packageService.unpackDataWithDecryption(dto);
    }

    @Override
    public boolean isTheSameProtocolWithClient(AuthDataObject authDataObject) {
        return PROTOCOL_NUMBER == authDataObject.getProtocol();
    }

    @Override
    public int getProtocolNumber() {
        return PROTOCOL_NUMBER;
    }

    @Override
    public NetDataExchangeHandler getNetExchangeHandler() {
        return netDataExchangeHandler;
    }

    @Override
    public PackageService getPackageService() {
        return packageService;
    }
}
