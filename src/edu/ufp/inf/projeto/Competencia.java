package edu.ufp.inf.projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Competencia implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    private String nome;

    public Competencia(String nome) {
        this.id= new UniqueID().getId();
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
     *gravamos para um ficheiro txt de competencias todas as competencias
     * @throws IOException
     */
    public void gravarCompetencia() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/competencias.txt", true));
        writer.append("Competencia: "+this.getNome()+"; ");
        writer.newLine();
        writer.close();
    }

    /**
     *carregamos de um ficheito txt de novas competencias e guardamos num arrayList de competencias
     * @return return arrayList de competencias
     */
    public static ArrayList<Competencia> carregarCompetencias(){
        int flag=0;
        ArrayList<Competencia> competencias= new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/novasCompetencias.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info= line.split(",");
                for(int i=0; i<info.length; i++){
                    for (Competencia co: competencias) {
                        if(co.getNome().compareTo(info[i])==0){
                            flag=1;
                        }
                    }
                    if(flag==0){
                        competencias.add(new Competencia(info[i]));
                    }
                }
            }
            reader.close();

        } catch (Exception e) {
            System.err.format("Exception occurred trying to read 'novasCompetencias.txt'.");
            e.printStackTrace();
            return null;
        }
        return competencias;
    }
}
