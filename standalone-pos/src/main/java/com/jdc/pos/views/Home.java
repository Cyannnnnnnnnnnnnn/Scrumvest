package com.jdc.pos.views;

import com.jdc.pos.model.entity.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Controller;

@Controller
public class Home extends AbstractController {

    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblRol;
    @FXML
    private Label lblProyectosActivos;

    @FXML
    private Label lblTareasAsignadas;

    @FXML
    private Label lblProductividad;

    @Override
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

        System.out.println("Usuario recibido en Home: " + usuario); // debug

        if (lblBienvenida != null && usuario != null) {
            lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
        }
        if (lblRol != null && usuario != null) {
            lblRol.setText("Rol: " + usuario.getRol());
        }
    }


    @FXML
    private void initialize() {
        // Aquí sí, porque ya está inicializado lblBienvenida
        if (usuario != null && lblBienvenida != null) {
            lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
        }
    }
}
