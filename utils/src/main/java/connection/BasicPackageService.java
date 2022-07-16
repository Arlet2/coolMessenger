package connection;

import connection.data_objects.NetDTO;
import encryptors.EncryptorService;
import encryptors.EncryptorsFactory;
import exceptions.SerializerException;
import utils.Serializer;

public class BasicPackageService implements PackageService {
    private final EncryptorService encryptor;

    public BasicPackageService(int encryptorProtocol) {
        this.encryptor = EncryptorsFactory.getEncryptor(encryptorProtocol);
    }

    @Override
    public NetDTO packageDataWithEncryption(Object object, NetDTO.DataCode code)
            throws SerializerException {
        byte[] bytes = Serializer.convertObjectToBytes(object);
        return generalizedPackage(encryptor.encrypt(bytes), code);
    }

    @Override
    public NetDTO packageData(Object object, NetDTO.DataCode code) throws SerializerException {
        return generalizedPackage(Serializer.convertObjectToBytes(object), code);
    }

    @Override
    public NetDTO decryptData(NetDTO dto) {
        EncryptorService decoder = EncryptorsFactory.getEncryptor(dto.getEncryptionProtocol());
        return new NetDTO(decoder.decrypt(dto.getBytes()), dto.getCode(), dto.getEncryptionProtocol());
    }

    private NetDTO generalizedPackage(byte[] data, NetDTO.DataCode code) {
        return new NetDTO(data, code, encryptor.getEncryptionProtocol());
    }
}
