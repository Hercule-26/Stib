package stib.dto;

public class StationDto extends Dto<Integer> {

    private String stationName;

    public StationDto(int number, String stationName) {
        super(number);
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return stationName;
    }

}
