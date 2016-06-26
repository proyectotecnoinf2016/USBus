package com.usbus.bll.administration.interfaces;

import javax.ejb.Local;
import java.io.IOException;

/**
 * Created by Lufasoch on 26/06/2016.
 */
@Local
public interface TenantLocal {
    void saveTenantStyle(long tenantId, String logo, String logoExtension,
                         String header, String headerExtension, String busColor,
                         Boolean showBus, String theme) throws IOException;
}
