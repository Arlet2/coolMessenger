package connection;

import connection.data_objects.NetDTO;
import exceptions.SerializerException;

public interface PackageService {
    NetDTO packageDataWithEncryption(Object object, NetDTO.DataCode code) throws SerializerException;

    NetDTO packageData(Object object, NetDTO.DataCode code) throws SerializerException;

    NetDTO decryptData(NetDTO dto);
}
