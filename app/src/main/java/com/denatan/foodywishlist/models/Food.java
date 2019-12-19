package com.denatan.foodywishlist.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Food implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nama;
    private String lokasi;
    private String notelp;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getNotelp() { return notelp;
    }
    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }
}