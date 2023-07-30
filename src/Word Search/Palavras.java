package trabalhoaeds2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

public class Palavras {

    private String palavraChave;
    private List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

    public Palavras(String palavra) {
        this.palavraChave = palavra;
    }

    public String getPalavra() {
        return palavraChave;
    }

    public void setPalavra(String palavra) {
        this.palavraChave = palavra;
    }

    public List<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public void addOcorrencia(String arquivo) {
        for (Ocorrencia ocorrenciaTemp : this.ocorrencias) {
            if (ocorrenciaTemp.getCaminhoArquivo().equals(arquivo)) {
                ocorrenciaTemp.OcorreuOcorrencia();
                return;
            }
        }
        ocorrencias.add(new Ocorrencia(arquivo));
    }
    
    public void exibirOcorrencias() {
        Collections.sort(ocorrencias, Comparator.comparingInt(Ocorrencia::getNumeroDeOcorrencias).reversed());
        StringBuilder message = new StringBuilder();
        message.append("A palavra buscada: ").append(palavraChave).append("\n\n");
        for (Ocorrencia ocorrencia : ocorrencias) {
            message.append("- Arquivo: ")
                    .append(ocorrencia.getCaminhoArquivo())
                    .append(", Ocorrencias: ")
                    .append(ocorrencia.getNumeroDeOcorrencias())
                    .append("\n");
        }
        if (message.length() > 0) {
            JOptionPane.showMessageDialog(null, message.toString(), "Ocorrências", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma ocorrência encontrada.", "Ocorrências", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

