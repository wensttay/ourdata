/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.parser;

import br.ifpb.simba.ourdata.reader.CSVReader;
import br.ifpb.simba.ourdata.reader.CSVReaderException;
import br.ifpb.simba.ourdata.validation.ColumnCellValidation;
import com.google.common.base.Strings;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class CkanResourceDocumentParser implements Parser<CkanResource, String> {

    private final CSVReader reader;

    public CkanResourceDocumentParser() {
        this.reader = new CSVReader();
    }

    @Override
    public String parse(CkanResource obj) throws CSVReaderException {

        StringBuilder builder = new StringBuilder();
        builder.append(obj.getDescription()).append(" ");

        List<String[]> csv = reader.read(obj.getUrl());

        for (String[] row : csv) {
            for (String cell : row) {
                if (isValidCell(cell)) {
                    builder.append(cell)
                            .append(" ");
                }
            }
        }

        return builder.toString();
    }

    private boolean isValidCell(String cell) {
        return !(Strings.isNullOrEmpty(cell)
                || ColumnCellValidation.hasJustSpecialCharacters(cell)
                || ColumnCellValidation.isInteger(cell)
                || ColumnCellValidation.isDoubleOrFloat(cell)
                || ColumnCellValidation.isWKT(cell)
                || ColumnCellValidation.isADate(cell));
    }

}
