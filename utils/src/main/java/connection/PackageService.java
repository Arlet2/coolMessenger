package connection;

import connection.data_objects.NetDTO;
import exceptions.SerializerException;

public interface PackageService {
    NetDTO packDataWithEncryption(Object object, NetDTO.DataCode code) throws SerializerException;

    NetDTO packData(Object object, NetDTO.DataCode code) throws SerializerException;
    
    Object unpackDataWithDecryption(NetDTO dto);
    Object unpackData(NetDTO dto);
}
