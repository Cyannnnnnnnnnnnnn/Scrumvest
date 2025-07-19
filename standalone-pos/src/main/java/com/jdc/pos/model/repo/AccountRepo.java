package com.jdc.pos.model.repo;

import com.jdc.pos.model.BaseRepository;
import com.jdc.pos.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends BaseRepository<Account, String> {
}
