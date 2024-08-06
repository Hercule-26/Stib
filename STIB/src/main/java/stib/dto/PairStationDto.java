package stib.dto;

public class PairStationDto extends Dto<Integer> {
    private final Integer idBegin;
    private final String nameBegin;
    private final Integer idEnd;
    private final String nameEnd;

    public PairStationDto(Integer key, Integer idBegin, String nameBegin, Integer idEnd, String nameEnd) {
        super(key);
        this.idBegin = idBegin;
        this.nameBegin = nameBegin;
        this.idEnd = idEnd;
        this.nameEnd = nameEnd;
    }
    public Integer getIdBegin() {
        return idBegin;
    }
    public String getNameBegin() {
        return nameBegin;
    }
    public Integer getIdEnd() {
        return idEnd;
    }
    public String getNameEnd() {
        return nameEnd;
    }
    public StationDto getStationBegin() {
        return new StationDto(idBegin, nameBegin);
    }
    public StationDto getStationEnd() {
        return new StationDto(idEnd, nameEnd);
    }
    @Override
    public String toString() {
        return nameBegin + " -> " + nameEnd;
    }
}
