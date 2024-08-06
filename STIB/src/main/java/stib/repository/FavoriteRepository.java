package stib.repository;

import stib.dto.PairStationDto;
import stib.dto.StationDto;
import stib.exception.RepositoryException;

import java.util.List;

public class FavoriteRepository implements Repository<Integer, PairStationDto> {
    private final FavoriteDao dao;

    /**
     * Creates a new instance of <code>StibRepository</code> with a
     * compatible <code>StibDao</code>.
     */
    public FavoriteRepository() {
        dao = FavoriteDao.getInstance();
    }
    @Override
    public List<PairStationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public PairStationDto get(Integer key) throws RepositoryException {
        if (contains(key)) {
            return dao.select(key);
        } else {
            return null;
        }
    }
    public void delete(Integer key) {
        dao.delete(key);
    }
    public Integer add(StationDto begin, StationDto end) {
        return dao.add(begin, end);
    }
    @Override
    public boolean contains(Integer key) throws RepositoryException {
        PairStationDto item = dao.select(key);
        return item != null;
    }
}
