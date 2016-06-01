package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Branch;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class BranchDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public BranchDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Branch branch) {
        return dao.persist(branch);
    }

    public long countAll() {
        return dao.count(Branch.class);
    }

    public long countTenant(long tenantId) {
        Query<Branch> query = ds.createQuery(Branch.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Branch getById(ObjectId id) {
        return dao.get(Branch.class, id);
    }

    public Branch getByBranchId(long tenantId, Long branchId){
        if (!(tenantId > 0) || (branchId == null)) {
            return null;
        }

        Query<Branch> query = ds.createQuery(Branch.class);

        query.and(query.criteria("id").equal(branchId),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public Branch getByBranchName(long tenantId, String name){
        if (!(tenantId > 0) || (name.isEmpty())) {
            return null;
        }

        Query<Branch> query = ds.createQuery(Branch.class);

        query.and(query.criteria("name").equal(name),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public void remove(ObjectId id) {
        dao.remove(Branch.class, id);
    }

    public void setInactive(long tenantId, String branchName) {
        if (!(tenantId > 0) || (branchName.isEmpty())) {
        } else {
            Query<Branch> query = ds.createQuery(Branch.class);

            query.and(query.criteria("name").equal(branchName),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Branch> updateOp = ds.createUpdateOperations(Branch.class).set("active", false);
            ds.update(query, updateOp);
        }
    }
}
