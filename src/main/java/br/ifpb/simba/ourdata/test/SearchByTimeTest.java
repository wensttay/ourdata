/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.entity.Period;
import br.ifpb.simba.ourdata.entity.PeriodTime;
import br.ifpb.simba.ourdata.entity.ResourceTimeSearch;
import br.ifpb.simba.ourdata.services.QueryResourceBo;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class SearchByTimeTest {

    public static void main(String[] args) {
        QueryResourceBo queryResourceBo = new QueryResourceBo();

        Calendar start = new GregorianCalendar(2014, 2, 11);
        Calendar end = new GregorianCalendar(2014, 2, 20);
        

        Period period = new Period(
                new PeriodTime(start.getTime()),
                new PeriodTime(end.getTime()));

        List<ResourceTimeSearch> listResourcesIntersectedBy = queryResourceBo.listResourcesIntersectedBy(period);

        for (ResourceTimeSearch resourceTimeSearch : listResourcesIntersectedBy) {
            System.out.println(resourceTimeSearch.toString());
        }
    }
}
