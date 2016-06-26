package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.TenantLocal;
import com.usbus.bll.administration.interfaces.TenantRemote;
import com.usbus.dal.dao.TenantDAO;

import javax.ejb.Stateless;
import java.io.IOException;

/**
 * Created by Lufasoch on 26/06/2016.
 */
@Stateless(name = "TenantEJB")
public class TenantBean implements TenantLocal, TenantRemote{
    private final TenantDAO dao = new TenantDAO();
    public TenantBean(){}

    @Override
    public void saveTenantStyle(long tenantId, String logo, String logoExtension, String header,
                                String headerExtension, String busColor, Boolean showBus, String theme) throws IOException {
        dao.saveTenantStyle(tenantId, logo, logoExtension, header, headerExtension, busColor, showBus, theme);
    }
}
