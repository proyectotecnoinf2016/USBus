package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.TenantLocal;
import com.usbus.bll.administration.interfaces.TenantRemote;
import com.usbus.commons.auxiliaryClasses.TenantStyleAux;
import com.usbus.dal.dao.TenantDAO;
import com.usbus.dal.model.Tenant;
import org.bson.types.ObjectId;

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
    public Tenant getById(String id) {
        return dao.getById(id);
    }

    @Override
    public Tenant getByLocalId(Long id) {
        return dao.getByLocalId(id);
    }

    @Override
    public String saveTenantStyle(long tenantId, TenantStyleAux style) throws IOException {
        return dao.saveTenantStyle(tenantId, style.getLogoB64(), style.getLogoExtension(), style.getHeaderB64(), style.getHeaderExtension(), style.getBusColor(), style.getShowBus(), style.getTheme());
    }

    @Override
    public TenantStyleAux getTenantStyle(String tenantName) throws IOException {
        return dao.getTenantStyle(tenantName);
    }
}
