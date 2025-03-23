package ru.ilyacherney.flowers.cultivar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyacherney.flowers.flower.FlowerRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CultivarServiceTest {

    @Mock
    CultivarRepository cultivarRepository;

    @Mock
    FlowerRepository flowerRepository;

    @InjectMocks
    CultivarService service;

    @Test
    public void testCreateCultivar() {
        // Assemble
        when(cultivarRepository.save(any(Cultivar.class))).thenReturn(new Cultivar("Ромашка", new BigDecimal(200.00)));

        // Act
        Cultivar savedCultivar = service.createCultivar("Ромашка", new BigDecimal(200.00));

        // Assert
        verify(cultivarRepository, times(1)).save(any(Cultivar.class));
        Assertions.assertNotNull(savedCultivar);
    }

    @Test
    public void testDeleteCultivar_WhenExits() {
        // Assemble
        Long cultivarId = 1L;
        when(cultivarRepository.existsById(cultivarId)).thenReturn(true);

        // Act
        service.deleteCultivar(cultivarId);

        // Assert
        verify(cultivarRepository, times(1)).deleteById(cultivarId);
    }

    @Test
    public void testDeleteCultivar_WhenDoesNotExits() {
        // Assemble
        Long cultivarId = 1L;
        when(cultivarRepository.existsById(cultivarId)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteCultivar(cultivarId));
        assertEquals("Cultivar with id 1 does not exist", exception.getMessage());

        verify(cultivarRepository, never()).deleteById(cultivarId);
    }

    @Test
    public void testDeleteCultivar_WhenFlowersWithTheCultivarExists() {
        // Assemble
        Long cultivarId = 1L;
        when(cultivarRepository.existsById(cultivarId)).thenReturn(true);
        when(flowerRepository.existsByCultivarId(cultivarId)).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> service.deleteCultivar(cultivarId));
        assertEquals("Cannot delete cultivar with id 1 because there are flowers associated with this cultivar", exception.getMessage());
        verify(cultivarRepository, never()).deleteById(cultivarId);
    }
}
