package com.example.user_management.appuser;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/user")
public class AppUserController {
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @GetMapping(path = "{userId}")
    public Optional<AppUser> getUser(@PathVariable("id") long aid) {
        return appUserRepository.findById(aid);
    }
    @PostMapping(consumes = {"application/json"})
    public AppUser addUser(@RequestBody AppUser appUser) {
        return appUserRepository.save(appUser);
    }
    @PostMapping(path="reset")
    public AppUser resetPassword(@RequestBody String password) {
        return appUserService.resetPassword(password);
    }
    @PutMapping(path = "{userId}", consumes = {"application/json"})
    public AppUser updateUser(@PathVariable long userId, @RequestBody AppUser updatedAppUser) {
        return appUserService.updateUser(userId,updatedAppUser);
    }
    @DeleteMapping("{id}")
    public AppUser deleteUser(@PathVariable long id) {
        AppUser a = appUserRepository.findById(id).orElse(new AppUser());
        appUserRepository.delete(a);
        return a;
    }
}
