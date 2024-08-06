package stib.repository;

import org.junit.jupiter.api.Test;
import stib.config.ConfigManager;
import stib.dto.StopDto;
import stib.dto.SuperKey;
import stib.exception.RepositoryException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StopDaoTest {

    @Test
    void selectAll() throws RepositoryException, IOException {
        ConfigManager.getInstance().load();
        StopDao dao = StopDao.getInstance();

        List<StopDto> result = dao.selectAll();
        assertEquals(94, result.size());
    }

    @Test
    void selectGoesRight() throws RepositoryException, IOException {
        ConfigManager.getInstance().load();
        StopDao dao = StopDao.getInstance();

        StopDto expected = new StopDto(new SuperKey(1, 8042), 9, "ART-LOI");
        StopDto result = dao.select(expected.getKey());
        assertEquals(expected.getKey().getLineKey(), result.getKey().getLineKey());
        assertEquals(expected.getKey().getStationKey(), result.getKey().getStationKey());
    }
    @Test
    void selectNotFound() throws RepositoryException, IOException {
        ConfigManager.getInstance().load();
        StopDao dao = StopDao.getInstance();

        StopDto result = dao.select(new SuperKey(9999, 9999));
        assertNull(result);
    }

    // A exécuté en solo car si on execute tout, il y a le configManager qui est load pour le dernier aussi
    @Test
    void selectGoesWrong() { // Connection pas effectué
        assertThrows(RepositoryException.class, () -> {
            StopDao dao = StopDao.getInstance();

            StopDto dto = dao.select(new SuperKey(1, 8042));
        });
    }
}