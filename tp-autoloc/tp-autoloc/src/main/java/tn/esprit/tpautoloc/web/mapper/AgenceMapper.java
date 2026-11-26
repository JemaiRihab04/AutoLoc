package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;

@Mapper(componentModel = "spring")
public interface AgenceMapper {

    @Mapping(target = "id", source = "idAgence")
    AgenceDTO toDto(Agence agence);

    @Mapping(target = "idAgence", source = "id")
    Agence toEntity(AgenceDTO dto);
}