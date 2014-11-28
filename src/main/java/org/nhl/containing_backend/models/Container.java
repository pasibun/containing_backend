package org.nhl.containing_backend.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Data model for a container.
 */
public class Container {

    private int number;
    private int arrivalDay;
    private int arrivalMonth;
    private int arrivalYear;
    private String arrivalSpanStart;
    private String arrivalSpanEnd;
    private String arrivalTransportType; // Method of transport through which the container was delivered.
    private String arrivalCompany;
    private int[] spawnPosition = new int[3];
    private String owner;
    private int departureDay;
    private int departureMonth;
    private int departureYear;
    private String departureSpanStart;
    private String departureSpanEnd;
    private String departureTransportType; // Method of transport through which the container must be dispatched.
    private String departureCompany;
    private String contentsName;
    private String contentsType;
    private String contentsDanger;
    private String iso;
    private float emptyWeight;
    private float contentsWeight;
    private float length;
    private float width;
    private float height;

    public Container() {
    }

    public Container(int number, int arrivalDay, int arrivalMonth, int arrivalYear, String arrivalSpanStart,
                     String arrivalSpanEnd, String arrivalTransportType, String arrivalCompany, String owner,
                     int departureDay, int departureMonth, int departureYear, String departureSpanStart,
                     String departureSpanEnd, String departureTransportType, String departureCompany, String contentsName,
                     String contentsType, String contentsDanger, String iso, float emptyWeight, float contentsWeight,
                     float length, float width, float height) {
        this.number = number;
        this.arrivalDay = arrivalDay;
        this.arrivalMonth = arrivalMonth;
        this.arrivalYear = arrivalYear;
        this.arrivalSpanStart = arrivalSpanStart;
        this.arrivalSpanEnd = arrivalSpanEnd;
        this.arrivalTransportType = arrivalTransportType;
        this.arrivalCompany = arrivalCompany;
        this.owner = owner;
        this.departureDay = departureDay;
        this.departureMonth = departureMonth;
        this.departureYear = departureYear;
        this.departureSpanStart = departureSpanStart;
        this.departureSpanEnd = departureSpanEnd;
        this.departureTransportType = departureTransportType;
        this.departureCompany = departureCompany;
        this.contentsName = contentsName;
        this.contentsType = contentsType;
        this.contentsDanger = contentsDanger;
        this.iso = iso;
        this.emptyWeight = emptyWeight;
        this.contentsWeight = contentsWeight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    /**
     * Calculate the metric equivalent of the feet-and-inches input.
     *
     * @param value String value in feet and inches
     * @return Corresponding metric value in metres
     */
    public static float calculateLength(String value) {
        String[] parts = value.split("'");
        String feetStr = parts[0];
        String inchesStr = "0";
        try {
            inchesStr = parts[1].replace("\"", "");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        float metres = 0;
        metres += Integer.parseInt(feetStr) * 0.3048;
        metres += Integer.parseInt(inchesStr) * 0.0254;
        return metres;
    }

    private static Date generateDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, 2000 + year);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public Date getArrivalDate() {
        return generateDate(arrivalDay, arrivalMonth, arrivalYear);
    }

    public Date getDepartureDate() {
        return generateDate(departureDay, departureMonth, departureYear);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getArrivalDay() {
        return arrivalDay;
    }

    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    public int getArrivalMonth() {
        return arrivalMonth;
    }

    public void setArrivalMonth(int arrivalMonth) {
        this.arrivalMonth = arrivalMonth;
    }

    public int getArrivalYear() {
        return arrivalYear;
    }

    public void setArrivalYear(int arrivalYear) {
        this.arrivalYear = arrivalYear;
    }

    public String getArrivalSpanStart() {
        return arrivalSpanStart;
    }

    public void setArrivalSpanStart(String arrivalSpanStart) {
        this.arrivalSpanStart = arrivalSpanStart;
    }

    public String getArrivalSpanEnd() {
        return arrivalSpanEnd;
    }

    public void setArrivalSpanEnd(String arrivalSpanEnd) {
        this.arrivalSpanEnd = arrivalSpanEnd;
    }

    public String getArrivalTransportType() {
        return arrivalTransportType;
    }

    public void setArrivalTransportType(String arrivalTransportType) {
        this.arrivalTransportType = arrivalTransportType;
    }

    public String getArrivalCompany() {
        return arrivalCompany;
    }

    public void setArrivalCompany(String arrivalCompany) {
        this.arrivalCompany = arrivalCompany;
    }

    public int[] getSpawnPosition() {
        return spawnPosition;
    }

    public void setSpawnPosition(int[] spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public int getSpawnX() {
        return spawnPosition[0];
    }

    public void setSpawnX(int value) {
        this.spawnPosition[0] = value;
    }

    public int getSpawnY() {
        return spawnPosition[1];
    }

    public void setSpawnY(int value) {
        this.spawnPosition[1] = value;
    }

    public int getSpawnZ() {
        return spawnPosition[2];
    }

    public void setSpawnZ(int value) {
        this.spawnPosition[2] = value;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(int departureDay) {
        this.departureDay = departureDay;
    }

    public int getDepartureMonth() {
        return departureMonth;
    }

    public void setDepartureMonth(int departureMonth) {
        this.departureMonth = departureMonth;
    }

    public int getDepartureYear() {
        return departureYear;
    }

    public void setDepartureYear(int departureYear) {
        this.departureYear = departureYear;
    }

    public String getDepartureSpanStart() {
        return departureSpanStart;
    }

    public void setDepartureSpanStart(String departureSpanStart) {
        this.departureSpanStart = departureSpanStart;
    }

    public String getDepartureSpanEnd() {
        return departureSpanEnd;
    }

    public void setDepartureSpanEnd(String departureSpanEnd) {
        this.departureSpanEnd = departureSpanEnd;
    }

    public String getDepartureTransportType() {
        return departureTransportType;
    }

    public void setDepartureTransportType(String departureTransportType) {
        this.departureTransportType = departureTransportType;
    }

    public String getDepartureCompany() {
        return departureCompany;
    }

    public void setDepartureCompany(String departureCompany) {
        this.departureCompany = departureCompany;
    }

    public String getContentsName() {
        return contentsName;
    }

    public void setContentsName(String contentsName) {
        this.contentsName = contentsName;
    }

    public String getContentsType() {
        return contentsType;
    }

    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    public String getContentsDanger() {
        return contentsDanger;
    }

    public void setContentsDanger(String contentsDanger) {
        this.contentsDanger = contentsDanger;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public float getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(float emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    public float getContentsWeight() {
        return contentsWeight;
    }

    public void setContentsWeight(float contentsWeight) {
        this.contentsWeight = contentsWeight;
    }

    public float getWeight() {
        return emptyWeight + contentsWeight;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
