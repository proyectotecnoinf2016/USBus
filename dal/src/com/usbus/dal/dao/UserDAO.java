package com.usbus.dal.dao;

import com.usbus.commons.auxiliaryClasses.Password;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.HumanResource;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Projection;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by JuanPablo on 4/28/2016.
 */
public class UserDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public UserDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(User user) {
        return dao.persist(user);
    }

    public long countAll() {
        return dao.count(User.class);
    }

    public long countTenant(long tenantId) {
        Query<User> query = ds.createQuery(User.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public User getById(ObjectId id) {
        return dao.get(User.class, id);
    }

    public User getByUsername(long tenantId, String username) {
        if (!(tenantId > 0) || (username.isEmpty())) {
            return null;
        }

        Query<User> query = ds.createQuery(User.class);

        query.and(query.criteria("username").equal(username),
                query.criteria("tenantId").equal(tenantId));

        return query.get();

    }

    public User getByEmail(long tenantId, String email) {
        if (!(tenantId > 0) || (email.isEmpty())) {
            return null;
        }

        Query<User> query = ds.createQuery(User.class);

        query.and(query.criteria("email").equal(email),
                query.criteria("tenantId").equal(tenantId));

        return query.get();

    }

    public List<User> getAllUsers(long tenantId, int offset, int limit) {
        if (!(tenantId > 0)) {
            return null;
        }

        return ds.find(User.class).field("tenantId")
                .equal(tenantId).offset(offset).limit(limit).asList();
    }

    public void remove(ObjectId id) {
        dao.remove(User.class, id);
    }

    public void setInactive(long tenantId, String username) {
        if (!(tenantId > 0) || (username.isEmpty())) {
        } else {
            Query<User> query = ds.createQuery(User.class);

            query.and(query.criteria("username").equal(username),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<User> updateOp = ds.createUpdateOperations(User.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void cleanUsers(long tenantId) {
        if (!(tenantId < 0)) {
            ds.delete(ds.createQuery(User.class));
        }
    }

    public Boolean validatePassword(long tenantId, String username, String password){
        if (!(tenantId > 0) || (username.isEmpty())) {
            return  false;
        } else {
            Query<User> query = ds.createQuery(User.class);
            query.and(query.criteria("username").equal(username),
                    query.criteria("tenantId").equal(tenantId));
            query.retrievedFields(true,"salt","passwordHash");

            User user = query.get();
            return Password.isExpectedPassword(password.toCharArray(),user.getSalt(),user.getPasswordHash());

        }
    }


    public List<Rol> getRoles(User user){
        Query<HumanResource> query = ds.createQuery(HumanResource.class);
        query.disableValidation();
        query.criteria("className").equal(HumanResource.class.getCanonicalName());
        query.and(query.criteria("username").equal(user.getUsername()),
                query.criteria("tenantId").equal(user.getTenantId()));
        query.retrievedFields(true, "roles");
        HumanResource hr = query.get();
        if (hr.getRoles().size() > 0)
            return hr.getRoles();
        else
            return null;



    }
}
