/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.ExcelReader;
import br.ifpb.simba.ourdata.reader.XLSReader;
import br.ifpb.simba.ourdata.reader.XLSXReader;
/**
 *
 * @author kieckegard
 */
public class TestXLS
{
    public static void main(String[] args)
    {
        String url = "http://repositorio.dados.gov.br/governo-politica/administracao-publica/pac/PAC_2015_06.xlsx";
        ExcelReader er = new XLSXReader();
        er.print(url);
    }
}
