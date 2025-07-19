package com.jdc.pos.views;

import com.jdc.pos.model.entity.Account;
import com.jdc.pos.model.service.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Register {

    @FXML
    private TextField txtLoginId, txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<Account.Role> cmbRole;

    @FXML
    private Label lblMessage;

    @Autowired
    private AccountService accountService;

    @FXML
    private void initialize() {
        cmbRole.getItems().setAll(Account.Role.values());
    }

    @FXML
    private void register() {
        try {
            Account acc = new Account();
            acc.setLoginId(txtLoginId.getText());
            acc.setName(txtName.getText());
            acc.setPassword(txtPassword.getText());
            acc.setRol(cmbRole.getValue());

            accountService.save(acc);

            lblMessage.setStyle("-fx-text-fill: green");
            lblMessage.setText("Usuario creado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setStyle("-fx-text-fill: red");
            lblMessage.setText("Error al registrar: " + e.getMessage());
        }
    }
}
