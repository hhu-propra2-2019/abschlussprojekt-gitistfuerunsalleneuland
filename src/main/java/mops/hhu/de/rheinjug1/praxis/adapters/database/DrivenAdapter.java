package mops.hhu.de.rheinjug1.praxis.adapters.database;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

public abstract class DrivenAdapter<Entity, DTO> {
    protected Entity entity;
    protected DTO dto;

    protected Entity toEntity(DTO dto) {
        copyProperties(dto, entity);
        return entity;
    }

    protected Optional<Entity> toEntity(Optional<DTO> dto) {
        if (dto.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toEntity(dto.get()));
    }

    protected List<Entity> toEntity(List<DTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(toList());
    }

    protected DTO toDTO(Entity entity) {
        copyProperties(entity, dto);
        return dto;
    }

    protected Optional<DTO> toDTO(Optional<Entity> entity) {
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toDTO(entity.get()));
    }

    protected List<DTO> toDTO(List<Entity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(toList());
    }
}
