package com.jdc.pos.model.service;

import com.jdc.pos.model.PosException;
import com.jdc.pos.model.entity.Account;
import com.jdc.pos.model.entity.Category;
import com.jdc.pos.model.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo repo;

    public List<Category> findAll() {
        return repo.findAll();
    }
    public List<Category> findAllByUser(Account usuario) {
        return repo.findByCreatedBy(usuario);
    }

public void save(Category category, Account usuario) {
    if(StringUtils.isEmpty(category.getName())) {
        throw new PosException("Por favor ingrese el nombre de la Categoria.");
    }

    if (category.getCreatedBy() == null) {
        category.setCreatedBy(usuario);
    }

    repo.save(category);
}

}
