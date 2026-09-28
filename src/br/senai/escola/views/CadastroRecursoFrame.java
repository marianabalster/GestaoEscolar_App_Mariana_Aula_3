package br.senai.escola.views;

import br.senai.escola.models.Espaco;
import br.senai.escola.models.RecursoEducacional;
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

public class CadastroRecursoFrame extends JFrame {
    private final JTextField txtPatrimonio = new JTextField(20);
    private final JTextField txtDescricao = new JTextField(20);
    private final JTextField txtQuantidade = new JTextField(20);
    private final JComboBox<String> cmbCategoria =
            new JComboBox<>(new String[]{"Equipamento", "Material didático", "Mobiliário", "Outro"});
    private final JComboBox<Espaco> cmbEspaco = new JComboBox<>();
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new Object[]{"Patrimônio", "Descrição", "Categoria", "Quantidade", "Espaço"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };

    public CadastroRecursoFrame() {
        super("Cadastro de Recursos Educacionais");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel conteudo = new JPanel(new BorderLayout(0, 24));
        JPanel superior = Tema.painel(new BorderLayout(0, 20));
        superior.add(Tema.cabecalho("Recursos educacionais", "Tudo o que sua escola precisa, em um só lugar."), BorderLayout.NORTH);
        JPanel cartao = Tema.cartao(new BorderLayout(0, 20));
        JPanel formulario = Tema.painel(new GridLayout(2, 2, 20, 16));
        formulario.add(Tema.campo("Patrimônio", txtPatrimonio));
        formulario.add(Tema.campo("Descrição", txtDescricao));
        formulario.add(Tema.campo("Categoria", cmbCategoria));
        formulario.add(Tema.campo("Quantidade", txtQuantidade));
        cartao.add(formulario, BorderLayout.NORTH);
        JPanel escolhaEspaco = Tema.painel(new BorderLayout(12, 0));
        escolhaEspaco.add(cmbEspaco, BorderLayout.CENTER);
        JButton btnAtualizarEspacos = Tema.botao("Atualizar espaços", false);
        btnAtualizarEspacos.addActionListener(e -> carregarEspacos());
        escolhaEspaco.add(btnAtualizarEspacos, BorderLayout.EAST);
        cartao.add(Tema.campo("Espaço associado", escolhaEspaco), BorderLayout.CENTER);
        JButton btnCadastrar = Tema.botao("Cadastrar recurso", true);
        btnCadastrar.addActionListener(e -> cadastrar());
        JPanel acoes = Tema.painel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        acoes.add(btnCadastrar);
        cartao.add(acoes, BorderLayout.SOUTH);
        superior.add(cartao, BorderLayout.CENTER);
        conteudo.add(superior, BorderLayout.NORTH);
        conteudo.add(Tema.tabela(new JTable(modeloTabela), "Recursos cadastrados"), BorderLayout.CENTER);
        getRootPane().setDefaultButton(btnCadastrar);
        Tema.configurar(this, conteudo, 960, 740);
        carregarEspacos();
        atualizarTabela();
        setLocationRelativeTo(null);
    }

    @Override public void setVisible(boolean visivel) {
        if (visivel) carregarEspacos();
        super.setVisible(visivel);
    }

    private void carregarEspacos() {
        Espaco selecionado = (Espaco) cmbEspaco.getSelectedItem();
        cmbEspaco.removeAllItems();
        for (Espaco item : BancoMemoria.listarEspacos()) cmbEspaco.addItem(item);
        if (selecionado != null) selecionarEspacoPorCodigo(selecionado.getCodigo());
    }

    private void selecionarEspacoPorCodigo(String codigo) {
        for (int i = 0; i < cmbEspaco.getItemCount(); i++) {
            if (cmbEspaco.getItemAt(i).getCodigo().equalsIgnoreCase(codigo)) {
                cmbEspaco.setSelectedIndex(i); return;
            }
        }
    }

    private void cadastrar() {
        String patrimonio = txtPatrimonio.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String categoria = (String) cmbCategoria.getSelectedItem();
        Espaco espaco = (Espaco) cmbEspaco.getSelectedItem();
        if (patrimonio.isBlank() || descricao.isBlank()) {
            exibirErro("Preencha patrimônio e descrição."); return;
        }
        if (espaco == null) {
            exibirErro("Cadastre e selecione um espaço antes de cadastrar o recurso."); return;
        }
        int quantidade;
        try {
            quantidade = Integer.parseInt(txtQuantidade.getText().trim());
        } catch (NumberFormatException erro) {
            exibirErro("Use um número inteiro para a quantidade.");
            txtQuantidade.requestFocus(); return;
        }
        if (quantidade <= 0) {
            exibirErro("A quantidade deve ser maior que zero."); return;
        }
        if (BancoMemoria.existePatrimonio(patrimonio)) {
            exibirErro("Patrimônio já cadastrado."); return;
        }
        BancoMemoria.adicionarRecurso(new RecursoEducacional(
                patrimonio, descricao, categoria, quantidade, espaco));
        atualizarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Recurso cadastrado com sucesso!");
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (RecursoEducacional recurso : BancoMemoria.listarRecursos()) {
            modeloTabela.addRow(new Object[]{recurso.getPatrimonio(), recurso.getDescricao(),
                    recurso.getCategoria(), recurso.getQuantidade(), recurso.getEspaco().getCodigo()});
        }
    }

    private void limparCampos() {
        txtPatrimonio.setText(""); txtDescricao.setText(""); txtQuantidade.setText("");
        txtPatrimonio.requestFocus();
    }

    private void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}
