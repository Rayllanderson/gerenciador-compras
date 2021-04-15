package com.rayllanderson.gerenciadordecompras.api.controllers;

import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserPostRequestBody;
import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserPutPasswordRequestBody;
import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserPutRequestBody;
import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserResponseBody;
import com.rayllanderson.gerenciadordecompras.domain.mapper.UserMapper;
import com.rayllanderson.gerenciadordecompras.domain.model.User;
import com.rayllanderson.gerenciadordecompras.domain.repositories.UserRepository;
import com.rayllanderson.gerenciadordecompras.domain.services.UserService;
import com.rayllanderson.gerenciadordecompras.domain.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserUtil userUtil;

    @GetMapping
    public ResponseEntity<List<UserResponseBody>> findAll() {
        List<UserResponseBody> users =
                userRepository.findAll().stream().map(UserMapper::toUserResponseBody).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseBody> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.toUserResponseBody(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponseBody> register(@RequestBody @Valid UserPostRequestBody user) {
        UserResponseBody dto = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/update/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserPutPasswordRequestBody user,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        user.setId(userUtil.getUserIdByUsername(userDetails.getUsername()));
        userService.updatePassword(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateNameUsernameOrEmail(@RequestBody @Valid UserPutRequestBody user,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        user.setId(userUtil.getUserIdByUsername(userDetails.getUsername()));
        userService.updateNameUsernameOrEmail(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userUtil.getUserIdByUsername(userDetails.getUsername());
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details")
    public ResponseEntity<UserResponseBody> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(UserMapper.toUserResponseBody((User) userDetails));
    }
}