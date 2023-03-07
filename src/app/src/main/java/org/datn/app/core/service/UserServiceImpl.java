package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.User;
import org.datn.app.core.repo.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User doInsert(User user) {
        return userRepo.save(user);
    }

    @Override
    public User doUpdateById(User user, Long aLong) {
        User user1 = userRepo.findById(aLong).get();
        user1.setAddress(user.getAddress());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setFullName(user.getFullName());
        user1.setModified(new Date());
        return userRepo.save(user1);
    }

    @Override
    public User doDeleteById(Long aLong) {
        userRepo.deleteById(aLong);
        return null;
    }

    @Override
    public User findById(Long aLong) {
        return userRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return userRepo.findAll(pageable);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
