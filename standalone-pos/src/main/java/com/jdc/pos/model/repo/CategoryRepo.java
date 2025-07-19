package com.jdc.pos.model.repo;

import com.jdc.pos.model.BaseRepository;
import com.jdc.pos.model.entity.Account;
import com.jdc.pos.model.entity.Category;

import java.util.List;

public interface CategoryRepo extends BaseRepository<Category, Integer> {
    List<Category> findByCreatedBy(Account createdBy);
}
