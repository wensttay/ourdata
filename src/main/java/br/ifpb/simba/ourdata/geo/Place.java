/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.geo;

import com.vividsolutions.jts.geom.Geometry;
import org.postgis.PGgeometry;

/**
 *
 * @author wensttay
 */
public class Place {
    private int id;
    private String nome;
    private String sigla;
    private String tipo;
    private Geometry way;

    public Place(Geometry way) {
        this.way = way;
    }

    public Place() {}

    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", tipo=" + tipo + ", way=" + way + '}';
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Geometry getWay() {
        return way;
    }

    public void setWay(Geometry way) {
        this.way = way;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
