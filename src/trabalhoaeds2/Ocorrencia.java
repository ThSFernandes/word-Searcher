
package trabalhoaeds2;

// Preciso passar o endereço do caminho do arquivo e o número de ocorrências 
public class Ocorrencia {
   
    private String caminhoArquivo;      // Caminho do arquivo
    private int numeroDeOcorrencias;   //  Número de ocorrências 

    public Ocorrencia(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.numeroDeOcorrencias = 1;
    }

    
    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public int getNumeroDeOcorrencias() {
        return numeroDeOcorrencias;
    }

    public void setNumeroDeOcorrencias(int numeroDeOcorrencias) {
        this.numeroDeOcorrencias = numeroDeOcorrencias;
    }
    
    // Método que incrementa o número de ocorrências 
    public void OcorreuOcorrencia (){
     this.numeroDeOcorrencias ++;
}
    
    
}
