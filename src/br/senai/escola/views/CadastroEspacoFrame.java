package br.senai.escola.views;

import br.senai.escola.models.Espaco;
import br.senai.escola.repository.BancoMemoria;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CadastroEspacoFrame extends JFrame {
    private final JTextField txtCodigo = new JTextField(20);
    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtCapacidade = new JTextField(20);
    private final JComboBox<String> cmbTipo =
            new JComboBox<>(new String[]{"Sala", "Laboratório", "Auditório"});
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new Object[]{"Código", "Nome", "Tipo", "Capacidade"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };

    public CadastroEspacoFrame() {
        super("Cadastro de Espaços");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel conteudo = new JPanel(new BorderLayout(0, 24));
        JPanel superior = Tema.painel(new BorderLayout(0, 20));
        superior.add(Tema.cabecalho("Espaços", "Organize os ambientes onde o aprendizado acontece."), BorderLayout.NORTH);
        JPanel cartao = Tema.cartao(new BorderLayout(0, 20));
        JPanel formulario = Tema.painel(new GridLayout(2, 2, 20, 16));
        formulario.add(Tema.campo("Código", txtCodigo));
        formulario.add(Tema.campo("Nome", txtNome));
        formulario.add(Tema.campo("Tipo", cmbTipo));
        formulario.add(Tema.campo("Capacidade", txtCapacidade));
        cartao.add(formulario, BorderLayout.NORTH);

        JButton btnCadastrar = Tema.botao("Cadastrar espaço", true);
        btnCadastrar.addActionListener(e -> cadastrar());
        JPanel acoes = Tema.painel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        acoes.add(btnCadastrar);
        cartao.add(acoes, BorderLayout.SOUTH);
        superior.add(cartao, BorderLayout.CENTER);
        conteudo.add(superior, BorderLayout.NORTH);
        conteudo.add(Tema.tabela(new JTable(modeloTabela), "Espaços cadastrados"), BorderLayout.CENTER);
        getRootPane().setDefaultButton(btnCadastrar);
        Tema.configurar(this, conteudo, 960, 740);
        atualizarTabela();
        setLocationRelativeTo(null);
    }

    private void cadastrar() {
        String codigo = txtCodigo.getText().trim();
        String nome = txtNome.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();
        if (codigo.isBlank() || nome.isBlank()) {
            exibirErro("Preencha o código e o nome."); return;
        }
        int capacidade;
        try {
            capacidade = Integer.parseInt(txtCapacidade.getText().trim());
        } catch (NumberFormatException erro) {
            exibirErro("Use um número inteiro para a capacidade.");
            txtCapacidade.requestFocus(); return;
        }
        if (capacidade <= 0) {
            exibirErro("A capacidade deve ser maior que zero."); return;
        }
        if (BancoMemoria.existeCodigoEspaco(codigo)) {
            exibirErro("Código já cadastrado."); return;
        }
        BancoMemoria.adicionarEspaco(new Espaco(codigo, nome, tipo, capacidade));
        atualizarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Espaço cadastrado com sucesso!");
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Espaco espaco : BancoMemoria.listarEspacos()) {
            modeloTabela.addRow(new Object[]{espaco.getCodigo(), espaco.getNome(),
                    espaco.getTipo(), espaco.getCapacidade()});
        }
    }

    private void limparCampos() {
        txtCodigo.setText(""); txtNome.setText(""); txtCapacidade.setText("");
        txtCodigo.requestFocus();
    }

    private void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}
