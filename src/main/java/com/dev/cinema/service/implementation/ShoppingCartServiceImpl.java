package com.dev.cinema.service.implementation;

import com.dev.cinema.dao.interfaces.ShoppingCartDao;
import com.dev.cinema.dao.interfaces.TicketDao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.interfaces.ShoppingCartService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartDao shoppingCartDao;
    private final TicketDao ticketDao;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartDao shoppingCartDao,
                                   TicketDao ticketDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.ticketDao = ticketDao;
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user).get();
    }

    @Override
    public void registerNewShoppingCart(User user) {
        create(new ShoppingCart(user));
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public void addSession(MovieSession movieSession, User user) {
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user).get();
        Ticket ticket = ticketDao.create(new Ticket(movieSession, user));
        List<Ticket> tickets = new ArrayList<>(shoppingCart.getTickets());
        tickets.add(ticket);
        shoppingCart.setTickets(tickets);
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.setTickets(new ArrayList<>());
        update(shoppingCart);
    }
}
