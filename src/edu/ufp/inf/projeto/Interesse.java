package edu.ufp.inf.projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Interesse implements Serializable{

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private String nome;

    public Interesse(String nome) {
        this.id = new UniqueID().getId();
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *gravamos para um txt de interesses todos os interesses
     * @throws IOException
     */
    public void gravarInteresse() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/interesses.txt", true));
        writer.append("Interesse: "+this.getNome()+"; ");
        writer.newLine();
        writer.close();
    }

    /**
     * carregamos de um ficheiro txt de novos interesses e guardamos num arrayList de interesses
     * @return reu«turn um arrayList de interesses
     */
    public static ArrayList<Interesse> carregarInteresses(){
        int flag=0;
        ArrayList<Interesse> interesses= new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/novosInteresses.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info= line.split(",");
                for(int i=0; i<info.length; i++){
                    for (Interesse in: interesses) {
                        if(in.getNome().compareTo(info[i])==0){
                            flag=1;
                        }
                    }
                    if(flag==0){
                        interesses.add(new Interesse(info[i]));
                    }

                }
            }
            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read 'novasCompetencias.txt'.");
            e.printStackTrace();
            return null;
        }
        return interesses;
    }
}
