package br.senai.escola.app;

import br.senai.escola.views.MenuPrincipal;
import br.senai.escola.views.Tema;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tema.instalar();
            new MenuPrincipal().setVisible(true);
        });
    }
}
