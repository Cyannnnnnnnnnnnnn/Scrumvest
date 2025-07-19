package com.jdc.pos.views;

import com.jdc.pos.model.PosException;
import com.jdc.pos.model.entity.Category;
import com.jdc.pos.model.entity.Product;
import com.jdc.pos.model.entity.Usuario;
import com.jdc.pos.model.service.CategoryService;
import com.jdc.pos.model.service.ProductService;
import com.jdc.pos.views.common.Dialog;
import com.jdc.pos.views.popups.ProductEdit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class Products extends AbstractController {

    @FXML
    private ComboBox<Category> category;

    @FXML
    private TextField name;

    @FXML
    private TableView<Product> tableView;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @FXML
    private void initialize() {
        configurarContextMenu();
    }

    private void configurarContextMenu() {
        MenuItem edit = new MenuItem("Editar Tarea");
        edit.setOnAction(event -> {
            Product product = tableView.getSelectionModel().getSelectedItem();
            if (null != product) {
                ProductEdit.edit(product, this::save, () -> categoryService.findAllByUser(usuario.getAccount()));
            }
        });

        MenuItem changeState = new MenuItem("Cambiar estado de la tarea");
        changeState.setOnAction(event -> {
            Product product = tableView.getSelectionModel().getSelectedItem();
            if (product != null) {
                Dialog.DialogBuilder.builder()
                        .title("Cambiar estado de la tarea")
                        .message(String.format("Cambiar estado de %s?", product.getName()))
                        .okActionListener(() -> {
                            product.setValid(!product.isValid());
                            productService.save(product, usuario.getAccount());
                            search();
                        })
                        .build().show();
            }
        });

        tableView.setContextMenu(new ContextMenu(edit, changeState));
    }

    @FXML
    private void search() {
        tableView.getItems().clear();
        List<Product> list = productService.search(category.getValue(), name.getText(), usuario.getAccount());
        tableView.getItems().addAll(list);
    }

    @FXML
    private void clear() {
        category.setValue(null);
        name.clear();
        tableView.getItems().clear();
    }

    @FXML
    private void upload() {
        try {
            Category category = this.category.getValue();
            if (null == category) {
                throw new PosException("Porfavor seleccione la categoria de destino para cambiarla.");
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload Product TSV File");
            Path desktop = Paths.get(System.getProperty("user.home")).resolve("Desktop");
            fileChooser.setInitialDirectory(desktop.toFile());
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Tab Separated File", "*.tsv")
            );

            File file = fileChooser.showOpenDialog(this.category.getScene().getWindow());

            if (null != file) {
                productService.upload(category, file, usuario.getAccount());
                name.clear();
                search();
            }

        } catch (PosException e) {
            Dialog.DialogBuilder.builder().title("Warning")
                    .message(e.getMessage())
                    .build().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addNew() {
        ProductEdit.addNew(this::save, () -> categoryService.findAllByUser(usuario.getAccount()));
    }

    private void save(Product product) {
        productService.save(product, usuario.getAccount());
        category.setValue(product.getCategory());
        search();
    }

    @Override
    protected void onUsuarioSet() {
        if (category != null) {
            category.getItems().setAll(categoryService.findAllByUser(usuario.getAccount()));
        }
    }
}
