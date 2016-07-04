package com.usbus.dal.dao;

import com.usbus.commons.enums.HRStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.HumanResource;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by jpmartinez on 06/06/16.
 */
public class HumanResourceDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public HumanResourceDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(HumanResource user) {
        if(user != null) {
            return dao.persist(user);
        } else {
            return null;
        }
    }

    public long countAll() {
        return dao.count(HumanResource.class);
    }

    public long countTenant(long tenantId) {
        if(tenantId > 0) {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("tenantId").equal(tenantId);
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            return query.countAll();
        } else {
            return 0;
        }
    }

    public HumanResource getById(String id) {
        if(id == null || id.isEmpty()) {
            return null;
        } else {
            return dao.get(HumanResource.class, id);
        }
    }

    public HumanResource getByUsername(long tenantId, String username) {
        if (tenantId < 0 || username == null || username.isEmpty()){
            return null;
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("username").equal(username),
                    query.criteria("tenantId").equal(tenantId));
            query.retrievedFields(false, "salt", "passwordHash");
            return query.get();
        }
    }

    public HumanResource getByEmail(long tenantId, String email) {
        if (tenantId < 0 || email == null || email.isEmpty()){
            return null;
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("email").equal(email),
                    query.criteria("tenantId").equal(tenantId));
            query.retrievedFields(false, "salt", "passwordHash");
            return query.get();
        }
    }

    public List<HumanResource> getByStatus(long tenantId, Boolean status, int offset, int limit) {
        if (tenantId < 0 || status == null || offset < 0 || limit < 0){
            return null;
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("status").equal(status),
                    query.criteria("tenantId").equal(tenantId));
            query.retrievedFields(false, "salt", "passwordHash");
            return query.offset(offset).limit(limit).asList();
        }
    }

    public List<HumanResource> getByHRStatus(long tenantId, HRStatus status, int offset, int limit) {
        if (tenantId < 0 || status == null || offset < 0 || limit < 0) {
            return null;
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("statusHistory.status").equal(status));
            query.retrievedFields(false, "salt", "passwordHash");
            return query.offset(offset).limit(limit).asList();
        }
    }

    public List<HumanResource> getByRol(long tenantId, Rol rol, int offset, int limit) {
        if (tenantId < 0 || rol == null || offset < 0 || limit < 0) {
            return null;
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("roles").equal(rol));
            query.retrievedFields(false, "salt", "passwordHash");
            return query.offset(offset).limit(limit).asList();
        }
    }

    public List<HumanResource> getByRolAndStatus(long tenantId, HRStatus status,Rol rol, int offset, int limit) {
        if (tenantId < 0 || rol == null || status == null || offset < 0 || limit < 0) {
            return null;
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("roles").equal(rol), query.criteria("statusHistory.status").equal(status));
            query.retrievedFields(false, "salt", "passwordHash");
            return query.offset(offset).limit(limit).asList();
        }
    }

    public List<HumanResource> getByRolAvailable(long tenantId,Rol rol, int offset, int limit) {
        if (tenantId < 0 || rol == null || offset < 0 || limit < 0) {
            return null;
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("roles").equal(rol), query.criteria("status").equal(true));
            query.retrievedFields(false, "salt", "passwordHash");
            return query.offset(offset).limit(limit).asList();
        }
    }

    public List<HumanResource> getAllHumanResources(long tenantId, int offset, int limit) {
        if (tenantId < 0 || offset < 0 || limit < 0) {
            return null;
        } else {
            return ds.find(HumanResource.class).disableValidation().retrievedFields(false, "salt", "passwordHash").field("tenantId")
                    .equal(tenantId).field("className").equal(HumanResource.class.getCanonicalName()).offset(offset).limit(limit).asList();
        }
    }

    public void remove(String id) {
        if(id == null || id.isEmpty()) {
        } else {
            dao.remove(HumanResource.class, id);
        }
    }

    public void setInactive(long tenantId, String username) {
        if(tenantId < 0 || username == null || username.isEmpty()) {
        } else {
            Query<HumanResource> query = ds.createQuery(HumanResource.class);

            query.and(query.criteria("username").equal(username),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<HumanResource> updateOp = ds.createUpdateOperations(HumanResource.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void cleanHumanResources(long tenantId) {
        if (tenantId > 0) {
            ds.delete(ds.createQuery(HumanResource.class).disableValidation().field("className").equal(HumanResource.class.getCanonicalName()));
        }
    }
}
