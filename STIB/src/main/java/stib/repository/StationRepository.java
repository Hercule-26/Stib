package stib.repository;

import stib.exception.RepositoryException;
import stib.dto.*;
import java.util.List;

/**
 * Repository of the students This repository can add, remove, search any
 * student.
 */
public class StationRepository implements Repository<Integer, StationDto> {

    private final StationDao dao;

    /**
     * Creates a new instance of <code>StibRepository</code> with a
     * compatible <code>StibDao</code>.
     */
    public StationRepository() {
        dao = StationDao.getInstance();
    }

    public StationRepository(StationDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        StationDto refreshItem = dao.select(key);
        return refreshItem != null;
    }

}
