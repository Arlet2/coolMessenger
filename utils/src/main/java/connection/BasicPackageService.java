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
    public NetDTO packDataWithEncryption(Object object, NetDTO.DataCode code)
            throws SerializerException {
        byte[] bytes = Serializer.convertObjectToBytes(object);
        return generalizedPack(encryptor.encrypt(bytes), code);
    }

    @Override
    public NetDTO packData(Object object, NetDTO.DataCode code) throws SerializerException {
        return generalizedPack(Serializer.convertObjectToBytes(object), code);
    }

    @Override
    public Object unpackDataWithDecryption(NetDTO dto) {
        EncryptorService decoder = EncryptorsFactory.getEncryptor(dto.getEncryptionProtocol());
        return generalizedUnpack(decoder.decrypt(dto.getBytes()));
    }

    @Override
    public Object unpackData(NetDTO dto) {
        return generalizedUnpack(dto.getBytes());
    }

    private Object generalizedUnpack(byte[] bytes) {
        return Serializer.convertBytesToObject(bytes);
    }

    private NetDTO generalizedPack(byte[] data, NetDTO.DataCode code) {
        return new NetDTO(data, code, encryptor.getEncryptionProtocol());
    }
}
