package stib.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stib.dto.StationDto;
import stib.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class StationRepositoryTest {
    @Mock
    private StationDao mock;
    private final StationDto stationDto;
    private final StationDto stationDto2;
    private final StationDto stationFalse;
    private final List<StationDto> all;

    public StationRepositoryTest() {
        this.stationFalse = new StationDto(9999, "Random Name");
        this.stationDto = new StationDto(8042,"ARTS-LOI");
        this.stationDto2 = new StationDto(8032,"PARC");

        this.all = new ArrayList<>();
        all.add(stationDto);
        all.add(stationDto2);
    }
    @BeforeEach
    void init() throws RepositoryException {
        Mockito.lenient().when(mock.select(stationDto.getKey())).thenReturn(stationDto);
        Mockito.lenient().when(mock.select(stationFalse.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    void getAll() throws RepositoryException {
        List<StationDto> expected = this.all;
        StationRepository repository = new StationRepository(mock);

        List<StationDto> result = repository.getAll();

        assertEquals(expected, result);
        // Vérifier que l'objet à été appeler une fois
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    void getGoesRight() throws RepositoryException {
        StationDto expected = stationDto;

        StationRepository repository = new StationRepository(mock);
        StationDto result = repository.get(stationDto.getKey());

        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(stationDto.getKey());
    }

    @Test
    void getDontFound() throws RepositoryException {

        StationRepository repository = new StationRepository(mock);
        StationDto result = repository.get(stationDto2.getKey());

        assertNull(result);
        Mockito.verify(mock, times(1)).select(stationDto2.getKey());
    }

    @Test
    void getGoesWrong() throws RepositoryException {

        assertThrows(RepositoryException.class, () -> {
            StationRepository repository = new StationRepository(mock);
            StationDto result = repository.get(null);
        });
    }
}