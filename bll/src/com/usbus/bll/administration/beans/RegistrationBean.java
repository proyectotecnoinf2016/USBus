package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.RegistrationLocal;
import com.usbus.bll.administration.interfaces.RegistrationRemote;
import com.usbus.commons.auxiliaryClasses.Password;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.dao.HumanResourceDAO;
import com.usbus.dal.dao.TenantDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.HumanResource;
import com.usbus.dal.model.Tenant;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpmartinez on 05/06/16.
 */
@Stateless(name = "RegistrationEJB")

public class RegistrationBean implements RegistrationLocal, RegistrationRemote {

    private final TenantDAO tenantDAO = new TenantDAO();
    private final UserDAO userDAO = new UserDAO();
    private final HumanResourceDAO hrDAO = new HumanResourceDAO();

    public RegistrationBean() {
    }

    @Override
    public long registerTenant(Tenant tenant) {
        ObjectId tenantOID = null;
        try {
            tenant.setTenantId(tenantDAO.countAll() + 1);
            tenantOID = tenantDAO.persist(tenant);
            return tenant.getTenantId();
        } catch (Exception ex) {
            tenantDAO.remove(tenantOID);
            return 0;
        }

    }

    @Override
    public Boolean registerUser(User user) {
        ObjectId userOID = null;
        try {

            String pass = user.getPassword();

            byte[] salt = Password.getNextSalt();
            byte[] hash = Password.hashPassword(pass.toCharArray(),salt,10000,256);

            List<Rol> roles = new ArrayList<>();
            roles.add(Rol.ADMINISTRATOR);
            HumanResource hr = new HumanResource(user, true, roles);
            hr.setSalt(salt);
            hr.setPasswordHash(hash);

            userOID = hrDAO.persist(hr);
            return true;

        } catch (Exception ex) {
            hrDAO.remove(userOID);
            tenantDAO.remove(tenantDAO.getByLocalId(user.getTenantId()).get_id());
            return false;

        }
    }

    @Override
    public Boolean registerClient(User user) {
        ObjectId userOID = null;
        try {

            String pass = user.getPassword();

            byte[] salt = Password.getNextSalt();
            byte[] hash = Password.hashPassword(pass.toCharArray(),salt,10000,256);

            user.setPasswordHash(hash);
            user.setSalt(salt);

            userOID = userDAO.persist(user);
            return true;

        } catch (Exception ex) {
            userDAO.remove(userOID);
            tenantDAO.remove(tenantDAO.getByLocalId(user.getTenantId()).get_id());
            return false;

        }

    }
}
