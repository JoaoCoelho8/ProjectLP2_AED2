package edu.ufp.inf.projeto;

import java.io.Serializable;

public class EncontroExistenteException extends Exception implements Serializable {

    public EncontroExistenteException(String message) {
        super(message);
    }

}