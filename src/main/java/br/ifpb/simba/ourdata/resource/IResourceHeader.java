/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.resource;

/**
 *
 * @author kieckegard
 */
public interface IResourceHeader<T>
{
    public T getHeader(String url);
}
