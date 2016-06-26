package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Branch;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by Lufasoch on 22/06/2016.
 */
public interface BranchRemote {
    ObjectId persist(Branch branch);
    Branch getById(ObjectId branchId);
    Branch getByLocalId(long tenantId, Long branchId);
    Branch getByBranchName(long tenantId, String name);
    void setInactive(long tenantId, Long branchId);
    List<Branch> getBranchesByTenant(long tenantId, int offset, int limit);
}
