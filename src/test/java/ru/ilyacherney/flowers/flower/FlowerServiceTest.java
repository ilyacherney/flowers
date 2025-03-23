package ru.ilyacherney.flowers.flower;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyacherney.flowers.cultivar.Cultivar;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlowerServiceTest {

    @Mock
    FlowerRepository flowerRepository;

    @InjectMocks
    FlowerService flowerService;

    @Test
    public void testCreateFlower() {
        // Assemble
        Cultivar cultivar = new Cultivar("Ромашка", new BigDecimal(80.00));
        when(flowerRepository.save(any(Flower.class))).thenReturn(new Flower(cultivar));

        // Act
        Flower savedFlower = flowerService.createFlower(cultivar);

        // Assert
        Assertions.assertEquals(cultivar, savedFlower.getCultivar());
        Mockito.verify(flowerRepository, times(1)).save(any(Flower.class));
    }

    @Test
    void testDeleteFlower_WhenExists() {
        // Arrange
        Long flowerId = 1L;
        when(flowerRepository.existsById(flowerId)).thenReturn(true);

        // Act
        flowerService.deleteFlower(flowerId);

        // Assert
        verify(flowerRepository, times(1)).deleteById(flowerId);
    }

    @Test
    void testDeleteFlower_WhenDoesNotExist() {
        // Arrange
        Long flowerId = 1L;
        when(flowerRepository.existsById(flowerId)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> flowerService.deleteFlower(flowerId));
        assertEquals("Flower with ID 1 not found.", exception.getMessage());

        verify(flowerRepository, never()).deleteById(anyLong());
    }
}
