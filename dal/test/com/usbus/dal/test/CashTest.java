package com.usbus.dal.test;

import com.usbus.dal.dao.CashRegisterDAO;
import jxl.write.WriteException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Lufasoch on 04/08/2016.
 */
public class CashTest {
    protected CashRegisterDAO cashRegisterDAO = new CashRegisterDAO();

    @Test
    public void excelTest(){
        try {
            cashRegisterDAO.createExcel(2, "SSS", 2L, 2L, new Date(), new Date());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
