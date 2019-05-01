package com.example.hugov.appcurso.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Curso {
    private Integer codigo;
    private String nome;
    private String professor;
    private String categoria;
    private String dado;
    private Bitmap imagem;

    public void setDado(String dado) {
        this.dado = dado;
        try{
            byte[] byteCode = Base64.decode(dado, Base64.DEFAULT);
           // this.imagem = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);

            int alt = 200;
            int larg = 200;

            Bitmap foto = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);
            this.imagem = Bitmap.createScaledBitmap(foto, alt, larg, true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public String getDado() {
        return dado;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getProfessor() {
        return professor;
    }

    public String getCategoria() {
        return categoria;
    }
}
