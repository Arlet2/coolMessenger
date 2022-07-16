package connection.data_saving;

public interface DataStorageService {
    void saveData(Object object);

    Object loadData();
}
