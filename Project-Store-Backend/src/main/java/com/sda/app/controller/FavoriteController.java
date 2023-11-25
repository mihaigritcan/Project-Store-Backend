package com.sda.app.controller;

import com.sda.app.dto.FavoriteDto;
import com.sda.app.entity.Favorite;
import com.sda.app.entity.User;
import com.sda.app.service.FavoriteService;
import com.sda.app.service.UserService;
import com.sda.app.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    public FavoriteService favoriteService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllFavCarts() {
        List<Favorite> favoriteList = this.favoriteService.findAll();
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("List de favorite")
                .data(favoriteList)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createFavCart(@RequestBody FavoriteDto favoriteDto) {
        System.out.println(favoriteDto.getUserId());

        try {
            Optional<User> optionalUser = this.userService.findById(favoriteDto.getUserId());

            if(optionalUser.isPresent()) {
                User user = optionalUser.get();

                Favorite favorite = new Favorite();

                favorite.setUser(user);
                favorite.setItems(favoriteDto.getItems());

                ApiResponse response = new ApiResponse.Builder()
                        .status(200)
                        .message("Cos creat cu success")
                        .data(favoriteService.createFavorite(favorite))
                        .build();
                return ResponseEntity.ok(response);
            } else {
                throw new Exception("Invalid user");
            }
        } catch (Exception exception) {
            ApiResponse response = new ApiResponse.Builder()
                    .status(500)
                    .message(exception.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateFavCart(@RequestBody FavoriteDto favoriteDto, @PathVariable("id") Integer id) {
        try {
            Optional<User> optionalUser = this.userService.findById(favoriteDto.getUserId());

            if(optionalUser.isPresent()) {
                User user = optionalUser.get();

                Favorite favorite = new Favorite();

                favorite.setId(id);
                favorite.setUser(user);
                favorite.setItems(favoriteDto.getItems());

                ApiResponse response = new ApiResponse.Builder()
                        .status(200)
                        .message("Cos actualizat cu success")
                        .data(favoriteService.updateFavorite(favorite))
                        .build();
                return ResponseEntity.ok(response);
            } else {
                throw new Exception("Invalid user");
            }
        } catch (Exception exception) {
            ApiResponse response = new ApiResponse.Builder()
                    .status(500)
                    .message(exception.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFavCart(@PathVariable("id") Integer id) {
        favoriteService.deleteFavoriteById(id);

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Cos sters cu success")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
