package com.sda.app.service;

import com.sda.app.entity.Favorite;
import com.sda.app.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> findAll() {
        return this.favoriteRepository.findAll();
    }

    public Optional<Favorite> findById(Integer id) {
        return favoriteRepository.findById(id);
    }

    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public Favorite updateFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public void deleteFavoriteById(Integer id) {
        favoriteRepository.deleteById(id);
    }
}
