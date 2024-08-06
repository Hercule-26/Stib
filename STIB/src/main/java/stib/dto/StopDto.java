package stib.dto;

public class StopDto extends Dto<SuperKey> {
    private Integer idOrder;
    private String stationName;
    public StopDto(SuperKey key, Integer idOrder, String stationName) {
        super(key);
        this.idOrder = idOrder;
        this.stationName = stationName;
    }

    public Integer getLineId() {return key.getLineKey();}
    public Integer getStationId() {return key.getStationKey();}
    public Integer getIdOrder() {return this.idOrder;}

    public String getStationName() {return this.stationName;}
    @Override
    public String toString() {
        return "StopDto{" + "id_line=" + key.getLineKey()
                + ", id_station=" + key.getStationKey()
                + ", id_Order=" + idOrder
                + ", name = " + stationName
                + '}';
    }
}
