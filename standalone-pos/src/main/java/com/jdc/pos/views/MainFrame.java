package com.jdc.pos.views;

import com.jdc.pos.PosApplication;
import com.jdc.pos.model.entity.Usuario;
import com.jdc.pos.utils.Menu;
import com.jdc.pos.views.common.Dialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;


@Controller
public class MainFrame {

    @FXML
    private VBox sideBar;
    @FXML
    private StackPane contentView;

    private Usuario usuario;

    private static Usuario staticUsuario; // para pasar entre métodos estáticos y de instancia

    @FXML
    private void initialize() {
        loadView(Menu.Home);
    }

    @FXML
    private void clickMenu(MouseEvent event) {

        Node node = (Node) event.getSource();

        if(node.getId().equals("Exit")) {
            // need to confirm
            Dialog.DialogBuilder.builder()
                    .title("Confirmar")
                    .message("¿Estás seguro de querer salir de SCRUMVEST???")
                    .okActionListener(() -> sideBar.getScene().getWindow().hide())
                    .build().show();
        } else {
            Menu menu = Menu.valueOf(node.getId());
            loadView(menu);
        }
    }

    private void loadView(Menu menu) {
        try {

            for(Node node : sideBar.getChildren()) {

                node.getStyleClass().remove("active");

                if(node.getId().equals(menu.name())) {
                    node.getStyleClass().add("active");
                }
            }

            contentView.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(menu.getFxml()));
            loader.setControllerFactory(PosApplication.getApplicationContext()::getBean);
            Parent view = loader.load();

            AbstractController controller = loader.getController();
            controller.setUsuario(usuario); // ← primero asignamos usuario
            controller.setTitle(menu);      // luego el título



            // Pasar el usuario a cada controlador cargado
            controller.setUsuario(usuario);

            contentView.getChildren().add(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void show(Usuario usuario) {
    staticUsuario = usuario;

    try {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainFrame.class.getResource("MainFrame.fxml"));
        
        // Custom ControllerFactory que permite pasar el usuario ANTES del initialize()
        loader.setControllerFactory(param -> {
            Object controller = PosApplication.getApplicationContext().getBean(param);
            if (controller instanceof MainFrame) {
                ((MainFrame) controller).setUsuario(staticUsuario); // ✅ Aquí sí está a tiempo
            }
            return controller;
        });

        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}



    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
