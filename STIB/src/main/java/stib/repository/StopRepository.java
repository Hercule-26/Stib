package stib.repository;

import stib.dto.StopDto;
import stib.dto.SuperKey;
import stib.exception.RepositoryException;

import java.util.List;

public class StopRepository implements Repository<SuperKey, StopDto> {
    private final StopDao dao;

    public StopRepository() {
        dao = StopDao.getInstance();
    }

    public StopRepository(StopDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(SuperKey key) throws RepositoryException {
        if (contains(key)) {
            return dao.select(key);
        } else {
            throw new RepositoryException("Element avec id 1 : " + key.getLineKey() + " id 2 : " + key.getStationKey() + " pas trouver");
        }
    }

    @Override
    public boolean contains(SuperKey key) throws RepositoryException {
        StopDto dto = dao.select(key);
        return dto != null;
    }
}
