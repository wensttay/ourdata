/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import br.ifpb.simba.ourdata.dao.entity.ColumnIndexDTOPostgresImpl;
import br.ifpb.simba.ourdata.dao.entity.ColumnIndexDao;
import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import br.ifpb.simba.ourdata.reader.TextColor;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pedro Arthur
 */
public class CSVColumnAnalyzer {

    private final ColumnIndexDao columnIndexDao;

    public CSVColumnAnalyzer() {
        columnIndexDao = new ColumnIndexDTOPostgresImpl();
    }

    public void analyze(List<CkanResource> resources) {

        for (CkanResource resource : resources) {

            if (resource.getFormat().equals("CSV")) {
                
                try {

                    List<Set<ColumnIndexDTO>> result = ColumnIndexProcessor.process(resource);

                    for (Set<ColumnIndexDTO> column : result) {

                        for (ColumnIndexDTO columnIndexDTO : column) {

                            this.columnIndexDao.insert(columnIndexDTO);
                        }
                    }
                
                } catch(OutOfMemoryError ex) {
                    System.out.println(TextColor.ANSI_RED + ex.getMessage() + TextColor.ANSI_BLACK);
                }
            }
        }
    }

}
