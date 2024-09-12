
import javax.swing.*;  // Importa as classes da biblioteca Swing para criar a interface gráfica.
import java.awt.*;      // Importa as classes para manipulação de layouts e cores.
import java.awt.event.*;  // Importa as classes para lidar com eventos (como cliques de botões).
import java.util.Random;   // Importa a classe Random para gerar números aleatórios.

public class JogoAdivinhacao extends JFrame {

    // Variáveis para armazenar o número a ser adivinhado, tentativas restantes, pontuação e nível de dificuldade.
    private int numeroAleatorio;
    private int tentativasRestantes;
    private int pontuacao = 0;
    private int nivelDificuldade;

    // Componentes da interface gráfica.
    private JLabel labelDica;  // Exibe dicas para o jogador.
    private JTextField campoNumero;  // Campo onde o jogador insere o palpite.
    private JButton botaoTentar;  // Botão que o jogador clica para tentar adivinhar o número.
    private JButton botaoIniciar;  // Botão para iniciar o jogo.
    private JLabel labelPontuacao;  // Exibe a pontuação do jogador.

    // Construtor da classe JogoAdivinhacao.
    public JogoAdivinhacao() {
        // Configurações da janela (título, tamanho e comportamento de fechar).
        setTitle("Jogo de Adivinhação - Modo Escuro");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Usando layout absoluto para posicionar os componentes manualmente.

        // Modo escuro: define o fundo da janela como cinza escuro.
        getContentPane().setBackground(Color.DARK_GRAY);

        // Configura o componente de dica.
        labelDica = new JLabel("Escolha um nível de dificuldade e inicie o jogo.");
        labelDica.setBounds(50, 20, 300, 30);  // Define a posição e tamanho do rótulo.
        labelDica.setForeground(Color.WHITE);  // Define a cor do texto como branco.
        add(labelDica);  // Adiciona o rótulo à janela.

        // Configura o campo de texto onde o jogador insere o número.
        campoNumero = new JTextField();
        campoNumero.setBounds(50, 60, 100, 30);  // Define a posição e tamanho.
        campoNumero.setBackground(Color.LIGHT_GRAY);  // Cor de fundo cinza claro.
        campoNumero.setForeground(Color.BLACK);  // Cor do texto preto.
        campoNumero.setEnabled(false);  // Inicialmente desabilitado até que o jogo comece.
        add(campoNumero);

        // Configura o botão "Tentar".
        botaoTentar = new JButton("Tentar");
        botaoTentar.setBounds(160, 60, 100, 30);  // Define a posição e tamanho.
        botaoTentar.setBackground(Color.GRAY);  // Define a cor de fundo como cinza.
        botaoTentar.setForeground(Color.WHITE);  // Cor do texto branco.
        botaoTentar.setEnabled(false);  // Inicialmente desabilitado até que o jogo comece.
        add(botaoTentar);

        // Configura o botão "Iniciar Jogo".
        botaoIniciar = new JButton("Iniciar Jogo");
        botaoIniciar.setBounds(50, 100, 130, 30);  // Define a posição e tamanho.
        botaoIniciar.setBackground(Color.GRAY);  // Cor de fundo cinza.
        botaoIniciar.setForeground(Color.WHITE);  // Texto branco.
        add(botaoIniciar);

        // Configura o rótulo da pontuação.
        labelPontuacao = new JLabel("Pontuação: 0");
        labelPontuacao.setBounds(200, 100, 150, 30);  // Define a posição e tamanho.
        labelPontuacao.setForeground(Color.WHITE);  // Cor do texto branco.
        add(labelPontuacao);

        // Ação para o botão "Iniciar Jogo".
        botaoIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define opções de níveis de dificuldade para o jogador escolher.
                String[] opcoes = {"Fácil (1-10)", "Médio (1-50)", "Difícil (1-100)"};
                int escolha = JOptionPane.showOptionDialog(null, "Escolha um nível de dificuldade",
                        "Nível de Dificuldade", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

                // Configura o nível de dificuldade e o número de tentativas baseado na escolha.
                if (escolha == 0) {
                    nivelDificuldade = 10;
                    tentativasRestantes = 5;
                } else if (escolha == 1) {
                    nivelDificuldade = 50;
                    tentativasRestantes = 7;
                } else if (escolha == 2) {
                    nivelDificuldade = 100;
                    tentativasRestantes = 10;
                }

                // Inicia o jogo.
                iniciarJogo();
            }
        });

        // Ação para o botão "Tentar".
        botaoTentar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Método que tenta adivinhar o número.
                tentarAdivinhar();
            }
        });
    }

    // Método para iniciar o jogo.
    private void iniciarJogo() {
        Random random = new Random();
        // Gera um número aleatório entre 1 e o nível de dificuldade.
        numeroAleatorio = random.nextInt(nivelDificuldade) + 1;
        // Habilita o campo de texto e o botão "Tentar".
        campoNumero.setEnabled(true);
        botaoTentar.setEnabled(true);
        // Atualiza a dica para o jogador.
        labelDica.setText("Tente adivinhar o número (1-" + nivelDificuldade + ")");
    }

    // Método para tentar adivinhar o número.
    private void tentarAdivinhar() {
        try {
            // Converte o valor do campo de texto para um número inteiro.
            int palpite = Integer.parseInt(campoNumero.getText());

            // Verifica se o palpite está dentro dos limites válidos.
            if (palpite < 1 || palpite > nivelDificuldade) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um número entre 1 e " + nivelDificuldade);
                return;
            }

            tentativasRestantes--;

            // Verifica se o palpite está correto.
            if (palpite == numeroAleatorio) {
                // Atualiza a pontuação baseada nas tentativas restantes.
                pontuacao += tentativasRestantes * 10;
                labelPontuacao.setText("Pontuação: " + pontuacao);
                JOptionPane.showMessageDialog(this, "Parabéns! Você acertou!");
                reiniciarJogo();
            } else if (tentativasRestantes == 0) {
                // Caso o jogador esgote as tentativas.
                JOptionPane.showMessageDialog(this, "Você perdeu! O número era: " + numeroAleatorio);
                reiniciarJogo();
            } else if (palpite < numeroAleatorio) {
                // Dá uma dica de que o número é maior.
                labelDica.setText("O número é maior! Tentativas restantes: " + tentativasRestantes);
            } else {
                // Dá uma dica de que o número é menor.
                labelDica.setText("O número é menor! Tentativas restantes: " + tentativasRestantes);
            }

            // Limpa o campo de texto.
            campoNumero.setText("");
        } catch (NumberFormatException e) {
            // Caso o jogador insira um valor não numérico.
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido.");
        }
    }

    // Método para reiniciar o jogo.
    private void reiniciarJogo() {
        // Desabilita o campo de texto e o botão "Tentar".
        campoNumero.setEnabled(false);
        botaoTentar.setEnabled(false);
        // Atualiza a dica para o jogador.
        labelDica.setText("Escolha um nível de dificuldade e inicie o jogo.");
        campoNumero.setText("");
    }

    // Método principal para iniciar o programa.
    public static void main(String[] args) {
        // Executa a interface gráfica na thread de eventos do Swing.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JogoAdivinhacao().setVisible(true);
            }
        });
    }
}
