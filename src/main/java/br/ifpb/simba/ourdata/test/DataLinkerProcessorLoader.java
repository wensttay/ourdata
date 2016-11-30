/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.entity.ColumnIndexDTOPostgresImpl;
import br.ifpb.simba.ourdata.dao.entity.ColumnIndexDao;
import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import br.ifpb.simba.ourdata.headerAnality.DataLinkerProcessor;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class DataLinkerProcessorLoader {
    
    public static void main(String[] args) {
        
        ColumnIndexDao columnIndexDao = new ColumnIndexDTOPostgresImpl();
        
        List<ColumnIndexDTO> list = columnIndexDao.list(0L, 360000L);
        
        DataLinkerProcessor processor = new DataLinkerProcessor(list);
        
        processor.startProcess();
    }
}
