/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public interface ColumnIndexDao {
    
    void insert(ColumnIndexDTO columnIndexDTO);
    List<ColumnIndexDTO> list(Long start, Long quantity);
    List<ResourceColumnIndex> getResourcesColumnIndex();
}
