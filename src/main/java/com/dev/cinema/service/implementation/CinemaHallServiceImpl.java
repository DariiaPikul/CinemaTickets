package com.dev.cinema.service.implementation;

import com.dev.cinema.dao.interfaces.CinemaHallDao;
import com.dev.cinema.model.entity.CinemaHall;
import com.dev.cinema.service.interfaces.CinemaHallService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    private final CinemaHallDao cinemaHallDao;

    @Autowired
    public CinemaHallServiceImpl(CinemaHallDao cinemaHallDao) {
        this.cinemaHallDao = cinemaHallDao;
    }

    @Override
    public CinemaHall create(CinemaHall cinemaHall) {
        return cinemaHallDao.create(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id, CinemaHall.class).get();
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll(CinemaHall.class);
    }

    @Override
    public void update(CinemaHall cinemaHall) {
        cinemaHallDao.update(cinemaHall);
    }
}
