import sourcecode.DataManager;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        DataManager CSGDBManager = new DataManager();
        CSGDBManager.makeConnection();
        CSGDBManager.createTable();
        CSGDBManager.insertRecord();
        CSGDBManager.selectFromDatabase();
        CSGDBManager.updateRecord();
        CSGDBManager.selectFromDatabase();
        CSGDBManager.deleteAllRecords();
        CSGDBManager.dropTable();
    }
}
