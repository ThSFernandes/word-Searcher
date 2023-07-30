package trabalhoaeds2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {

    private static TabelaHash tabelaHash = new TabelaHash();
    private static List<File> arquivosTxt = new ArrayList<>();

    // Usada para seleção do diretório raiz
    public static File selecionaDiretorioRaiz() {
        //Exibe apenas diretórios
        JFileChooser janelaSelecao = new JFileChooser(".");
        janelaSelecao.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }

            public String getDescription() {
                return "Diretório";
            }
        });

        //Configuração do modo de exibição
        janelaSelecao.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int acao = janelaSelecao.showOpenDialog(null);

        if (acao == JFileChooser.APPROVE_OPTION) {
            return janelaSelecao.getSelectedFile();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

        List<String> stopWords = Arrays.asList("a", "o", "em", "para", "com", "de", "do", "da", "dos", "das", "no",
                "na", "nos", "nas", "por", "que", "se", "é", "são", "como", "um", "uma", "uns", "umas", "não",
                "mais", "menos", "mas", "porém", "entretanto", "todavia", "contudo", "assim", "então", "logo",
                "portanto", "porque", "porquê", "quando", "onde", "quem", "qual", "quais", "porquanto", "apesar",
                "além", "ainda", "também", "inclusive", "exclusivamente", "somente", "apenas", "a", "an", "and", "are",
                "as", "at", "be", "by", "for", "from", "has", "he", "in", "is", "it", "its", "of", "on", "that",
                "the", "to", "was", "were", "will", "with");

        File pastaInicial = selecionaDiretorioRaiz();

        if (pastaInicial == null) {
            System.out.println("Voce deve selecionar uma pasta para o processamento.");
        } else {
            ArrayDeque<File> pastas = new ArrayDeque<>();
            pastas.push(pastaInicial);

            while (!pastas.isEmpty()) {
                File diretorioAtual = pastas.pop();
                File arquivosDir[] = diretorioAtual.listFiles();

                if (arquivosDir != null) {
                    for (File arquivo : arquivosDir) {
                        if (arquivo.isDirectory()) {
                            pastas.push(arquivo);
                        } else {
                            if (arquivo.getAbsolutePath().endsWith(".txt")) {
                                arquivosTxt.add(arquivo);
                                lerArquivo(arquivo, stopWords);
                            }
                        }
                    }
                }
            }

            String palavraSemTratamento = JOptionPane.showInputDialog("Digite a palavra que seja pesquisar !");

            String palavraBuscada = palavraSemTratamento.toLowerCase();

            palavraBuscada = removerAcentos(palavraBuscada);

            Palavras palavraEncontrada = tabelaHash.searchPalavra(palavraBuscada);
            if (palavraEncontrada != null) {

                palavraEncontrada.exibirOcorrencias();
                imprimirTabelhash();
                tabelaHash.printHash();

            }  else {
                JOptionPane.showMessageDialog(null, "Palavra nao encontrada");
                imprimirTabelhash();
                tabelaHash.printHash();
   

            }

        }
    }




    public static void lerArquivo(File arquivo, List<String> stopWords) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] palavras = linha.split("[\\s.,:'0-9\"\\(\\)-]+");
                for (String palavra : palavras) {
                 
                    palavra = removerAcentos(palavra);
                    if (!stopWords.contains(palavra)) {
                        // Busca pela palavra na tabela hash
                        Palavras palavraExistente = tabelaHash.searchPalavra(palavra.toLowerCase());
                        if (palavraExistente == null) { 
                            //Cria a palavra
                            Palavras novaPalavra = new Palavras(palavra.toLowerCase());
                            novaPalavra.addOcorrencia(arquivo.getAbsolutePath()); 
                            tabelaHash.insertHash(novaPalavra);
                        } else {
                            //Verificação se a palavra ocorreu
                            boolean arquivoExistente = false;
                            for (Ocorrencia ocorrencia : palavraExistente.getOcorrencias()) {
                                if (ocorrencia.getCaminhoArquivo().equals(arquivo.getAbsolutePath())) {
                                    ocorrencia.OcorreuOcorrencia();
                                    arquivoExistente = true;
                                    break;
                                }
                            }
                            if (!arquivoExistente) {
                                palavraExistente.addOcorrencia(arquivo.getAbsolutePath());
                            }
                        }
                    }
                }
            }

           reader.close(); 
        } catch (IOException e) {
            System.out.println("Ocorreu um erro !");
        }
    }
    
    
   
    private static String removerAcentos(String palavra) {
    palavra = Normalizer.normalize(palavra, Normalizer.Form.NFD);
    palavra = palavra.replaceAll("[^\\p{ASCII}]", "");
    return palavra;
}
    
    public static void imprimirTabelhash(){
        String verificaSeQuerImprimir= JOptionPane.showInputDialog("Gostaria de exibir a tabela hash: 'Sim ou Não '?");
                verificaSeQuerImprimir.toLowerCase();
                if(verificaSeQuerImprimir.equals("sim")){
                    tabelaHash.printHash();

                }
                System.out.println("Programa finalizado !.");
    }
}
