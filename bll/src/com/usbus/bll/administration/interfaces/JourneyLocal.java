package com.usbus.bll.administration.interfaces;

import com.usbus.commons.enums.JourneyStatus;
import com.usbus.dal.model.Journey;
import org.bson.types.ObjectId;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 05/06/2016.
 */
@Local
public interface JourneyLocal {
    ObjectId persist(Journey bus01);
    Journey getById(ObjectId oid);
    Journey getByLocalId(long tenantId, Long id);
    void setInactive(long tenantId, Long journeyId);
    void setActive(long tenantId, Long journeyId);
    Journey getByJourneyId(long tenantId, Long id);
    List<Journey> JourneysByTenantIdAndStatus(long tenantId, JourneyStatus status, int offset, int limit);
    List<Journey> getJourneysByTenantAndDate(long tenantId, Date time, int offset, int limit);
    List<Journey> getJourneysByTenantDateAndStatus(long tenantId, Date time, JourneyStatus journeyStatus, int offset, int limit);
    Double getJourneyPrice(long tenantId, Long journeyId, String origin, String destination);
}
