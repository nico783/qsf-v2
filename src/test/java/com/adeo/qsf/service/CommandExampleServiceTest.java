package com.adeo.qsf.service;

import com.adeo.qsf.domain.CommandExample;
import com.adeo.qsf.repository.CommandExampleRepository;
import com.adeo.qsf.service.dto.CommandExampleDTO;
import com.adeo.qsf.service.mapper.CommandExampleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandExampleServiceTest {

    @InjectMocks
    private CommandExampleService commandExampleService;

    @Mock
    private CommandExampleRepository commandExampleRepository;

    @Mock
    private CommandExampleMapper commandExampleMapper;

    @Test
    void shouldSaveCommandExampleDTO() {
        // arrange
        CommandExampleDTO commandExampleDTO = new CommandExampleDTO();

        CommandExample commandExampleReturn = new CommandExample();
        when(commandExampleMapper.toEntity(commandExampleDTO)).thenReturn(commandExampleReturn);

        CommandExample persistent = new CommandExample();
        when(commandExampleRepository.save(commandExampleReturn)).thenReturn(persistent);

        CommandExampleDTO dtoReturn = new CommandExampleDTO();
        when(commandExampleMapper.toDto(persistent)).thenReturn(dtoReturn);

        // act
        CommandExampleDTO result = commandExampleService.save(commandExampleDTO);

        // assert
        verify(commandExampleMapper).toEntity(commandExampleDTO);
        verify(commandExampleRepository).save(commandExampleReturn);
        verify(commandExampleMapper).toDto(persistent);
        assertSame(dtoReturn, result);
    }

}
