package connection.data_saving;

import exceptions.DataSavingException;

public interface DataStorageService {
    void saveData(Object object);

    Object loadData();
}
