package com.usbus.bll.administration.interfaces;

import javax.ejb.Remote;
import java.io.IOException;

/**
 * Created by Lufasoch on 26/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface TenantRemote {
    void saveTenantStyle(long tenantId, String logo, String logoExtension,
                         String header, String headerExtension, String busColor,
                         Boolean showBus, String theme) throws IOException;
}
