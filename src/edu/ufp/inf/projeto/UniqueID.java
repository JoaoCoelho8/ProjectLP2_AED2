package edu.ufp.inf.projeto;

import java.io.Serializable;

public class UniqueID implements Serializable {

    private Integer id;
    static Integer counter = 0;

    /**
     * atribuir um id unico para cada id e incrementa em 1 cada id novo que aparece
     */
    public UniqueID() {
        this.id = counter;
        counter+=1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
