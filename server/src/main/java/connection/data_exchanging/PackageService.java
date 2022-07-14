package connection.data_exchanging;

import connection_utils.DataTransferObject;
import exceptions.SerializerException;

public interface PackageService {
    DataTransferObject encryptPackageData(Object object, DataTransferObject.DataCode code) throws SerializerException;

    DataTransferObject packageData(Object object, DataTransferObject.DataCode code) throws SerializerException;
}
