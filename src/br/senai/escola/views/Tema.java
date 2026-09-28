package br.senai.escola.views;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;

/** Componentes compartilhados para manter todas as telas consistentes. */
public final class Tema {
    public static final Color FUNDO = new Color(245, 245, 247);
    public static final Color TEXTO = new Color(29, 29, 31);
    public static final Color SECUNDARIO = new Color(110, 110, 115);
    public static final Color AZUL = new Color(0, 113, 227);
    private static String familia = "SansSerif";

    private Tema() { }

    public static void instalar() {
        var fontes = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames());
        for (String nome : new String[]{"SF Pro Text", "SF Pro Display", "SF Pro", "Segoe UI", "SansSerif"}) {
            if (fontes.contains(nome)) { familia = nome; break; }
        }
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) { /* O tema funciona com o look and feel padrão. */ }
        for (Object chave : UIManager.getDefaults().keySet().toArray()) {
            if (UIManager.get(chave) instanceof Font) UIManager.put(chave, new FontUIResource(fonte(14, false)));
        }
        UIManager.put("Panel.background", FUNDO);
        UIManager.put("Label.foreground", TEXTO);
        UIManager.put("OptionPane.background", FUNDO);
        UIManager.put("TextField.selectionBackground", new Color(205, 228, 255));
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", TEXTO);
    }

    public static Font fonte(int tamanho, boolean negrito) {
        return new Font(familia, negrito ? Font.BOLD : Font.PLAIN, tamanho);
    }

    public static JPanel painel(LayoutManager layout) {
        JPanel painel = new JPanel(layout);
        painel.setOpaque(false);
        return painel;
    }

    public static JPanel cartao(LayoutManager layout) {
        JPanel painel = new JPanel(layout) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D desenho = (Graphics2D) g.create();
                desenho.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                desenho.setColor(Color.WHITE);
                desenho.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                desenho.dispose();
                super.paintComponent(g);
            }
        };
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(24, 24, 24, 24));
        return painel;
    }

    public static JLabel texto(String texto, int tamanho, boolean negrito, Color cor) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setFont(fonte(tamanho, negrito));
        rotulo.setForeground(cor);
        return rotulo;
    }

    public static JPanel cabecalho(String titulo, String descricao) {
        JPanel painel = painel(new BorderLayout(0, 9));
        painel.add(texto(titulo, 32, true, TEXTO), BorderLayout.NORTH);
        painel.add(texto(descricao, 14, false, SECUNDARIO), BorderLayout.CENTER);
        painel.setBorder(new EmptyBorder(0, 0, 8, 0));
        return painel;
    }

    public static JButton botao(String titulo, boolean principal) {
        JButton botao = new JButton(titulo) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D desenho = (Graphics2D) g.create();
                desenho.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color cor = principal ? AZUL : new Color(237, 243, 251);
                if (getModel().isPressed()) cor = cor.darker();
                else if (getModel().isRollover()) cor = principal ? new Color(0, 125, 250) : new Color(222, 235, 252);
                desenho.setColor(cor);
                desenho.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                if (isFocusOwner()) {
                    desenho.setColor(principal ? Color.WHITE : AZUL);
                    desenho.drawRoundRect(3, 3, getWidth() - 7, getHeight() - 7, 14, 14);
                }
                desenho.dispose();
                super.paintComponent(g);
            }
        };
        botao.setForeground(principal ? Color.WHITE : AZUL);
        botao.setFont(fonte(14, true));
        botao.setContentAreaFilled(false);
        botao.setBorder(new EmptyBorder(13, 20, 13, 20));
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    public static JPanel campo(String nome, JComponent componente) {
        JPanel painel = painel(new BorderLayout(0, 8));
        JLabel rotulo = texto(nome, 13, true, SECUNDARIO);
        rotulo.setLabelFor(componente);
        painel.add(rotulo, BorderLayout.NORTH);
        componente.setPreferredSize(new Dimension(180, 42));
        if (componente instanceof JTextField) {
            componente.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 215)),
                    new EmptyBorder(10, 12, 10, 12)));
        }
        painel.add(componente, BorderLayout.CENTER);
        return painel;
    }

    public static JPanel tabela(JTable tabela, String titulo) {
        JPanel painel = cartao(new BorderLayout(0, 16));
        painel.add(texto(titulo, 18, true, TEXTO), BorderLayout.NORTH);
        tabela.setRowHeight(42);
        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setSelectionBackground(new Color(225, 239, 255));
        tabela.setSelectionForeground(TEXTO);
        tabela.setFillsViewportHeight(true);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().setBackground(FUNDO);
        tabela.getTableHeader().setForeground(SECUNDARIO);
        tabela.getTableHeader().setFont(fonte(12, true));
        tabela.getTableHeader().setPreferredSize(new Dimension(0, 36));
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object valor,
                    boolean selecionado, boolean foco, int linha, int coluna) {
                super.getTableCellRendererComponent(t, valor, selecionado, foco, linha, coluna);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                if (!selecionado) setBackground(linha % 2 == 0 ? Color.WHITE : new Color(250, 250, 252));
                return this;
            }
        });
        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBorder(BorderFactory.createEmptyBorder());
        rolagem.getViewport().setBackground(Color.WHITE);
        painel.add(rolagem, BorderLayout.CENTER);
        return painel;
    }

    public static void configurar(JFrame janela, JPanel conteudo, int largura, int altura) {
        conteudo.setBackground(FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 36, 32, 36));
        janela.setContentPane(conteudo);
        janela.setSize(largura, altura);
        janela.setMinimumSize(new Dimension(largura, altura));
        janela.setLocationRelativeTo(null);
    }
}
