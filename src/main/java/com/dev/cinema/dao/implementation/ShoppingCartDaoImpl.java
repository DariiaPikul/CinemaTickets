package com.dev.cinema.dao.implementation;

import com.dev.cinema.dao.AbstractDao;
import com.dev.cinema.dao.interfaces.ShoppingCartDao;
import com.dev.cinema.model.entity.ShoppingCart;
import com.dev.cinema.model.entity.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl extends AbstractDao<ShoppingCart> implements ShoppingCartDao {
    protected ShoppingCartDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Optional<ShoppingCart> getByUser(User user) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM ShoppingCart sc left join fetch sc.tickets "
                            + "join fetch sc.user WHERE sc.user = :user", ShoppingCart.class)
                    .setParameter("user", user)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<ShoppingCart> getAll(Class<ShoppingCart> clazz) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ShoppingCart> getAllShoppingCartsQuery =
                    criteriaBuilder.createQuery(ShoppingCart.class);
            Root<ShoppingCart> root = getAllShoppingCartsQuery.from(ShoppingCart.class);
            root.fetch("tickets", JoinType.LEFT);
            root.fetch("user", JoinType.LEFT);
            getAllShoppingCartsQuery.select(root);
            return session.createQuery(getAllShoppingCartsQuery).getResultList();
        }
    }
}
