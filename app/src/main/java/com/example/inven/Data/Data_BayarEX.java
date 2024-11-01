package com.example.inven.Data;

public class Data_BayarEX {

    private String id,nama_beli,qty,harga,keterangan,pembayaran;

    public Data_BayarEX() {
    }

    public Data_BayarEX(String id, String nama, String jumlah, String harga) {
        this.id = id;
        this.nama_beli = nama_beli;
        this.qty = qty;
        this.harga = harga;
        this.keterangan = keterangan;
        this.pembayaran = pembayaran;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama_beli;
    }

    public void setNama(String nama_beli) {
        this.nama_beli = nama_beli;
    }

    public String getJumlah() {
        return qty;
    }

    public void setJumlah(String nama_saldo) {
        this.qty = nama_saldo;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getpembayaran() {
        return pembayaran;
    }

    public void setpembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }
}
