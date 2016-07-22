package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Branch;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by Lufasoch on 22/06/2016.
 */
public interface BranchRemote {
    String persist(Branch branch);
    Branch getById(String branchId);
    Branch getByLocalId(long tenantId, Long branchId);
    List<Branch> getByBranchName(long tenantId, String name, boolean status, int offset, int limit);
    void setInactive(long tenantId, Long branchId);
    List<Branch> getBranchesByTenant(long tenantId,boolean status, int offset, int limit);
}
