package com.jdc.pos.model.service;

import com.jdc.pos.model.PosException;
import com.jdc.pos.model.entity.Account;
import com.jdc.pos.model.entity.Category;
import com.jdc.pos.model.entity.Product;
import com.jdc.pos.model.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

  

    @Autowired
    private ProductRepo productRepo;

    // ✅ Buscar productos solo del usuario logueado
    public List<Product> search(Category category, String name, Account usuario) {
        StringBuilder sb = new StringBuilder("select p from Product p where p.usuario = :usuario");
        Map<String, Object> params = new HashMap<>();
        params.put("usuario", usuario);

        if (null != category) {
            sb.append(" and p.category = :category");
            params.put("category", category);
        }

        if (!StringUtils.isEmpty(name)) {
            sb.append(" and lower(p.name) like lower(:name)");
            params.put("name", name.concat("%"));
        }

        return productRepo.findByQuery(sb.toString(), params);
    }

    public void save(Product product, Account usuario) {
        if (null == product.getCategory()) {
            throw new PosException("Por favor seleccione la Categoria.");
        }

        if (StringUtils.isEmpty(product.getName())) {
            throw new PosException("Por favor Ingrese en la cantidad sprint de la Tarea .");
        }

        if (product.getPrice() == 0) {
            throw new PosException("Please enter Product Price.");
        }

        if (product.getUsuario() == null) {
            product.setUsuario(usuario); // ← asignar usuario si no se ha asignado
        }

        if (product.getCreatedBy() == null) {
            product.setCreatedBy(usuario); // ✅ ← asignar createdBy también
        }

        productRepo.save(product);
    }


    // ✅ Carga masiva con usuario
    @Transactional
    public void upload(Category category, File file, Account usuario) {
        try {
            Files.lines(file.toPath())
                .map(line -> line.split("\t"))
                .filter(array -> array.length >= 2)
                .map(array -> {
                    try {
                        Product product = new Product();
                        product.setCategory(category);
                        product.setName(array[0]);
                        product.setPrice(Integer.parseInt(array[1]));
                        product.setUsuario(usuario); // ✅ Asignar creador
                        if (array.length > 2) {
                            product.setRemark(array[2]);
                        }
                        return product;
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(product -> null != product)
                .forEach(productRepo::save);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
