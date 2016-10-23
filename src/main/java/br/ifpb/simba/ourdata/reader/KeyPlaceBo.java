/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import br.ifpb.simba.ourdata.dao.entity.PlaceBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class KeyPlaceBo {

    public static final int NUM_ROWS_CHECK_DEFAULT = 10;
    public static final int WKT_ID = -99;

    private int numRowsCheck;
    private final PlaceBdDao placeBdDao;
    private List<Place> placesGazetteer;

    public KeyPlaceBo(int numRowsCheck) {
        this.numRowsCheck = numRowsCheck;
        placeBdDao = new PlaceBdDao();
        this.placesGazetteer = placeBdDao.getAll();
    }

    public int getNumRowsCheck() {
        return this.numRowsCheck;
    }

    public void setNumRowsCheck(int numRowsCheck) {
        this.numRowsCheck = numRowsCheck;
    }

    public List<Place> buscarPorTitulos(String value) {
        String upperValue, upperName;
        upperValue = value.toUpperCase();
        List<Place> result = new ArrayList<>();
        for (Place p : placesGazetteer) {
            upperName = p.getNome().toUpperCase();
            if (upperValue.equals(upperName) || upperValue.equals(p.getSigla())) {
                result.add(p);
            }
        }
        return result;
    }

    private Place getMinorPlace(List<Place> places) {
        if (places.isEmpty()) {
            return null;
        }
        //first place
        Place minor = places.get(0);
        Geometry minor_way = minor.getWay();

        for (int i = 0; i < places.size(); i++) {

            Place current = places.get(i);
            Geometry current_way = current.getWay();

            if (current_way.getArea() < minor_way.getArea()) {
                minor = current;
                minor_way = current_way;
            }
        }

        return minor;
    }

    private KeyPlace getMinorKeyPlace(List<KeyPlace> keyPlaces) {
        if (keyPlaces.isEmpty()) {
            return null;
        }
        //first place
        KeyPlace minor = keyPlaces.get(0);
        Geometry minor_way = minor.getPlace().getWay();

        for (int i = 0; i < keyPlaces.size(); i++) {

            KeyPlace current = keyPlaces.get(i);
            Geometry current_way = current.getPlace().getWay();

            if (current_way.getArea() < minor_way.getArea()) {
                minor = current;
                minor_way = current_way;
            }
        }

        return minor;
    }

    public List<KeyPlace> getKeyPlaces(CkanResource resource, CkanDataset dataset) throws IOException {

        float percent = 0;

        List<KeyPlace> resultKeyPlaces = new ArrayList<>();
        String resourceId = resource.getId();
        String resourceUrl = resource.getUrl();

        CSVReaderOD cSVReaderOD = new CSVReaderOD();

        //get a List which contains all csv content
        List<String[]> csvRows = cSVReaderOD.build(resourceUrl);

        if (csvRows == null) {
            csvRows = new ArrayList<>();
        }

        int csvRowsSize = csvRows.size();
        System.out.println("File Row Size: " + csvRowsSize + " |||| ");

        //Iterating all csvRows
        for (int rowIndex = 1; rowIndex < csvRowsSize; rowIndex++) {

            //getting current row
            String[] row = csvRows.get(rowIndex);

            //creating current row keyplace list
            List<KeyPlace> rowKeyPlaces = new ArrayList<>();

            //Iterating each csvRow's columns
            for (int colIndex = 0; colIndex < row.length; colIndex++) {

//              Dentro desse comando se faz o filtro para a lista de colunas que apresentaram
//              resutados encontrados na pesquisa no Gazetteer
                if (resultKeyPlaces.size() > numRowsCheck) {
                    boolean validColumNumber = false;
                    while (colIndex < row.length) {
                        for (int i = 0; i < numRowsCheck; i++) {
                            if (resultKeyPlaces.get(i).getColumNumber() == colIndex) {
                                validColumNumber = true;
                                break;
                            }
                        }

                        if (validColumNumber) {
                            break;
                        } else {
                            ++colIndex;
                        }
                    }
                }

                if (colIndex >= row.length) {
                    break;
                }

                String colValue = row[colIndex].replace("\n", " ");
                colValue = colValue.trim();
//                String colValue = row[colIndex];

                Place place = null;

                // MELHORAR ISSO, TA FEIO
                String columValueGeometry = "";

                if (colValue != null && !colValue.equals("")) {
                    //Search by GEOMETRY String format
                    if (place == null) {
                        place = searchByGemotryFormat(colValue);

                        if (place != null) {
                            columValueGeometry = new WKTWriter().write(place.getWay());
                        }
                    }

                    //Search by colum Name
                    if (place == null) {
                        //Creating place list
                        List<Place> places = new ArrayList<>();
                        places.addAll(buscarPorTitulos(colValue));
                        place = getMinorPlace(places);
                    }
                }

                if (place != null) {

                    KeyPlace keyPlace = preencherKeyplace(colIndex, csvRowsSize,
                            colValue, resourceId, place);
                    
                    if (!columValueGeometry.isEmpty()) {
                        keyPlace.setColumValue(columValueGeometry);
                    }
                    
                    rowKeyPlaces.add(keyPlace);
   
                }

            } //ends col iteration

            if (!rowKeyPlaces.isEmpty()) {
                resultKeyPlaces.add(getMinorKeyPlace(rowKeyPlaces));
            }

            /*
             * If there's no places found in the csv file and we already verify
             * the whole thing until row 10,
             * I guess the csv does not contains any place xD
             */
            if (rowIndex >= numRowsCheck && resultKeyPlaces.isEmpty()) {
                KeyPlace placeByDescriptions = getPlaceByDescriptions(resource, csvRowsSize, dataset);
                if (placeByDescriptions != null) {
                    resultKeyPlaces.add(placeByDescriptions);
                }
                System.out.println(TextColor.ANSI_RED.getCode() + " " + "ERRO: ATINGIU O NUMERO MAX DE " + numRowsCheck + " ROWS VERIFICADAS SEM ENCONTRAR NENHUMA KEYPLACE !!");
                break;
            }

            //percent feedback
            percent = percentFeedback(rowKeyPlaces, rowIndex, csvRowsSize, percent);

        } //ends rows iteration

        if (!resultKeyPlaces.isEmpty()) {
            System.out.println("100 %");
        }

        cSVReaderOD.closeAll();
        return resultKeyPlaces;
    }

    private float percentFeedback(List<KeyPlace> rowKeyPlaces, int rowIndex, int csvRowsSize, Float percent) {
        if (!rowKeyPlaces.isEmpty()) {
            NumberFormat formatter = new DecimalFormat("#0.00");
            float percentRead = (((float) rowIndex * 100) / (float) csvRowsSize);

            if (percent + 10 < percentRead) {

                System.out.println(formatter.format(percentRead) + " %");
                return percentRead;
            }
        }

        return percent;
    }

    private KeyPlace preencherKeyplace(int colIndex, int csvRowsSize,
            String colValue, String resourceId, Place place) {

        KeyPlace keyPlace = new KeyPlace();
        keyPlace.setColumNumber(colIndex);
        keyPlace.setColumValue(colValue);
        keyPlace.setIdResource(resourceId);
        keyPlace.setMetadataCreated(new Timestamp(System.currentTimeMillis()));
        keyPlace.setPlace(place);
        keyPlace.setRepeatNumber(1);
        keyPlace.setRowsNumber(csvRowsSize);
        return keyPlace;
    }

    private Place searchByGemotryFormat(String colValue) {

        try {
            Geometry geometry = new WKTReader().read(colValue);
            Envelope envelope = geometry.getEnvelopeInternal();

            Place place = new Place(new GeometryFactory().toGeometry(envelope));

            place.setMaxX(envelope.getMaxX());
            place.setMaxY(envelope.getMaxY());
            place.setMinX(envelope.getMinX());
            place.setMinY(envelope.getMinY());
            place.setId(WKT_ID);

            return place;
        } catch (ParseException | NullPointerException ex) {
//            System.out.println("Não foi possível converter " + colValue + " para Well Known Text.");
            return null;
        }
    }

    private KeyPlace getPlaceByDescriptions(CkanResource resource, int rowsSize, CkanDataset dataset) {
        
        String resourceName = resource.getName();
        String resourceDescription = resource.getDescription();
        String datasetName = dataset.getName();
        String datasetNotes = dataset.getNotes();
        

        for (Place place : placesGazetteer) {

            if (!place.getNome().equals("")) {
                if (resourceName.contains(place.getNome())
                        || resourceDescription.contains(place.getNome())
                        || datasetName.contains(place.getNome()) 
                        || datasetNotes.contains(place.getNome())) {
                    
                    return preencherKeyplace(WKT_ID, rowsSize, place.getNome(), resource.getId(), place);
                }
            }
        }

        return null;
    }
}
