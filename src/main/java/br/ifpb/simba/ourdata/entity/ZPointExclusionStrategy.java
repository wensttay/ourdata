/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 *
 * @author kieckegard
 */
public class ZPointExclusionStrategy implements ExclusionStrategy
{

    @Override
    public boolean shouldSkipField(FieldAttributes arg0)
    {
        return arg0.getName().equals("z");
    }

    @Override
    public boolean shouldSkipClass(Class<?> arg0)
    {
        return false;
    }
    
}
