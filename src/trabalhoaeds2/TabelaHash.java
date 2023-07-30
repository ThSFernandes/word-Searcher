package trabalhoaeds2;


import java.util.LinkedList;

public class TabelaHash {

    private static int HASHMOD = 569;
    private static LinkedList<Palavras>[] hashTable = new LinkedList[HASHMOD];
    
    private static StringBuilder stringBuilder = new StringBuilder();
    public TabelaHash() {

    }

    public static void insertHash(Palavras palavra) {
        int valorHash = hashFunction(palavra.getPalavra());

        if (hashTable[valorHash] == null) {
            hashTable[valorHash] = new LinkedList<Palavras>();
        }

        LinkedList<Palavras> balde = hashTable[valorHash];
        boolean encontrado = false;

        for (Palavras p : balde) {
            if (p.getPalavra().equals(palavra.getPalavra())) {
                Ocorrencia ocorrencia = palavra.getOcorrencias().get(0);
                StringBuilder stringBuilder = new StringBuilder(palavra.getPalavra());
                stringBuilder.append(" (");
                stringBuilder.append(ocorrencia.getCaminhoArquivo());
                stringBuilder.append(": ");
                stringBuilder.append(ocorrencia.getNumeroDeOcorrencias());
                stringBuilder.append(")");
                p.addOcorrencia(stringBuilder.toString());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            balde.add(palavra);
        }
    }

     public static Palavras searchPalavra(String palavra) {
        int valorHash = hashFunction(palavra);

        if (hashTable[valorHash] != null) {
            for (Palavras p : hashTable[valorHash]) {
                if (p.getPalavra().equals(palavra)) {
                    return p;
                }
            }
        }

        return null;
    }

    public static int hashFunction(String palavra) {
        int hash = 0;
        int p = 29791; // Um número primo para ajudar na distribuição dos valores hash
        int m = HASHMOD; // Tamanho da tabela hash

        stringBuilder.setLength(0);
        stringBuilder.append(palavra);

        for (int i = 0; i < stringBuilder.length(); i++) {
            hash = (hash * p + stringBuilder.charAt(i)) % m;
        }

        return hash;
    }
    


    public static void printHash() {
        int c = 0;
        for (LinkedList<Palavras> list : hashTable) {
            System.out.print("[" + c + "] ");
            c++;
            if (list == null) {
                System.out.println();
                continue;
            }
            for (Palavras palavra : list) {
                System.out.print(palavra.getPalavra() + "->");
            }
            System.out.println();
        }
    }
    
   
}