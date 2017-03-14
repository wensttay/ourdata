package br.ifpb.simba.ourdata.headerAnality;

import br.ifpb.simba.ourdata.reader.CSVReaderOD;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Pedro Arthur
 */
public class ResourceHeaderAnalityCsv implements ResourceHeaderAnality<ResourceHeader> {

    /**
     * Method of taking an ResourceHeader over a URL passed by parameter
     *
     * @param url Url of the resource you want to analyze
     *
     * @return ResourceHeader, class that contains information about the
     * resource.
     */
    @Override
    public ResourceHeader getHeader(String url) {

        List<String[]> rows = new CSVReaderOD().build(url);
        List<ColumnHeader> columns = new ArrayList<>();

        String[] header = rows.get(0);
        int qtd_columns = header.length;
        int qtd_rows = rows.size() - 1;

        Set<String> set;
        for (int j = 0; j < qtd_columns; j++) {
            set = new TreeSet<>();
            for (int i = 1; i < qtd_rows; i++) {
                String cell = rows.get(i)[j];
                set.add(cell);
            }
            ColumnHeader column = new ColumnHeader(set.size(), header[j]);
            columns.add(column);
        }

        return new ResourceHeader(columns, qtd_rows);
    }
}
