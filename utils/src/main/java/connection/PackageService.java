package connection;

import connection.data_objects.NetDTO;
import exceptions.SerializerException;

public interface PackageService {
    NetDTO encryptPackageData(Object object, NetDTO.DataCode code) throws SerializerException;

    NetDTO packageData(Object object, NetDTO.DataCode code) throws SerializerException;
    NetDTO decryptPackageData(NetDTO dto);
}
