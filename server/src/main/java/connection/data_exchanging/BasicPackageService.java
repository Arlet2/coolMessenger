package connection.data_exchanging;

import connection_utils.DataTransferObject;
import encryptors.EncryptorService;
import exceptions.SerializerException;
import utils.Serializer;

public class BasicPackageService implements PackageService {
    private final EncryptorService encryptor;

    BasicPackageService(EncryptorService encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public DataTransferObject encryptPackageData(Object object, DataTransferObject.DataCode code)
            throws SerializerException {
        byte[] bytes = Serializer.convertObjectToBytes(object);
        return generalizedPackage(encryptor.encrypt(bytes), code);
    }

    @Override
    public DataTransferObject packageData(Object object, DataTransferObject.DataCode code) throws SerializerException {
        return generalizedPackage(Serializer.convertObjectToBytes(object), code);
    }

    @Override
    public DataTransferObject decryptPackageData(DataTransferObject dto) {
        return new DataTransferObject(encryptor.decrypt(dto.getBytes()), dto.getCode(), dto.getEncryptionProtocol());
    }

    private DataTransferObject generalizedPackage(byte[] data, DataTransferObject.DataCode code) {
        return new DataTransferObject(data, code, encryptor.getEncryptionProtocol());
    }
}
