package jogoMemorizacao;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Controle {
    
    private String jogador;
    private int pontuacao;
    private int rodada;
    private String dificuldade;
    private Tela telaPrincipal;
    private JButton[] listaBotoes;
    private JButton botaoIniciar;    
    private boolean sequenciaAutomaticaIniciada;
    private boolean rodadaIniciada = false;
    private boolean jogoIniciado;
    private int qtdSequencia = 3;
    private JButton[] sequenciaBotao;
    private boolean sequenciaAutomatica = false;
    private int indiceUsuario = 0;
    private int delay = 500;
    private int multPontuacao;
    private int qtAcertos;
   
    
    
    public Controle(Tela telaPrincipal) {        
        this.telaPrincipal=telaPrincipal;
        this.listaBotoes=telaPrincipal.obterInstanciaBotoesAcao();
        this.botaoIniciar=telaPrincipal.obterInstaciaBotaoIniciar();
        
        
    }

 public void selecaoDificuldade(String dificuldade){
        switch(dificuldade.toLowerCase()){
            case "fácil":
                delay = 500;
                multPontuacao = 2;
                break;
            case "médio":
                delay = 200;
                multPontuacao = 4;
                break;
            case "díficil":
                delay = 50;
                multPontuacao = 8;
                break;
            default: System.out.println("nenhuma dificuldade encontrada");
        }
 }
    public void iniciarNovoJogo(String jogador, String dificuldade){
        this.dificuldade= dificuldade;
        this.jogador = jogador;
        selecaoDificuldade(dificuldade);
        pontuacao = 0;
        jogoIniciado = true;
        qtdSequencia = 3;
        JOptionPane.showMessageDialog(null, "novo jogo iniciado");
        
    }
    public void iniciarRodada(){
        
        rodadaIniciada = true;
        rodada++;
        sequenciaAutomatica = true;
        indiceUsuario = 0;
        sequenciaBotao = gerarSequenciaBotoes(gerarSequenciaNumerica(qtdSequencia));
        botaoIniciar.setEnabled(false);
        pintarBotoesSequencia(sequenciaBotao);
        telaPrincipal.atualizarRodada(String.valueOf(rodada));
        telaPrincipal.atualizarPontuacao(String.valueOf(pontuacao));
    }
    public void acaoClick(java.awt.event.MouseEvent evt){
         if(sequenciaAutomaticaIniciada == true){
        }
        else{
        JButton botaoClicado =(JButton) evt.getSource();
        PintarBotoes.piscarBotoes(botaoClicado, delay, () -> callbackAcaoClick( botaoClicado));
        
    }
    }
    private void callbackAcaoClick(JButton botaoClicado){
        validaSequencia(botaoClicado);

    }
    private void pintarBotoesSequencia(JButton[] sequencia){
        PintarBotoes.piscarBotoes(sequencia, delay, this::callbackPintarBotoesSequencia);
    }
    private void callbackPintarBotoesSequencia(){
        sequenciaAutomatica = false;
        JOptionPane.showMessageDialog(null, "Agora é sua vez!");
    }
    private void validaSequencia(JButton botaoClicado){
        if(botaoClicado.equals(sequenciaBotao[qtAcertos])){
            qtAcertos++;
            sequenciaValida();
        }
        else{
            sequenciaInvalida();
        }

    }
    private void sequenciaValida(){
        if(qtAcertos == qtdSequencia){
           qtAcertos = 0;
           pontuacao += qtdSequencia * multPontuacao; 
           JOptionPane.showMessageDialog(null, "Acertou a sequencia  Pontuacao: " + pontuacao);
           qtdSequencia++;
           for(int i = 0; i < sequenciaBotao.length; i++){
               sequenciaBotao[i] = null;
           }
           iniciarRodada();
        }
    }
    private void sequenciaInvalida(){
        JOptionPane.showMessageDialog(null, "GameOver: Pontuacao final: " +pontuacao);
        rodadaIniciada = false;
        sequenciaAutomatica = false;
        pontuacao = 0;
        botaoIniciar.setEnabled(true);
        telaPrincipal.atualizarPontuacao("0");
        telaPrincipal.atualizarRodada("0");
        for (int i = 0; i < sequenciaBotao.length; i++) {
            sequenciaBotao[i] = null;
        }
    }
    
    private JButton[] gerarSequenciaBotoes(int[] sequenciaNumerica){
        JButton[] sequenciaBotao = new JButton[sequenciaNumerica.length]; 
        for(int i = 0; i<sequenciaNumerica.length; i++){
            sequenciaBotao[i] = listaBotoes[sequenciaNumerica[i]];
        }
        return sequenciaBotao;
    }
    
    private int[] gerarSequenciaNumerica(int qtdSequencias){
        Random aleatorio = new Random();
        int[] sequencia = new int[qtdSequencias];
        for(int i = 0; i<qtdSequencias; i++){
            sequencia[i] = aleatorio.nextInt(listaBotoes.length);
        }
        return sequencia;
    }
    
    
    
    
    
}
