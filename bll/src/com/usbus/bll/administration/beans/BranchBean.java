package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.BranchLocal;
import com.usbus.bll.administration.interfaces.BranchRemote;
import com.usbus.dal.dao.BranchDAO;
import com.usbus.dal.model.Branch;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by Lufasoch on 22/06/2016.
 */
public class
BranchBean implements BranchLocal, BranchRemote {
    private final BranchDAO dao = new BranchDAO();
    public BranchBean() {}

    @Override
    public String persist(Branch branch) {
        branch.setId(dao.getNextId(branch.getTenantId()));
        return dao.persist(branch);
    }

    @Override
    public Branch getById(String branchId) {
        return dao.getById(branchId);
    }

    @Override
    public Branch getByLocalId(long tenantId, Long branchId) {
        return dao.getByBranchId(tenantId, branchId);
    }

    @Override
    public Branch getByBranchName(long tenantId, String name) {
        return dao.getByBranchName(tenantId, name);
    }

    @Override
    public void setInactive(long tenantId, Long branchId) {
        dao.setInactive(tenantId, branchId);
    }

    @Override
    public List<Branch> getBranchesByTenant(long tenantId, boolean status, int offset, int limit) {
        return dao.getBranchesByTenant(tenantId,offset,limit,status);
    }
}
