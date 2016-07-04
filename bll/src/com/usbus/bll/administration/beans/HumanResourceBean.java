package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.HumanResourceLocal;
import com.usbus.bll.administration.interfaces.HumanResourceRemote;
import com.usbus.commons.auxiliaryClasses.Password;
import com.usbus.commons.enums.HRStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.exceptions.UserException;
import com.usbus.dal.dao.HumanResourceDAO;
import com.usbus.dal.model.HumanResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpmartinez on 26/06/16.
 */
public class HumanResourceBean implements HumanResourceLocal,HumanResourceRemote{
    private final HumanResourceDAO hrdao = new HumanResourceDAO();

    public HumanResourceBean(){

    }
    @Override
    public void persist(HumanResource user) throws UserException {
        String pass = user.getPassword();

        byte[] salt = Password.getNextSalt();
        byte[] hash = Password.hashPassword(pass.toCharArray(), salt, 10000, 256);

        HumanResource hr = new HumanResource(user, true, user.getRoles());
        hr.setSalt(salt);
        hr.setPasswordHash(hash);

        String oid = hrdao.persist(hr);
        if (oid == null){
            throw new UserException("Ocurrió un error al persistir el recurso humano.");
        }
    }

    @Override
    public HumanResource getById(String id) {
        return hrdao.getById(id);
    }

    @Override
    public HumanResource getByUsername(long tenantId, String username) {
        return hrdao.getByUsername(tenantId,username);
    }

    @Override
    public HumanResource getByEmail(long tenantId, String email) {
        return hrdao.getByEmail(tenantId,email);
    }

    @Override
    public List<HumanResource> getByStatus(long tenantId, Boolean status, int offset, int limit) {
        return hrdao.getByStatus(tenantId,status,offset,limit);
    }

    @Override
    public List<HumanResource> getByHRStatus(long tenantId, HRStatus status, int offset, int limit) {
        return hrdao.getByHRStatus(tenantId,status,offset,limit);
    }
    @Override
    public List<HumanResource> getByRol(long tenantId, Rol rol, int offset, int limit) {
        return hrdao.getByRol(tenantId,rol,offset,limit);
    }
    @Override
    public List<HumanResource> getByRolAndStatus(long tenantId, HRStatus status, Rol rol, int offset, int limit) {
        return hrdao.getByRolAndStatus(tenantId,status,rol,offset,limit);
    }
    @Override
    public List<HumanResource> getByRolAvailable(long tenantId, Rol rol,int offset, int limit) {
        return hrdao.getByRolAvailable(tenantId,rol,offset,limit);
    }
    @Override
    public List<HumanResource> getAllHumanResources(long tenantId, int offset, int limit) {
        return hrdao.getAllHumanResources(tenantId,offset,limit);
    }

    @Override
    public void setInactive(long tenantId, String username) {
        hrdao.setInactive(tenantId,username);
    }
}
