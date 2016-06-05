package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Route;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class RouteDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public RouteDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Route route) {
        return dao.persist(route);
    }

    public long countAll() {
        return dao.count(Route.class);
    }

    public long countTenant(long tenantId) {
        Query<Route> query = ds.createQuery(Route.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Route getById(ObjectId id) {
        return dao.get(Route.class, id);
    }

    public Route getByLocalId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Route> query = ds.createQuery(Route.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public void remove(ObjectId id) {
        dao.remove(Route.class, id);
    }

    public void setInactive(long tenantId, Long routeId) {
        if (!(tenantId > 0) || (routeId == null)) {
        } else {
            Query<Route> query = ds.createQuery(Route.class);

            query.and(query.criteria("id").equal(routeId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Route> updateOp = ds.createUpdateOperations(Route.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void setInactive(long tenantId, String routeName) {
        if (!(tenantId > 0) || (routeName.isEmpty())) {
        } else {
            Query<Route> query = ds.createQuery(Route.class);

            query.and(query.criteria("name").equal(routeName),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Route> updateOp = ds.createUpdateOperations(Route.class).set("active", false);
            ds.update(query, updateOp);
        }
    }
}
