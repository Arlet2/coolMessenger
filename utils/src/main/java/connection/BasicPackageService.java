package connection;

import connection.data_objects.NetDTO;
import encryptors.EncryptorService;
import exceptions.SerializerException;
import utils.Serializer;

public class BasicPackageService implements PackageService {
    private final EncryptorService encryptor;

    BasicPackageService(EncryptorService encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public NetDTO encryptPackageData(Object object, NetDTO.DataCode code)
            throws SerializerException {
        byte[] bytes = Serializer.convertObjectToBytes(object);
        return generalizedPackage(encryptor.encrypt(bytes), code);
    }

    @Override
    public NetDTO packageData(Object object, NetDTO.DataCode code) throws SerializerException {
        return generalizedPackage(Serializer.convertObjectToBytes(object), code);
    }

    @Override
    public NetDTO decryptPackageData(NetDTO dto) {
        return new NetDTO(encryptor.decrypt(dto.getBytes()), dto.getCode(), dto.getEncryptionProtocol());
    }

    private NetDTO generalizedPackage(byte[] data, NetDTO.DataCode code) {
        return new NetDTO(data, code, encryptor.getEncryptionProtocol());
    }
}
