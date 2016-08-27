package com.usbus.dal.test;

import com.usbus.commons.auxiliaryClasses.Dimension;
import com.usbus.dal.dao.ParcelDAO;
import com.usbus.dal.model.Parcel;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kavesa on 19/08/2016.
 */
public class ParcelTest {
    protected ParcelDAO parcelDAO = new ParcelDAO();

    @Test
    public void addParcel() {

        Dimension rubick = new Dimension(5.0, 5.0, 5.0);
        Parcel parcel01 = new Parcel(2L, 1L, rubick, 300, 1L, 1L, 2L, "Montevideo", "Plaza Cuba", false, false, true);
        Parcel parcel02 = new Parcel(2L, 2L, rubick, 300, 1L, 1L, 2L, "Montevideo", "Plaza Cuba", false, false, false);

        parcelDAO.persist(parcel01);
        parcelDAO.persist(parcel02);
        List<Parcel> parcelList = parcelDAO.getByJourney(2L, 1L, 0, 10);
        assertEquals(parcelList.get(0).getDestinationName(), parcelList.get(1).getDestinationName());
    }

}