package com.usbus.dal.test;

import com.usbus.dal.dao.CashRegisterDAO;
import jxl.write.WriteException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Lufasoch on 04/08/2016.
 */
public class CashTest {
    protected CashRegisterDAO cashRegisterDAO = new CashRegisterDAO();

    @Test
    public void excelTest(){
        try {
            cashRegisterDAO.createExcel("SSS","Lufasoch");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
