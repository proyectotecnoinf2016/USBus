package com.usbus.bll.administration.interfaces;

import com.usbus.commons.enums.JourneyStatus;
import com.usbus.dal.model.Journey;
import org.bson.types.ObjectId;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 05/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface JourneyRemote {
    String persist(Journey j01); //service
    Journey getById(String oid);
    Journey getByLocalId(long tenantId, Long id);
    void setInactive(long tenantId, Long journeyId);
    void setActive(long tenantId, Long journeyId);
    Journey getByJourneyId(long tenantId, Long id);
    List<Journey> JourneysByTenantIdAndStatus(long tenantId, JourneyStatus status, int offset, int limit);
    List<Journey> getJourneysByTenantAndDate(long tenantId, Date time, int offset, int limit);
    List<Journey> getJourneysByTenantDateAndStatus(long tenantId, Date time, JourneyStatus journeyStatus, int offset, int limit);
    Double getJourneyPrice(long tenantId, Long journeyId, String origin, String destination);

    JSONObject getNextJourneysForUser(long tenantId, String username, Date date, int offset, int limit);

    List<Journey> getJourneysByDateOriginAndDestination(long tenantId, Date time, String origin, String destination);
}
