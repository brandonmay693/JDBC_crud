package test;

import sourcecode.DataManager;
import java.sql.ResultSet;
import org.junit.*;
/**
 * Created by maybra01 on 7/28/2016.
 */
public class CRUDTest {

    DataManager myDataManager;
    ResultSet rs;

    @Test
    public void testCrud() {

        myDataManager = new DataManager();
        boolean validResult = false;
        myDataManager.makeConnection();
        try {
            rs = myDataManager.createTable();
            if (rs.next())
                validResult = true;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        Assert.assertTrue(validResult);

        Assert.assertEquals(2,myDataManager.insertRecord());
        Assert.assertEquals(1,myDataManager.updateRecord());
        Assert.assertEquals(0,myDataManager.deleteAllRecords());
        try {
            rs = myDataManager.dropTable();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }




}
