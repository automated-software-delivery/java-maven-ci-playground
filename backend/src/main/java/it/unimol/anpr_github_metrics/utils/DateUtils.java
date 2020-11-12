package it.unimol.anpr_github_metrics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Simone Scalabrino.
 */
public class DateUtils {
    public static final SimpleDateFormat GITHUB_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static Date getOptionalDate(String date) {
        if (date == null)
            return null;

        try {
            return DateUtils.GITHUB_DATE.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date " + date + ". " + e.getMessage());
        }
    }

    public static Date getMandatoryDate(String date) {
        assert date != null;

        try {
            return DateUtils.GITHUB_DATE.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date " + date + ". " + e.getMessage());
        }
    }
}
