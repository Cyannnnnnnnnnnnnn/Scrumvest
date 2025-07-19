package com.jdc.pos.views;

import com.jdc.pos.model.entity.Usuario;
import com.jdc.pos.utils.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public abstract class AbstractController {

    protected Usuario usuario;

    @FXML
    private Label headerTitle;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        onUsuarioSet();
    }

    public void setTitle(Menu menu) {
        if (headerTitle != null) {
            headerTitle.setText(menu.getTitle());
        }
    }

    // Método que puede ser sobrescrito por subclases para usar el usuario ya asignado
    protected void onUsuarioSet() {
        // Por defecto no hace nada
    }
}
