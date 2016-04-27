/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.ExcelReader;
import br.ifpb.simba.ourdata.reader.XLSReader;
/**
 *
 * @author kieckegard
 */
public class TestXLS
{
    public static void main(String[] args)
    {
        String url = "http://www.capes.gov.br/images/stories/download/avaliacaotrienal/planilhascomparativastrienal2007/Artes_Musica.xls";
        ExcelReader er = new XLSReader();
        er.print(url);
    }
}
