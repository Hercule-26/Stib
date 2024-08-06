package stib.dto;

public class SuperKey {
    private Integer lideId;
    private Integer stationId;

    public SuperKey(Integer lideId, Integer stationId) {
        this.lideId = lideId;
        this.stationId = stationId;
    }

    public int getLineKey() {
        return lideId;
    }

    public Integer getStationKey() {
        return stationId;
    }
}
