package cl.usach.ingesoft.agendator.entity.base;

import static cl.usach.ingesoft.agendator.util.DateUtils.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Wrapper for different formattings over date ranges.
 */
public class DateHelper implements Serializable {

    private Date startDate;
    private Date endDate;

    public DateHelper(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Human readable formatted start dates
    public String getStartDateTime() {
        return formatDateTime(startDate);
    }
    public String getStartDate() {
        return formatDate(startDate);
    }
    public String getStartTime() {
        return formatTime(startDate);
    }

    // ISO (reversed) formatted start dates
    public String getIsoStartDateTime() {
        return formatIsoDateTime(startDate);
    }
    public String getIsoStartDate() {
        return formatIsoDate(startDate);
    }
    public String getIsoStartTime() {
        return formatIsoTime(startDate);
    }

    // ------------------------------------------

    // Human readable formatted end dates
    public String getEndDateTime() {
        return formatDateTime(endDate);
    }
    public String getEndDate() {
        return formatDate(endDate);
    }
    public String getEndTime() {
        return formatTime(endDate);
    }

    // ISO (reversed) formatted end dates
    public String getIsoEndDateTime() {
        return formatIsoDateTime(endDate);
    }
    public String getIsoEndDate() {
        return formatIsoDate(endDate);
    }
    public String getIsoEndTime() {
        return formatIsoTime(endDate);
    }
}
