package stib.repository;

import org.junit.jupiter.api.Test;
import stib.config.ConfigManager;
import stib.dto.StationDto;
import stib.exception.RepositoryException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationDaoTest {

    @Test
    void selectAll() throws RepositoryException, IOException {
        ConfigManager.getInstance().load();
        StationDao dao = StationDao.getInstance();

        List<StationDto> result = dao.selectAll();
        assertEquals(60, result.size());
    }

    @Test
    void selectGoesRight() throws RepositoryException, IOException {
        ConfigManager.getInstance().load();
        StationDao dao = StationDao.getInstance();

        StationDto expected = new StationDto(8042,"ARTS-LOI");
        StationDto result = dao.select(8042);

        assertEquals(expected.getKey(), result.getKey());
    }

    @Test
    void selectNotFound() throws RepositoryException, IOException {
        ConfigManager.getInstance().load();
        StationDao dao = StationDao.getInstance();

        StationDto result = dao.select(9999);
        assertNull(result);
    }

    // A exécuté en solo car si on execute tout, il y a le configManager qui est load pour le dernier aussi
    @Test
    void selectGoesWrong() { // Connection pas effectué
        assertThrows(RepositoryException.class, () -> {
            StationDao dao = StationDao.getInstance();

            StationDto dto = dao.select(8042);
        });
    }
}