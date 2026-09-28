package br.senai.escola.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        super("Gestão Escolar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel conteudo = new JPanel(new BorderLayout(0, 32));
        JPanel introducao = Tema.painel(new BorderLayout(0, 22));
        introducao.add(Tema.texto("ESCOLA  /  VISÃO GERAL", 12, true, Tema.SECUNDARIO), BorderLayout.NORTH);
        introducao.add(Tema.cabecalho("Mais organização. Mais possibilidades.",
                "Um espaço simples para cuidar da sua escola."), BorderLayout.CENTER);
        conteudo.add(introducao, BorderLayout.NORTH);
        JPanel atalhos = Tema.painel(new GridLayout(1, 2, 24, 0));
        atalhos.add(atalho("01", "Espaços", "Salas, laboratórios e auditórios.",
                "Gerenciar espaços", () -> new CadastroEspacoFrame().setVisible(true)));
        atalhos.add(atalho("02", "Recursos educacionais", "Materiais e equipamentos conectados.",
                "Gerenciar recursos", () -> new CadastroRecursoFrame().setVisible(true)));
        conteudo.add(atalhos, BorderLayout.CENTER);
        conteudo.add(Tema.texto("Gestão Escolar   •   Os dados ficam disponíveis durante esta sessão.",
                12, false, Tema.SECUNDARIO), BorderLayout.SOUTH);
        Tema.configurar(this, conteudo, 920, 500);
    }

    private JPanel atalho(String numero, String titulo, String descricao, String acao, Runnable abrir) {
        JPanel cartao = Tema.cartao(new BorderLayout(0, 20));
        cartao.add(Tema.texto(numero, 28, true, Tema.AZUL), BorderLayout.NORTH);
        JPanel detalhes = Tema.painel(new BorderLayout(0, 10));
        detalhes.add(Tema.texto(titulo, 23, true, Tema.TEXTO), BorderLayout.NORTH);
        detalhes.add(Tema.texto(descricao, 14, false, Tema.SECUNDARIO), BorderLayout.CENTER);
        cartao.add(detalhes, BorderLayout.CENTER);
        JButton botao = Tema.botao(acao, true);
        botao.addActionListener(e -> abrir.run());
        cartao.add(botao, BorderLayout.SOUTH);
        return cartao;
    }
}