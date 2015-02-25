package com.example.android.calcolotasi;

import java.text.NumberFormat;

/**
 * Created by innocenzo on 09/02/15.
 */
public class Provincia {
    private long id;
    private String nome;
    private String descrizione;
    private double price;
    private String image;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nome + "\n(" +nf.format(price) + ")";
    }

}


