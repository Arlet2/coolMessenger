package connection;

import connection.data_objects.DataTransferObject;
import exceptions.SerializerException;

public interface PackageService {
    DataTransferObject encryptPackageData(Object object, DataTransferObject.DataCode code) throws SerializerException;

    DataTransferObject packageData(Object object, DataTransferObject.DataCode code) throws SerializerException;
    DataTransferObject decryptPackageData(DataTransferObject dto);
}
