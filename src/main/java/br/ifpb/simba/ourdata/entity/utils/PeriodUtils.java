package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.Period;
import br.ifpb.simba.ourdata.entity.PeriodTime;
import br.ifpb.simba.ourdata.heideltime.TimeMLReader;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class PeriodUtils {

    private TimeMLReader timeMLReader;

    public TimeMLReader getTimeMLReader() {
        return timeMLReader;
    }

    public void setTimeMLReader(TimeMLReader timeMLReader) {
        this.timeMLReader = timeMLReader;
    }

    public PeriodUtils() {
        timeMLReader = new TimeMLReader();
    }

    public PeriodUtils(String properties_path) {
        timeMLReader = new TimeMLReader(properties_path);
    }

    public List<Date> readDates(String text, Date date) throws JDOMException, IOException, DocumentCreationTimeMissingException {
        List<Date> list = new ArrayList<>();

        List listtimevalues = timeMLReader.ListFromXml(text, date);
        Iterator i = listtimevalues.iterator();

        SimpleDateFormat sdf = null;
        int dateVariation = 0;

        while (i.hasNext()) {

            Element timevalue = (Element) i.next();
            String time = timevalue.getAttributeValue("value");
            boolean justAYear = false;

            if (time != null && time.length() <= 15) {

                if (time.length() == 10) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                } else if (time.length() == 4) {
                    sdf = new SimpleDateFormat("yyyy");
                    justAYear = true;
                }

                if (time.contains("LESS")) {
                    dateVariation = Integer.parseInt(time.substring(16)) * (-1);
                    time = time.substring(0, 10);
                }

                if (sdf != null) {
                    try {
                        Calendar startCal = sdf.getCalendar();
                        startCal.setTime(sdf.parse(time));
                        startCal.add(Calendar.DATE, dateVariation);

                        Date d = startCal.getTime();
                        list.add(d);

                        Calendar finalCal = sdf.getCalendar();
                        finalCal.setTime(sdf.parse(time));

                        if (justAYear) {
                            finalCal.add(Calendar.DATE, dateVariation);
                            finalCal.add(Calendar.YEAR, 1);
                            finalCal.add(Calendar.SECOND, -1);
                        } else {
                            finalCal.add(Calendar.DATE, 1);
                            finalCal.add(Calendar.SECOND, -1);
                        }

                        d = finalCal.getTime();
                        list.add(d);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        return list;
    }

    public Period findPeriod(String text, int col, Date date) throws JDOMException, IOException, DocumentCreationTimeMissingException {
        List<Date> readStringDates = readDates(text, date);

        if (readStringDates.isEmpty()) {
            return null;
        }

        Iterator i = readStringDates.iterator();

        Date startDate = (Date) i.next();
        Date endDate = startDate;

        if (startDate != null) {
            while (i.hasNext()) {
                Date nDate = (Date) i.next();
                if (nDate.before(startDate)) {
                    startDate = nDate;
                }
                if (nDate.after(endDate)) {
                    endDate = nDate;
                }
            }
        }

        return new Period(new PeriodTime(startDate, col), new PeriodTime(endDate, col));
    }

    public Period joinPeriods(List<Period> periods) {

        if (periods.isEmpty()) {
            return null;
        }

        Period period = periods.get(0);

        for (Period p : periods) {
            if (p.getEndDate().getDate().after(period.getEndDate().getDate())) {
                period.setEndDate(p.getEndDate());
            }
            if (p.getStartDate().getDate().before(period.getStartDate().getDate())) {
                period.setStartDate(p.getStartDate());
            }
        }

        return period;
    }
}
